package es.lanyu.commons.identificable;

/**Interfaz para generador de claves de tipo K (normalmente para generacion de ids de {@link Identificable}).
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public interface GeneradorIdentificadores<K> {

	K generarID();

}
