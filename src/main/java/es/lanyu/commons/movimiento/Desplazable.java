package es.lanyu.commons.movimiento;

import es.lanyu.commons.movimiento.PosicionableConPosicion;

/**Interfaz que implementa el movimiento en dos dimensiones al extender
 * {@link PosicionableConPosicion} y {@link MovibleConVelocidad}
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public interface Desplazable extends PosicionableConPosicion, MovibleConVelocidad{

	@Override
	default void mover(float deltaTime){
		getPosicion().suma(getVelocidad().getCopiaMultiplicada(deltaTime));
	}
	
}
