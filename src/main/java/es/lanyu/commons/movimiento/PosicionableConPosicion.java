package es.lanyu.commons.movimiento;

import es.lanyu.commons.math.Vector2d;

/**Interfaz que extiende {@link Posicionable} utilizando un {@link Vector2d} para
 * controlar la posicion.
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public interface PosicionableConPosicion extends Posicionable {

	Vector2d getPosicion();
	
	@Override
	default float getX() {
		return getPosicion().getX();
	}

	default void setX(float x) {
		getPosicion().setX(x);
	}

	@Override
	default float getY() {
		return getPosicion().getY();
	}

	default void setY(float y) {
		getPosicion().setY(y);
	}
	
}
