package es.lanyu.commons.identificable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorIdentificables {
	@SuppressWarnings("rawtypes")
	Map<Class, Map> mapaIdentificables = new HashMap<>();

	@SuppressWarnings("unchecked")
	public <K extends Comparable<K>, T extends Identificable<K>> void addIdentificable(Class<T> clase, K id, T identificable){
		Map<K, T> mapa = getMapaIdentificables().get(clase);
		if (mapa == null) {
			mapa = new HashMap<>();
			getMapaIdentificables().put(clase, mapa);
		}
		
		mapa.put(id, identificable);
	}
	
	@SuppressWarnings("rawtypes")
	public Map<Class, Map> getMapaIdentificables() {
		return mapaIdentificables;
	}

	@SuppressWarnings("unchecked")
	public <I> Collection<I> getIdentificables(Class<I> clase) {
		return getMapaIdentificables().get(clase).values();
	}
	
	@SuppressWarnings("unchecked")
	public <I> Collection<I> getIdentificablesOrdenados(Class<I> clase, Comparator<I> comparador) {
		List<I> lista = new ArrayList<>(getMapaIdentificables().get(clase).values());
		//Los ordeno por el comparador
		Collections.sort(lista, comparador);
		
		return lista;
	}

	@SuppressWarnings("unchecked")
	public <K extends Comparable<K>, T extends Identificable<K>> T getIdentificable(Class<T> clase, K id){
		return (T) getMapaIdentificables().get(clase).get(id);
	}
	
}
