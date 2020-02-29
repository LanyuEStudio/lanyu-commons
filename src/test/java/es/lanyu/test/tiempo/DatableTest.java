package es.lanyu.test.tiempo;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.junit.Test;

import es.lanyu.commons.tiempo.Datable;
import es.lanyu.commons.tiempo.DatableImpl;
import es.lanyu.commons.tiempo.DatableInstant;
import es.lanyu.commons.tiempo.DatableLocalDateTime;

/**Test para los tipos del paquete {@link es.lanyu.commons.tiempo}
 * @author isma
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public class DatableTest {

    TemporalAmount dia = Period.ofDays(1);
    TemporalAmount minuto = Duration.ofMinutes(1);
    
    @Test
    public void settersDatableInstantCoherentes() {
        settersDatableCoherentes(Instant.class, new DatableInstant(), minuto,
                i -> Date.from(i),
                d -> ((Instant)d.getTemporal()).toEpochMilli());
        settersDatableCoherentes(LocalDateTime.class, new DatableLocalDateTime(), minuto,
                l -> new DatableLocalDateTime(l).getFecha(),
                d -> new DatableLocalDateTime(d.getTemporal()).getTimestamp());
    }
    
    /**Test para evaluar la compatibilidad con codigo anterior a version 1.0
     * @param <T> tipo que implementa {@link Temporal}{@code &}{@link Comparable}
     * @param tipo T para usar
     * @param datable para usar como punto de partida del test
     * @param tiempoExtra para usar en los incrementos internos que se usan para variar el {@code datable}
     * @param funcionDate {@link Function} para convertir {@code T} en {@link Date}
     * @param funcionTimestamp {@link Function} para convertir {@code T} en {@code timestamp}
     */
    @SuppressWarnings("deprecation")
	private <T extends Comparable & Temporal> void settersDatableCoherentes(Class<T> tipo, DatableImpl<T> datable, TemporalAmount tiempoExtra,
            Function<T, Date> funcionDate, Function<DatableImpl<T>, Long> funcionTimestamp) {
        Date date = new Date();
        datable.setFecha(date);
        long timestamp = date.getTime();
        assertEquals("Temporal timestamp distinto a Date timestamp para setFecha", timestamp, datable.getFecha().getTime());
        timestamp++;
        datable.setTimestamp(timestamp);
        assertEquals("Temporal timestamp distinto de setTimestamp", timestamp, (long)datable.getTimestamp());
        assertEquals("Date NO actualizado tras setTimeStamp", new Date(timestamp), datable.getFecha());
        date = funcionDate.apply((T)datable.getTemporal().plus(tiempoExtra));
        datable.setFecha(date);
        assertEquals("Timestamp NO actualizado tras setFecha", date.getTime(), (long)datable.getTimestamp());
        T temporal = datable.addTiempo(tiempoExtra);
        datable.setTemporal(temporal);
        assertEquals("Timestamp NO actualizado tras setTemporal", (long)funcionTimestamp.apply(datable), (long)datable.getTimestamp());
        assertEquals("Date NO actualizado tras setTemporal", funcionDate.apply(datable.getTemporal()), datable.getFecha());
    }
    
    @Test
    public void ordenacionDatables() {        
        ordenacionDatablesLocalDate(Instant.now(), dia);
        ordenacionDatablesLocalDate(LocalDateTime.now(), minuto);
        ordenacionDatablesLocalDate(LocalTime.now(), minuto);
        ordenacionDatablesLocalDate(LocalDate.now(), dia);
    }
    
    @SuppressWarnings("unchecked")
    private <T extends Comparable<T> & Temporal> void ordenacionDatablesLocalDate(T temporal, TemporalAmount tiempoExtra) {
        DatableImpl<T> datable = new DatableImplAdapter<T>(temporal) {};
        DatableImpl<T>[] temporales = new DatableImpl[] {
                new DatableImplAdapter<T>((T)tiempoExtra.subtractFrom(temporal)),
                datable,
                new DatableImplAdapter<T>((T)tiempoExtra.addTo(temporal)) {} };
        List<DatableImpl<T>> datables = Arrays.asList(temporales[1], temporales[0], temporales[2]);
        datables.sort(Datable.getComparatorDatable((Class<T>)temporal.getClass()));
        
        assertArrayEquals("Orden incorrecto", temporales, datables.toArray());
    }
    
    @Test
    public void moverTemporalUnaCantidad() {
    	modificarTiempoTemporal(new DatableInstant(), minuto);
    	modificarTiempoTemporal(new DatableLocalDateTime(), minuto);
    }
    
    @SuppressWarnings("unchecked")
	private <T extends Comparable & Temporal> void modificarTiempoTemporal(Datable<T> datable, TemporalAmount tiempo) {
    	T modificado = (T) datable.getTemporal().plus(tiempo);
    	datable.addTiempo(tiempo);
    	assertEquals("Tiempo sumado NO coincide", modificado, datable.getTemporal());
    	modificado = (T) datable.getTemporal().minus(tiempo);
    	datable.restarTiempo(tiempo);
    	assertEquals("Tiempo restado NO coincide", modificado, datable.getTemporal());
    }
    
    private class DatableImplAdapter<T extends Comparable & Temporal> extends DatableImpl<T> {

        @Override
        public Date getFecha() { return null; }

        @Override
        public Long getTimestamp() { return null; }

        @Override
        public void setFecha(Date fecha) {}

        @Override
        public void setTimestamp(Long timestamp) { }

        public DatableImplAdapter(T temporal) {
            super(temporal);
        }

    }
}
