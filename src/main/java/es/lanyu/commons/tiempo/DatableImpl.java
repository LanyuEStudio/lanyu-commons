package es.lanyu.commons.tiempo;
import java.util.Date;

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
