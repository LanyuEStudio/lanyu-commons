package es.lanyu.commons.identificable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**Esta interfaz permite identificar un objeto por su identificador de tipo K 
 * y comparar entre dos de ellos pues debe implementar la interfaz {@code Comparable<Identificable<K>>}.
 * Requiere Java 8
 * 
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 * @param <K> Tipo del identificador. Debe implementar {@code Comparable<K>}
 */
public interface Identificable<K extends Comparable<K>> extends Comparable<Identificable<K>>{
	
	@SuppressWarnings("unchecked")
	default K getIdentificador(){
		K id = null;
		Identificador iden = es.lanyu.commons.reflect.Utils.buscarAnotacionEnClase(Identificador.class, getClass());
		String campoId = "id";
		if(iden != null)
			campoId = iden.value();
		try {
			Field campo = es.lanyu.commons.reflect.Utils.getCampo(getClass(), campoId, true);
			id = (K)campo.get(this);
		} catch (Exception e) {
			try {
				throw new IdentificadorException();
			} catch (IdentificadorException e1) {
				e1.printStackTrace();
			}
		}
		
		return id;
	}
	
	static <KI extends Comparable<KI>> Comparator<Identificable<KI>> getComparator(){
//		return new Comparator<Identificable<KI>>() {
//			
//			@Override
//			public int compare(Identificable<KI> o1, Identificable<KI> o2) {
//				return o1.compareTo(o2);
//			}
//		};
		return Comparator.naturalOrder();// Java 8
	}
	
	static <KI extends Comparable<KI>, T extends Identificable<KI>>
		T getIdentificablePorIndiceOrdenado(int indice, Collection<T> listaIdentificables) {
		
		List<T> identificablesOrdenados = new ArrayList<>(listaIdentificables);
		identificablesOrdenados.sort(getComparator());
		
		return identificablesOrdenados.get(indice);
	}
	
	
	static <KI extends Comparable<KI>, T extends Identificable<KI>>
		T getIdentificablePorId(KI id, Collection<T> listaIdentificables) {
		return listaIdentificables.stream()
									.filter(i -> i.getIdentificador().equals(id))
									.findAny()
									.orElse(null);
	}
	
	@Override
	default int compareTo(Identificable<K> identificable) {
		return getIdentificador().compareTo(identificable.getIdentificador());
	}
	
	
	/**Metodo auxiliar para implementar por defecto hashcode en la interface
	 * @return hashCode para el identificable solo teniendo en cuenta el identificador
	 */
	default int getHashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getIdentificador() == null) ? 0 : getIdentificador().hashCode());
		
		return result;
	}
	
	/**Metodo auxiliar para implementar por defecto equals en la interface
	 * @param obj Objecto con el que comparar la igualdad
	 * @return true si los dos identificadore son iguales
	 */
	default boolean isEquals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		Identificable<K> other = (Identificable<K>)obj;
		if (getIdentificador() == null) {
			if (other.getIdentificador() != null)
				return false;
		} else if (!getIdentificador().equals(other.getIdentificador()))
			return false;
		return true;
	}
	
	@SuppressWarnings("serial")
	static class IdentificadorException extends Exception {
		public IdentificadorException(){
			super("Para implementar la interfaz Identificable deben implementarse"
			+ " el método getIdentificador(), utilizar la anotacion @Identificador"
			+ " o tener campo de tipo compatible con la clave y de nombre 'id'.");
		}
	}
}
