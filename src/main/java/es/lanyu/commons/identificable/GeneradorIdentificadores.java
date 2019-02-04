package es.lanyu.commons.identificable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class GeneradorIdentificadores<K extends Comparable<K>> {

	private Map<K, Identificable<K>> mapaIdentificadores = new HashMap<>();
	
	public abstract K generarID();
	
	@SuppressWarnings("unchecked")
	protected <T extends Identificable<K>> Map<K, T> getMapaIdentificadores() {
		return (Map<K, T>) mapaIdentificadores;
	}
	
	public Identificable<K> getIdentificable(K id){
		return getMapaIdentificadores().get(id);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Identificable<K>> Collection<T> getIdentificablesRegistrados() {
		return (Collection<T>) getMapaIdentificadores().values();
	}
}
