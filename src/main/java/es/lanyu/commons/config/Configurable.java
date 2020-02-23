package es.lanyu.commons.config;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Collection;

/**Interfaz que asocia un tipo a {@link Propiedades} de tipo clave-valor.
 * Permite comunicar eventos {@link PropertyChangeEvent} a los {@link PropertyChangeListener}
 * que se establezcan.
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public interface Configurable {
	static String RUTA_PROPIEDADES_POR_DEFECTO = "data/config.properties";
	
	/**Devuelve un {@link File} con la ruta por defecto para la lectura de {@link Propiedades}.
	 * El valor por defecto es {@code "data/config.properties"}
	 * @return {@code File} al archivo con las propiedades
	 */
	static File getRutaPorDefecto() { return new File(RUTA_PROPIEDADES_POR_DEFECTO); }
	
	/**
	 * @return Coleccion de listeners que vigilan los cambios de propiedades 
	 */
	Collection<PropertyChangeListener> getListeners();
	
	/**Devuelve las {@link Propiedades} del objeto {@code Configurable}
	 * @return {@code Propiedades} del objeto
	 */
	Propiedades getPropiedades();

	/**Guarda las {@link Propiedades} almacenadas en la ruta por defecto
	 */
	default void guardarConfiguracion(){
		guardarConfiguracion(getRutaPorDefecto().toString());
	}

	/**Guarda las {@link Propiedades} almacenadas en la {@code ruta} especificada
	 * @param ruta Ruta al archivo donde guardar las propiedades
	 */
	default void guardarConfiguracion(String ruta){
		getPropiedades().guardarPropiedades(ruta);
	}
	
	/**Actualiza una propiedad de las {@link Propiedades} almacenadas y se lo comunica a los listeners.
	 * @param <T> tipo del valor para actualizar
	 * @param idPropiedad clave de la propiedad
	 * @param valor al que actualizar la propiedad
	 */
	default <T> void actualizarPropiedad(String idPropiedad, T valor) {
		PropertyChangeEvent evento = new PropertyChangeEvent(this, idPropiedad, getPropiedades().get(idPropiedad), valor);
		getPropiedades().actualizarPropiedad(idPropiedad, valor.toString());
		getListeners().forEach(l -> l.propertyChange(evento));
	}
	
	default void addPropiedadListener(PropertyChangeListener listener) {
		getListeners().add(listener);
	}
	
	default void removePropiedadListener(PropertyChangeListener listener) {
		getListeners().remove(listener);
	}
}