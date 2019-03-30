package es.lanyu.commons.identificable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**Gestiona mapas de nombrables para extender la funcionalidad de 
 * {@link GestorIdentificables} a {@link Nombrable}.
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public class GestorNombrables extends GestorIdentificables {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T extends Identificable & Nombrable> List<T> getIdentificablePorNombre(Class<T> clase, String nombre){
		return ((Map<?, T>)getMapaParaClase(clase)).values().stream()
									.filter(n -> n.getNombre().equals(nombre))
									.collect(Collectors.toList());
	}
	
}
