package es.lanyu.commons.identificable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;


/**
 * @author isma
 *
 * @param <K> Tipo del identificador. Debe implementar Comparable<K>
 * 
 * Esta interfaz permite identificar un objeto por su identificador y comparar entre dos
 * Tambien permite cargar un mapa para una clase especifica con identificables de un tipo 
 * o clase hija desde datos obtenidos en archivo json con un serializador Json
 */
/**
 * @author isma
 *
 * @param <K>
 */
public interface Identificable<K extends Comparable<K>> extends Comparable<Identificable<K>>{
	
	K getIdentificador();
	
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
	
	static <KI extends Comparable<KI>, T extends Identificable<KI>> T  getIdentificablePorIndiceOrdenado(int indice, Collection<T> listaIdentificables){
		List<T> identificablesOrdenados = new ArrayList<>(listaIdentificables);
		identificablesOrdenados.sort(getComparator());
		
		return identificablesOrdenados.get(indice);
	}
	
	static <KI extends Comparable<KI>, T extends Identificable<KI>> T  getIdentificablePorId(KI id, Collection<T> listaIdentificables){
		return listaIdentificables.stream().filter(i -> i.getIdentificador().equals(id)).findAny().orElse(null);
	}
	
	@Override
	default int compareTo(Identificable<K> identificable) {
		return getIdentificador().compareTo(identificable.getIdentificador());
	}
	
	
	/**
	 * @return hashCode para el identificable solo teniendo en cuenta el identificador
	 */
	default int getHashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getIdentificador() == null) ? 0 : getIdentificador().hashCode());
		
		return result;
	}
	
	/**
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
}
