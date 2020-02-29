package es.lanyu.commons.tiempo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DatableLocalDateTime extends DatableImpl<LocalDateTime> {

    @Override
    public Date getFecha() {
        return (getTemporal() != null) ? Date.from(getTemporal().atZone(ZoneId.systemDefault()).toInstant()) : null;
    }
    
    @Override
    public void setFecha(Date fecha) {
        setTemporal(fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }
    
    @Override
    public Long getTimestamp() {
        return (getTemporal() != null) ? getTemporal().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(): null;
    }

    @Override
    public void setTimestamp(Long timestamp) {
        setTemporal(Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

    public DatableLocalDateTime() {
        this(LocalDateTime.now());
    }

    public DatableLocalDateTime(LocalDateTime temporal) {
        super(temporal);
    }

}
