package es.lanyu.commons.math;

import java.util.function.Function;

/**Permite crear una transicion entre dos estados y actualizar su estado en funcion del tiempo
 * transcurrido. Tambien permite invertir la transicion. Al usar valores {@code float} permite
 * definir multiples transiciones (puede usarse para movimiento usando transciones para cada coordenada
 * o mutar de un color a otro creando una transicion para cada componente R, G y B)
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public class Transicion {
	float valorInicial;
	float valorFinal;
	float miliSegundosParaTransicion;
	Function<Float, Float> funcion;
	
	float deltaPorMiliSegundo;
	boolean finActualizacion = false;
	float valorActual;
	
	public float getValorActual() {
		return valorActual;
	}
	
	public boolean esFinActualizacion() {
		return finActualizacion;
	}
	
	public void setFinActualizacion(boolean finActualizacion) {
		this.finActualizacion = finActualizacion;
	}
	
	public static void actualizarTransiciones(Transicion[] transiciones, float delta){
		for(Transicion t : transiciones){
			t.updateValor(delta);
		}
	}
	
	public <T, R> Transicion(float valorInicial, float valorFinal, float miliSegundosParaTransicion, Function<Float, Float> funcion){
		this(valorInicial, valorFinal, miliSegundosParaTransicion);
		this.funcion = funcion;
	}
	
	public Transicion(float valorInicial, float valorFinal, float miliSegundosParaTransicion) {
		super();
		this.valorInicial = valorInicial;
		this.valorFinal = valorFinal;
		this.miliSegundosParaTransicion = miliSegundosParaTransicion;
		this.funcion = new Function<Float, Float>() {
				@Override
				public Float apply(Float t) {
					return deltaPorMiliSegundo*t;
				}
			};
		valorActual = valorInicial;
		
		deltaPorMiliSegundo = (valorFinal - valorInicial)/this.miliSegundosParaTransicion;
	}
	
	public float updateValor(float delta){
		if(!finActualizacion){
			valorActual += funcion.apply(delta);

			if(deltaPorMiliSegundo > 0)
				finActualizacion = valorActual >= valorFinal;
			else 
				finActualizacion = valorActual <= valorFinal;

			if(finActualizacion)
				valorActual = valorFinal;
		}
		
		return valorActual;
	}
	
	public void flipTransicion(){
		valorFinal = valorInicial;
		valorInicial = valorActual;
		finActualizacion = false;
	}
	
}
