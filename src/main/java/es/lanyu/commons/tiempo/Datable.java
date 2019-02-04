package es.lanyu.commons.tiempo;

import java.util.Comparator;
import java.util.Date;

public interface Datable extends Comparable<Datable>{
	
	default Date getFecha() {
		return (getTimeStamp() != null)?new Date(getTimeStamp()):null;
	}
	
	default Long getTimeStamp() {
		return null;
	}
	
	public static Comparator<Datable> getComparatorDatable() {
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
	
	default boolean antesDe(Datable datable) {
		return getFecha().before(datable.getFecha());
	}
	
	default boolean despuesDe(Datable datable) {
		return getFecha().after(datable.getFecha());
	}
	
}
