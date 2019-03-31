package es.lanyu.commons.servicios.entidad;

import java.util.HashMap;
import java.util.Map;

import es.lanyu.commons.identificable.GeneradorIdentificadores;
import es.lanyu.commons.identificable.GestorNombrables;

/**Implementacion basica de la interfaz {@link ServicioEntidad}.
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public class ServicioEntidadImpl implements ServicioEntidad {
	private GestorNombrables gestorIdentificables;
	@SuppressWarnings("rawtypes")
	Map<Class, GeneradorIdentificadores> mapaGeneradores;
	
	public ServicioEntidadImpl() {
		gestorIdentificables = new GestorNombrables();
		mapaGeneradores = new HashMap<>();
	}
	
	@Override
	public GestorNombrables getGestorNombrables() {
		return gestorIdentificables;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<Class, GeneradorIdentificadores> getMapaGeneradores() {
		return mapaGeneradores;
	}

}
