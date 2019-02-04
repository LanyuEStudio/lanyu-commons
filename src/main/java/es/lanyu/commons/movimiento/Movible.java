package es.lanyu.commons.movimiento;

public interface Movible {

	float getVelocidadX();
	
	float getVelocidadY();
	
	void mover(float deltaTime);
}
