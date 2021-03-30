package es.lanyu.commons.servicios.entidad;

import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.lanyu.commons.identificable.AbstractNombrable;
import es.lanyu.commons.identificable.GeneradorIdentificadores;
import es.lanyu.commons.identificable.GeneradorIdentificadoresString;
import es.lanyu.commons.identificable.GestorNombrables;
import es.lanyu.commons.identificable.Identificable;

/**Interfaz para dar servicio con la gestion de entidades de tipo {@link Identificable}.
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public interface ServicioEntidad {
	/**{@link Logger} de {@code ServicioEntidad}.
	 */
	Logger SERVICIO_ENTIDAD_LOG = Logger.getLogger(ServicioEntidad.class.getName());

	/**Devuelve el {@link GestorNombrables} que utiliza para administrar las entidades
	 * @return {@code GestorNombrables} que utiliza
	 */
	GestorNombrables getGestorNombrables();
	
	/**Devuelve el mapa con los {@link GeneradorIdentificadores} que usa para cada clase.
	 * @return {@code Map<Class, GeneradorIdentificadores} que usa
	 */
	@SuppressWarnings("rawtypes")
	Map<Class, GeneradorIdentificadores> getMapaGeneradores();
	
	/**Devuelve los elementos registrados como recursos del tipo {@code clase}.
	 * @param <K> Tipo del identificador del tipo {@code T}
	 * @param <T> Tipo del {@code Identificable & Nombrable}
	 * @param clase Tipo usado para referenciar los recursos buscados
	 * @return {@code Collection<T>} que contiene los recursos buscados
	 */
	default <K extends Comparable<K>, T extends Identificable<K>> Collection<T> getElementosRegistradosDe(Class<T> clase) {
		return getGestorNombrables().getIdentificables(clase);
	}
	
	/**Devuelve el {@link Identificable} con el {@code id} de tipo {@code K} requerido o {@code null}.
	 * Si no se encuentra el {@code id} se emitira un mensaje en {@link ServicioEntidad#SERVICIO_ENTIDAD_LOG}
	 * de nivel {@link Level#INFO}.
	 * @param <K> Tipo del identificador del tipo {@code T}
	 * @param <T> Tipo del {@code Identificable & Nombrable}
	 * @param clase Tipo usado para referenciar el recurso buscado
	 * @param id Identificador del {@code Identificable} buscado
	 * @return {@code Identificable} buscado o {@code null} si no se encuentra
	 */
	default <K extends Comparable<K>, T extends Identificable<K>> T getIdentificable(Class<T> clase, K id) {
		T elemento = getGestorNombrables().getIdentificable(clase, id);
		if(elemento == null)
			SERVICIO_ENTIDAD_LOG.log(Level.INFO, "No se encuentra ID:" + id);
		
		return elemento;
	}
	
	/**Utiliza la implementacion {@link AbstractNombrable} para generar un objeto del tipo {@code clase}.
	 * Se generara un id con el {@link GeneradorIdentificadores} apropiado (de tipo {@code String} y lo
	 * agrega al {@link GestorNombrables} de este servicio.
	 * con el {@code nombre} pasado.
	 * @param <T> Tipo de {@code AbstractNombrable} que se quiere crear
	 * @param clase Tipo que se quiere crear
	 * @param nombre Nombre que se quiere establecer
	 * @return Objeto creado con los datos proporcionados
	 */
	@SuppressWarnings("rawtypes")
	default <T extends AbstractNombrable> T generarNombrable(Class<T> clase, String nombre) {
		T elemento = null;
		try {
			elemento = clase.newInstance();
			GeneradorIdentificadores generaIds = getGeneradorIdsParaClase(clase);
			do {
				elemento.setIdentificador((String) generaIds.generarId());
			} while (getGestorNombrables().getIdentificable(clase, elemento.getIdentificador()) != null);
			elemento.setNombre(nombre);
			getGestorNombrables().addIdentificable(clase, elemento);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return elemento;
	}
	
	/**Genera un {@code GeneradorIdentificadores<String>} por defecto para la {@code clase} y
	 * lo almacena con el resto de generadores o recupera el existente asociado a esa clase.
	 * @param <T> Tipo de {@code Identificable<String>} que se quiere crear
	 * @param clase Clase para la que crear el Generador
	 * @return {@code GeneradorIdentificadores<String>} creado o recuperado
	 */
	default <T extends Identificable<String>> GeneradorIdentificadores<String>
		getGeneradorIdsParaClase(Class<T> clase) {
		return crearGeneradorIdsParaClase(clase, false);
	}
	
	/**Genera un {@link GeneradorIdentificadores} por defecto para la {@code clase} y
	 * lo almacena con el resto de generadores o recupera el existente asociado a esa clase en
	 * funcion de si se quiere uno {@code nuevo} o no.
	 * @param <T> Tipo de {@code Identificable<String>} que se quiere crear
	 * @param clase Clase para la que generar el Generador
	 * @param nuevo si se quiere enlazar con un nuevo generador independientemente de si existe uno
	 * @return {@code GeneradorIdentificadores<String>} creado
	 */
	@SuppressWarnings("unchecked")
	default <T extends Identificable<String>> GeneradorIdentificadores<String>
		crearGeneradorIdsParaClase(Class<T> clase, boolean nuevo) {
		GeneradorIdentificadores<String> generador;
		if (nuevo || (generador = getMapaGeneradores().get(clase)) == null) {
			generador = new GeneradorIdentificadoresString();
			getMapaGeneradores().put(clase, generador);
		}
		
		return generador;
	}
}
