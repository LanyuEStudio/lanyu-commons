package es.lanyu.commons.tiempo;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.Date;

/**Implementacion basica de {@link Datable}
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public abstract class DatableImpl<T extends Temporal & Comparable> implements Datable<T> {
    protected T temporal;
	
	@Override
    public T getTemporal() {
        return temporal;
    }
	
	/**Establece el temporal tal cual como el nuevo valor
	 * @param temporal con el valor a establecer
	 */
	public void setTemporal(T temporal) {
        this.temporal = temporal;
    }
	
	/**@deprecated se recomienda usar {@link #setTemporal(Temporal) setTemporal(T)}
	 * @param fecha para establecer con {@link #setTemporal(Temporal) setTemporal(T)}
	 */
	public abstract void setFecha(Date fecha);
	
	/**@deprecated se recomienda usar {@link #setTemporal(Temporal) setTemporal(T)}
	 * @param timestamp para establecer con {@link #setTemporal(Temporal) setTemporal(T)}
	 */
	public abstract void setTimestamp(Long timestamp);
	
	public DatableImpl(T temporal) {
		super();
	    setTemporal(temporal);
    }
	
	@Override
    @SuppressWarnings("unchecked")
	public T addTiempo(TemporalAmount tiempo) {
		T nuevoTemporal = (T) getTemporal().plus(tiempo);
		setTemporal(nuevoTemporal);
		return nuevoTemporal;
	}
	
	@Override
    @SuppressWarnings("unchecked")
	public T restarTiempo(TemporalAmount tiempo) {
		T nuevoTemporal = (T) getTemporal().minus(tiempo);
		setTemporal(nuevoTemporal);
		return nuevoTemporal;
	}
	
    @Override
    public String toString() {
        return getTemporal().toString();
    }
    
}
