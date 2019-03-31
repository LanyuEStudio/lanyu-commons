package es.lanyu.commons.tiempo;
import java.util.Date;

/**Implementacion basica de {@link Datable}
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public abstract class DatableImpl implements Datable {
	protected transient Date fecha;
	protected Long timeStamp;
	
	@Override
	public Date getFecha() {
		if(fecha == null && getTimeStamp() != null)
			fecha = Datable.super.getFecha();
		return fecha;
	}
	
	@Override
	public Long getTimeStamp() {
		return timeStamp;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
		setTimeStamp((fecha != null)?fecha.getTime():null);
	}
	
	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
}
