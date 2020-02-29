package es.lanyu.commons.tiempo;

import java.time.Duration;
import java.time.Period;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

/**Interfaz para objetos que tienen una fecha. Permite la comparacion de estos objetos por fecha
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 * @param <T> tipo especifico de Temporal y Comparable (ejemplo Instant, LocalDate, etc...)
 */
public interface Datable<T extends Comparable & Temporal> {
	
	/**Devuelve un {@link Comparator} para ordenar {@link Datable}s por sus fechas
	 * obtenidas con {@link Datable#getFecha()}.
	 * @param <T> tipo concreto para el que se quiere conseguir el comparador
	 * @param tipo concreto para el que se quiere conseguir el comparador (ejemplo: {@code Instant.class})
	 * @return {@code Comparator} para ordenar {@code Datables} por fechas
	 */
	static <T extends Comparable<T> & Temporal> Comparator<Datable<T>> getComparatorDatable(Class<T> tipo) {
		return new Comparator<Datable<T>>() {
			@Override
			public int compare(Datable<T> datable1, Datable<T> datable2) {
			    return Objects.compare(datable1.getTemporal(), datable2.getTemporal(), Comparator.naturalOrder());
			}
		};
	}
	
    /**Devuelve el {@link Temporal} del {@link Datable}. Por defecto devuelve {@code null}
     * si no se le ha proporcionado un valor que significa que se desconoce la fecha/hora. 
     * @return {@link Temporal} del {@code Datable} o {@code null} si no se proporciono valor
     */
    T getTemporal();
    
	/**Devuelve la {@link Date} que corresponde con el {@code timeStamp} o {@code null}
	 * si {@link Datable#getTimestamp()} devuelve {@code null}.
	 * @deprecated se recomienda usar {@link #getTemporal()}
	 * @return {@code Date} correspondiente a timeStamp o {@code null}
	 */
    Date getFecha();
	
	/**Devuelve el {@code timeStamp} del {@link Datable}. Por defecto devuelve {@code null}
	 * si no se le ha proporcionado un valor que significa que se desconoce la fecha.
	 * @deprecated se recomienda usar {@link #getTemporal()} 
	 * @return {@code timeStamp} del {@code Datable} o {@code null} si no se proporciono valor
	 */
	Long getTimestamp();
	
	/**Suma al temporal la cantidad con el valor de {@code tiempo}
	 * @param tiempo cantidad de tiempo a sumar (ejemplo: {@code Duration.ofHours(1)} o {@code Period.ofDays(1)})
	 * @return devuelve el nuevo valor asignado al temporal
	 */
	T addTiempo(TemporalAmount tiempo);
	
	/**Resta al temporal la cantidad con el valor de {@code tiempo}
	 * @param tiempo cantidad de tiempo a restar (ejemplo: {@code Duration.ofHours(1)} o {@code Period.ofDays(1)})
	 * @return devuelve el nuevo valor asignado al temporal 
	 */
	T restarTiempo(TemporalAmount tiempo);

	/**Devuelve si este {@code Datable} es anterior al {@code datable} pasado.
	 * @param datable {@code Datable con el que comparar}
	 * @return {@code true} si es anterior a {@code datable}
	 */
	@SuppressWarnings("unchecked")
	default boolean antesDe(Datable<? extends T> datable) {
		return getTemporal().compareTo(datable.getTemporal()) < 0;
	}
	
	/**Devuelve si este {@code Datable} es posterior al {@code datable} pasado.
	 * @param datable {@code Datable con el que comparar}
	 * @return {@code true} si es posterior a {@code datable}
	 */
	@SuppressWarnings("unchecked")
	default boolean despuesDe(Datable<? extends T> datable) {
		return getTemporal().compareTo(datable.getTemporal()) > 0;
	}
	
}
