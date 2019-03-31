package es.lanyu.commons.movimiento;

/**Interfaz para objetos con velocidad que pueden moverse.
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public interface Movible {

	/**Devuelve la velocidad en el eje X
	 * @return Velocidad en el eje X
	 */
	float getVelocidadX();
	
	/**Devuelve la velocidad en el eje Y
	 * @return Velocidad en el eje Y
	 */
	float getVelocidadY();
	
	/**Mueve el objeto durante el tiempo pedido
	 * @param deltaTime nanosegundos que se ha movido el objeto
	 */
	void mover(float deltaTime);
	
	/**Detiene el objeto estableciendo su velocidad a cero.
	 */
	void detener();
}
