package es.lanyu.commons.movimiento;

import es.lanyu.commons.math.Vector2d;

/**Interfaz que extiende {@link Movible} utilizando un {@link Vector2d} para
 * controlar la velocidad.
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public interface MovibleConVelocidad extends Movible{

	Vector2d getVelocidad();
	
	@Override
	default float getVelocidadX() {
		return getVelocidad().getX();
	}

	default void setVelocidadX(float x) {
		getVelocidad().setX(x);
	}

	@Override
	default float getVelocidadY() {
		return getVelocidad().getY();
	}

	default void setVelocidadY(float y) {
		getVelocidad().setY(y);
	}
	
	@Override
	default void detener() {
		getVelocidad().setX(0);
		getVelocidad().setY(0);
	}
}
