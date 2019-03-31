package es.lanyu.commons.tiempo;

import java.util.Comparator;
import java.util.Date;

/**Interfaz para objetos que tienen una fecha. Permite la comparacion de estos objetos por fecha
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public interface Datable extends Comparable<Datable>{
	
	/**Devuelve la {@link Date} que corresponde con el {@code timeStamp} o {@code null}
	 * si {@link Datable#getTimeStamp()} devuelve {@code null}.
	 * @return {@code Date} correspondiente a timeStamp o {@code null}
	 */
	default Date getFecha() {
		return (getTimeStamp() != null)?new Date(getTimeStamp()):null;
	}
	
	/**Devuelve el {@code timeStamp} del {@link Datable}. Por defecto devuelve {@code null}
	 * si no se le ha proporcionado un valor que significa que se desconoce la fecha. 
	 * @return {@code timeStamp} del {@code Datable} o {@code null} si no se proporciono valor
	 */
	default Long getTimeStamp() {
		return null;
	}
	
	/**Devuelve un {@link Comparator} para ordenar {@link Datable}s por sus fechas
	 * obtenidas con {@link Datable#getFecha()}.
	 * @return {@code Comparator} para ordenar {@code Datables} por fechas
	 */
	static Comparator<Datable> getComparatorDatable() {
		return new Comparator<Datable>() {
			@Override
			public int compare(Datable datable1, Datable datable2) {
				if(datable1 == null || datable1.getFecha() == null) return 0;
				else if(datable2 == null || datable2.getFecha() == null) return 0;
				else return datable1.getFecha().compareTo(datable2.getFecha());
			}
		};
	}
	
	@Override
	default int compareTo(Datable datable) {
		return getFecha().compareTo(datable.getFecha());
	}
	
	/**Devuelve si este {@code Datable} es anterior al {@code datable} pasado.
	 * @param datable {@code Datable con el que comparar}
	 * @return {@code true} si es anterior a {@code datable}
	 */
	default boolean antesDe(Datable datable) {
		return getFecha().before(datable.getFecha());
	}
	
	/**Devuelve si este {@code Datable} es posterior al {@code datable} pasado.
	 * @param datable {@code Datable con el que comparar}
	 * @return {@code true} si es posterior a {@code datable}
	 */
	default boolean despuesDe(Datable datable) {
		return getFecha().after(datable.getFecha());
	}
	
}
