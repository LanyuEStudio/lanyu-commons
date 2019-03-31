package es.lanyu.commons.config;

import java.io.File;

/**Interfaz que asocia un tipo a {@link Propiedades} de tipo clave-valor
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public interface Configurable {

	/**Devuelve las {@link Propiedades} del objeto {@code Configurable}
	 * @return {@code Propiedades} del objeto
	 */
	Propiedades getPropiedades();

	/**Devuelve un {@link File} con la ruta por defecto para la lectura de {@link Propiedades}.
	 * El valor por defecto es {@code "data/config.properties"}
	 * @return {@code File} al archivo con las propiedades
	 */
	default File getRutaPorDefecto() { return new File("data/config.properties"); }

	/**Guarda las {@link Propiedades} almacenadas en la ruta por defecto
	 */
	default void guardarConfiguracion(){
		guardarConfiguracion(getRutaPorDefecto().getAbsolutePath());
	}

	/**Guarda las {@link Propiedades} almacenadas en la {@code ruta} especificada
	 * @param ruta Ruta al archivo donde guardar las propiedades
	 */
	default void guardarConfiguracion(String ruta){
		Propiedades.guardarPropiedades(getPropiedades(), ruta);
	}
}