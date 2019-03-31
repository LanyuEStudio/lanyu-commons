package es.lanyu.commons.identificable;

/**Interfaz para generador de claves de tipo K (normalmente para generacion de ids de {@link Identificable}).
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 * @param <K> Tipo del identificador a generar
 */
public interface GeneradorIdentificadores<K> {

	/**Genera un identificador del tipo {@code K} 
	 * @return Identificador de tipo {@code K}
	 */
	K generarId();

}
