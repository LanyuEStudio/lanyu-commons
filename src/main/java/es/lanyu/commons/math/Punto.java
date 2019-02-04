package es.lanyu.commons.math;

public class Punto implements Vector2d {
	float x, y;
	
	protected Punto(Vector2d vector) {
		this(vector.getX(), vector.getY());
	}
	
	public Punto(float x, float y) {
		setX(x);
		setY(y);
	}

	public float getX() { return x; }
	
	public float getY() { return y;	}
	
	public void setX(float x) {	this.x = x; }
	
	public void setY(float y) {	this.y = y; }
	
	@SuppressWarnings("unchecked")
	public <T extends Vector2d> T getCopia() {
		return (T) new Punto(this);
	}
	
	@Override
	public String toString() {
		return String.format("(X:%.2f Y:%.2f)", getX(), getY());
	}
	
}

