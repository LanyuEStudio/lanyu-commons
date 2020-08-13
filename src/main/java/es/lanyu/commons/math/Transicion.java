package es.lanyu.commons.math;

import java.util.function.Function;

/**
 * Permite crear una transicion entre dos estados y actualizar su estado en
 * funcion del tiempo transcurrido. Tambien permite invertir la transicion.
 * Al usar valores {@code float} permite definir multiples transiciones (puede
 * usarse para movimiento usando transiciones para cada coordenada o mutar de un
 * color a otro creando una transicion para cada componente R, G y B)
 * 
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0.3
 * @since 1.0
 */
public class Transicion {
    private float valorInicial;
    private float valorFinal;
    private Function<Float, Float> funcion;

    private float deltaPorMiliSegundo;
    private boolean finActualizacion = false;
    private float valorActual;

    protected Function<Float, Float> getFuncion() {
        return funcion;
    }

    public float getValorActual() {
        return valorActual;
    }

    private void setValorActual(float valorActual) {
        this.valorActual = valorActual;
    }

    public boolean esFinActualizacion() {
        return finActualizacion;
    }

    public void setFinActualizacion(boolean finActualizacion) {
        this.finActualizacion = finActualizacion;
    }

    public static void actualizarTransiciones(Transicion[] transiciones, float delta) {
        for (Transicion t : transiciones) {
            t.updateValor(delta);
        }
    }

    public Transicion(float valorInicial, float valorFinal, float miliSegundosParaTransicion,
            Function<Float, Float> funcion) {
        this(valorInicial, valorFinal, miliSegundosParaTransicion);
        this.funcion = funcion;
    }

    public Transicion(float valorInicial, float valorFinal, float miliSegundosParaTransicion) {
        super();
        this.valorInicial = valorInicial;
        this.valorFinal = valorFinal;
        this.funcion = t -> deltaPorMiliSegundo * t;
        setValorActual(valorInicial);

        deltaPorMiliSegundo = (valorFinal - valorInicial) / miliSegundosParaTransicion;
    }

    public float updateValor(float delta) {
        if (!finActualizacion) {
            setValorActual(getValorActual() + getFuncion().apply(delta));

            if (deltaPorMiliSegundo > 0)
                finActualizacion = getValorActual() >= valorFinal;
            else
                finActualizacion = getValorActual() <= valorFinal;

            if (finActualizacion)
                setValorActual(valorFinal);
        }

        return getValorActual();
    }

    public void flipTransicion() {
        valorFinal = valorInicial;
        valorInicial = getValorActual();
        finActualizacion = false;
        deltaPorMiliSegundo = -deltaPorMiliSegundo;
    }

}
