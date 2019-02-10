package es.lanyu.commons.identificable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class GeneradorIdentificadores<I extends Identificable<K>, K extends Comparable<K>> {

	private Map<K, I> mapaIdentificadores = new HashMap<>();
	
	public abstract K generarID();
	
	protected Map<K, I> getMapaIdentificadores() {
		return mapaIdentificadores;
	}
	
	public Identificable<K> getIdentificable(K id){
		return getMapaIdentificadores().get(id);
	}
	
	public Collection<I> getIdentificablesRegistrados() {
		return getMapaIdentificadores().values();
	}
}
