package es.lanyu.commons.movimiento;

import es.lanyu.commons.movimiento.PosicionableConPosicion;

public interface Desplazable extends PosicionableConPosicion, MovibleConVelocidad{

	default void mover(float deltaTime){
		getPosicion().suma(getVelocidad().getCopiaMultiplicada(deltaTime));
	}
	
}
