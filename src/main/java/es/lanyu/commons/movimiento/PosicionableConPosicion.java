package es.lanyu.commons.movimiento;

import es.lanyu.commons.math.Vector2d;

public interface PosicionableConPosicion extends Posicionable {

	Vector2d getPosicion();
	
	default float getX() {
		return getPosicion().getX();
	}

	default void setX(float x) {
		getPosicion().setX(x);
	}

	default float getY() {
		return getPosicion().getY();
	}

	default void setY(float y) {
		getPosicion().setY(y);
	}
	
}
