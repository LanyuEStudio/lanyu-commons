package es.lanyu.commons.tiempo;

import java.time.Instant;
import java.util.Date;

public class DatableInstant extends DatableImpl<Instant> {

    @Override
    public Date getFecha() {
        return (getTemporal() != null) ? Date.from(getTemporal()) : null;
    }

    @Override
    public void setFecha(Date fecha) {
        setTemporal(fecha.toInstant());
    }
    
    @Override
    public Long getTimestamp() {
        return (getTemporal() != null) ? getTemporal().toEpochMilli() : null;
    }

    @Override
    public void setTimestamp(Long timestamp) {
        setTemporal(Instant.ofEpochMilli(timestamp));
    }

    public DatableInstant() {
        this(Instant.now());
    }

    public DatableInstant(Instant temporal) {
        super(temporal);
    }

}
