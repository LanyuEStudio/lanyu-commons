package es.lanyu.commons.identificable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import es.lanyu.commons.reflect.ReflectUtils;

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
	
	/**Devuelve el identificador de tipo K que identifica univocamente al objeto en su tipo.
	 * Por defecto se devolvera el valor del campo marcado con la anotacion {@link Identificador},
	 * si la implementacion no usa esta anotacion se devolvera el campo con nombre {@code "id"}.
	 * Para mejorar el rendimiento se recomienda al menos implementarlo con una "lazy-loading"
	 * para no buscar el {@link Field} en sucesivas invocaciones como se hace en {@link IdentificableString#getIdentificador()}:
	 * <pre>&#64;Override
	 * public String getIdentificador() {
	 *   if(id == null)
	 *     setIdentificador(Identificable.super.getIdentificador());
	 *   return id;
	 * }</pre>
	 * No obstante se recomienda sobrescribir el metodo para poder realizar validaciones.
	 * @return El identificador de tipo K correspondiente al objeto.
	 */
	@SuppressWarnings("unchecked")
	default K getIdentificador(){
		K id = null;
		Identificador iden = ReflectUtils.buscarAnotacionEnClase(Identificador.class, getClass());
		String campoId = "id";
		if(iden != null)
			campoId = iden.value();
		try {
			Field campo = ReflectUtils.getCampo(getClass(), campoId, true);
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
	
	/**Devuelve el {@link Comparator} para ordenar los {@code Identificable<K>} por el orden natural
	 * del tipo {@code K}
	 * @param <K> Tipo del identificador usado por el {@code Identificable}
	 * @return {@code Comparator} segun la descripcion
	 */
	static <K extends Comparable<K>> Comparator<Identificable<K>> getComparator(){
//		return new Comparator<Identificable<KI>>() {
//			
//			@Override
//			public int compare(Identificable<KI> o1, Identificable<KI> o2) {
//				return o1.compareTo(o2);
//			}
//		};
		return Comparator.naturalOrder();// Java 8
	}
	
	/**Devuelve el {@link Identificable} que se encuentra en el {@code indice} cuando los
	 * {@code identificables} estan ordenados por su orden natural (ver {@link Identificable#getComparator()}
	 * @param <K> Tipo del identificador
	 * @param <T> Tipo del {@code Identificable<K>} 
	 * @param indice Posicion que ocupa en {@code identificables} ordenados
	 * @param identificables {@link Collection} de identificables. No se requiere pasarla ordenada.
	 * @return {@code Identificable} que ocupa la posicion {@code indice}
	 */
	static <K extends Comparable<K>, T extends Identificable<K>>
		T getIdentificablePorIndiceOrdenado(int indice, Collection<T> identificables) {
		
		List<T> identificablesOrdenados = new ArrayList<>(identificables);
		identificablesOrdenados.sort(getComparator());
		
		return identificablesOrdenados.get(indice);
	}
	
	
	/**Devuelve un {@link Identificable} con el {@code id} dentro de {@code identificables}
	 * @param <K> Tipo del identificador
	 * @param <T> Tipo del {@code Identificable<K>} 
	 * @param id Identificador del {@code Identificable} buscado
	 * @param identificables {@link Collection} de identificables donde buscar
	 * @return El {@code Identificable} buscado o {@code null} si no existe
	 */
	static <K extends Comparable<K>, T extends Identificable<K>>
		T getIdentificablePorId(K id, Collection<T> identificables) {
		return identificables.stream()
							.filter(i -> i.getIdentificador().equals(id))
							.findAny()
							.orElse(null);
	}
	
	@Override
	default int compareTo(Identificable<K> identificable) {
		return getIdentificador().compareTo(identificable.getIdentificador());
	}
	
	
	/**Metodo auxiliar para implementar por defecto {@link Object#hashCode()} en la interface
	 * @return hashCode para el identificable solo teniendo en cuenta el identificador
	 */
	default int getHashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getIdentificador() == null) ? 0 : getIdentificador().hashCode());
		
		return result;
	}
	
	/**Metodo auxiliar para implementar por defecto {@link Object#equals(Object)} en la interface
	 * @param obj Objeto contra el que compararse
	 * @return {@code true} si los dos identificadore son iguales
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
