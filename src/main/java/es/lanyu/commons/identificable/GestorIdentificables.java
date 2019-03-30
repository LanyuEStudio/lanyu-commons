package es.lanyu.commons.identificable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**Gestiona mapas de identificables (clave, identificable) para multiples tipos de identificables.
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 * @see Identificable
 */
public class GestorIdentificables {
	@SuppressWarnings("rawtypes")
	private Map<Class, Map> mapaIdentificables = new HashMap<>();

	@SuppressWarnings("unchecked")
	protected <K extends Comparable<K>, T extends Identificable<K>> Map<K, T> getMapaParaClase(Class<T> clase) {
		return getMapaIdentificables().get(clase);
	}
	
	public <K extends Comparable<K>, T extends Identificable<K>> void addIdentificable(Class<T> clase, T identificable){
		Map<K, T> mapa = getMapaParaClase(clase);
		// Si no existe el mapa para esta clase lo creo y lo controlo
		if (mapa == null) {
			mapa = new HashMap<>();
			getMapaIdentificables().put(clase, mapa);
		}
		
		mapa.put(identificable.getIdentificador(), identificable);
	}
	
	@SuppressWarnings("rawtypes")
	protected Map<Class, Map> getMapaIdentificables() {
		return mapaIdentificables;
	}

	/**Devuelve los identificables que controla el gestor para la clase pedida
	 * @param <T> Tipo especifico que implementa {@link Identificable}
	 * @param clase de los identificables solicitados
	 * @return Collection con los identificables solicitados 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T extends Identificable> Collection<T> getIdentificables(Class<T> clase) {
		return getMapaParaClase(clase).values();
	}
	
	
	/**Devuelve los identificables que controla el gestor para la clase pedida ordenados por el comparador
	 * @param <T> Tipo especifico que implementa {@link Identificable}
	 * @param clase de los identificables solicitados
	 * @param comparador por el que se debe ordenar la lista de identificadores
	 * @return List con los identificadores ordenados
	 */
	@SuppressWarnings("rawtypes")
	public <T extends Identificable> List<T> getIdentificablesOrdenados(Class<T> clase, Comparator<T> comparador) {
		List<T> lista = new ArrayList<>(getIdentificables(clase));
		//Los ordeno por el comparador
		Collections.sort(lista, comparador);
		
		return lista;
	}

	/**Devuelve un Identificable que cumpla con los argumentos de la llamada
	 * @param <T> Tipo especifico que implementa {@link Identificable}
	 * @param <K> Tipo del identificador para el tipo {@code T}
	 * @param clase del identificable
	 * @param id identificador del Identificable
	 * @return Identificable de la clase y con el id pedido o null si no existe
	 */
	public <K extends Comparable<K>, T extends Identificable<K>> T getIdentificable(Class<T> clase, K id){
		return getMapaParaClase(clase).get(id);
	}
	
}
