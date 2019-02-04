package es.lanyu.commons.math;

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
