package es.lanyu.commons.math;

/**Extiende la interfaz {@link Vector2d} para añadir la coordenada Z para un entorno de tres dimensiones.
 * Contiene metodos para realizar operaciones de suma y escalado de vectores.
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public interface Vector3d extends Vector2d {

	float getZ();
	void setZ(float z);

	default <V extends Vector2d> void setDesdeVector(Vector3d vector) {
		Vector2d.super.setDesdeVector(vector);
		setZ(vector.getZ());
	}
	
	default <V extends Vector2d> V producto (float multiplicador) {
		setZ(getZ()*multiplicador);
		
		return (V) Vector2d.super.producto(multiplicador);
	}
	
	default <V extends Vector2d> V suma (Vector3d sumando) {
		setZ(getZ() + sumando.getZ());
		
		return (V) Vector2d.super.suma(sumando);
	}
	
	default  <V extends Vector2d> V resta (Vector3d sumando) {
		setZ(getZ() - sumando.getZ());
		
		return (V) Vector2d.super.resta(sumando);
	}
}
