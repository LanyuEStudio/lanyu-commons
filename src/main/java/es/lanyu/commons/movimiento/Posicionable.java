package es.lanyu.commons.movimiento;

/**Interfaz para posicionar objetos en dos dimensiones
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public interface Posicionable {
	
	/**Devuelve la coordenada X del objeto
	 * @return Coordenada X del objeto
	 */
	default float getX() { return 0; }

	/**Devuelve la coordenada Y del objeto
	 * @return Coordenada Y del objeto
	 */
	default float getY() { return 0; }

}
