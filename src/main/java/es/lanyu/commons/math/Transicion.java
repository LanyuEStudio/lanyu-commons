package es.lanyu.commons.math;

import java.util.function.Function;

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
