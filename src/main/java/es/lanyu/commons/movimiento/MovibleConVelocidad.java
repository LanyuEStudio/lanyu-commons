package es.lanyu.commons.movimiento;

import es.lanyu.commons.math.Vector2d;

public interface MovibleConVelocidad extends Movible{

	Vector2d getVelocidad();
	
	default float getVelocidadX() {
		return getVelocidad().getX();
	}

	default void setVelocidadX(float x) {
		getVelocidad().setX(x);
	}

	default float getVelocidadY() {
		return getVelocidad().getY();
	}

	default void setVelocidadY(float y) {
		getVelocidad().setY(y);
	}
	
	default void detener() {
		getVelocidad().setX(0);
		getVelocidad().setY(0);
	}
}
