package es.lanyu.commons.math;

/**Implementacion basice de {@link Vector2d}.
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public class Punto implements Vector2d {
	float x, y;
	
	public Punto() {}
	
	protected Punto(Vector2d vector) {
		this(vector.getX(), vector.getY());
	}
	
	public Punto(float x, float y) {
		setX(x);
		setY(y);
	}
	
	public Punto(Float x, Float y) {
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

