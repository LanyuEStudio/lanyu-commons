package es.lanyu.commons.identificable;

import java.io.Serializable;
import java.util.Comparator;

public abstract class IdentificableString implements Identificable<String>, Serializable {
	
	private static final long serialVersionUID = 5657627507807513121L;
	
	protected String id;
	
	public static Comparator<Identificable<String>> getComparadorPorIdentificador() {
		return Identificable.getComparator();
	}
	
	@Override
	public String getIdentificador() {
		return id;
	}
	
	public void setIdentificador(String id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		return getHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return isEquals(obj);
	}
}
