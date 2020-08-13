package es.lanyu.test.math;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import es.lanyu.commons.math.MathUtils;
import es.lanyu.commons.math.Transicion;
import es.lanyu.commons.tiempo.DatableInstant;

/**
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0.3
 */
public class MathTest {
    int numeroRandomGenerados = 10;
    float min = -99999f;// Float.MIN_VALUE;
    float max = 99999f;// Float.MAX_VALUE;
    
    @Test
    public void randomFloat() {
        int testRealizados = 0;
        float minAux = min, maxAux = max;
        do {
            System.out.println("Test numero: " + testRealizados++);
            comprobarMinMax();
        } while (testRealizados < numeroRandomGenerados);
        assertTrue("Ultimo valor entre primeros [" + minAux + " | " + min + " | " + max + " | " + maxAux + "]",
                min >= minAux && max <= maxAux);
        System.out.println("Ultimo min: " + min + " y max: " + max);
    }
    
    public void comprobarMinMax() {
        float aux = MathUtils.generarFloatRandom(min, max);
        assertTrue("Random " + aux + " NO mayor que min: " + min, aux >= min);
        System.out.println(aux);
        min = aux;
        aux = MathUtils.generarFloatRandom(min, max);
        assertTrue("Random " + aux + " NO menor que max: " + max, aux <= max);
        System.out.println(aux);
        max = aux;
    }
    
    @Test
    public void transicionTest() {
        Transicion transicion = new Transicion(0, 1, 1000);
        long deltaTotal = ejecutarTransicion(transicion);
        System.out.println("Transicion: Finalizada en " + deltaTotal + "ms con valor: " + transicion.getValorActual());
        transicion.flipTransicion();
        deltaTotal = ejecutarTransicion(transicion);
        System.out.println("Transicion invertida: Finalizada en " + deltaTotal + "ms con valor: " + transicion.getValorActual());
    }
    
    private long ejecutarTransicion(Transicion transicion) {
        long deltaTotal = 0;
        DatableInstant inicio = new DatableInstant(), fin;
        while (!transicion.esFinActualizacion()) {
            fin = new DatableInstant();
            long delta = inicio.duracionHasta(fin).toMillis();
            transicion.updateValor(delta);
//            System.out.println("Actualizando valor en " + delta + "ms hasta " + transicion.getValorActual());
            inicio = fin;
            deltaTotal += delta;
        }
        return deltaTotal;
    }

}
