package es.lanyu.commons.math;

public interface Vector2d {

	float getX();
	float getY();
	void setX(float x);
	void setY(float y);
	<V extends Vector2d> V getCopia();

	default <V extends Vector2d> void setDesdeVector(V vector) {
		setX(vector.getX());
		setY(vector.getY());
	}
	
	default <V extends Vector2d> V producto (float multiplicador) {
		setX(getX()*multiplicador);
		setY(getY()*multiplicador);
		
		return (V) this;
	}
	
	default  <V extends Vector2d> V suma (V sumando) {
		setX(getX() + sumando.getX());
		setY(getY() + sumando.getY());
		
		return (V) this;
	}
	
	default  <V extends Vector2d> V resta (V sumando) {
		setX(getX() - sumando.getX());
		setY(getY() - sumando.getY());
		
		return (V) this;
	}
	
	default <T extends Vector2d> T getCopiaMultiplicada(float multiplicador) {
		T vecAux = getCopia();
		vecAux.producto(multiplicador);
		
		return vecAux;
	}
	
	default <T extends Vector2d> T  getCopiaSumada(Punto sumando) {
		T vecAux = getCopia();
		vecAux.suma(sumando);
		
		return vecAux;
	}

	default <T extends Vector2d> T getCopiaResta(Punto restando) {
		return getCopiaSumada(restando.getCopiaMultiplicada(-1));
	}
	
	default float pendiente() {
		return getY()/getX();
	}
	
	default float getModulo(){
		return (float) Math.sqrt(getX()*getX() + getY()*getY());
	}
}
