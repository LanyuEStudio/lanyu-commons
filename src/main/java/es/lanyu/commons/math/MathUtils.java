package es.lanyu.commons.math;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Clase de utilidad para realizar operaciones matematicas como
 * {@link MathUtils#horquillar(Comparable, Comparable, Comparable)} entre dos
 * valores, redondeos o dividir un arco en {@code N} puntos.
 * 
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0.3
 * @since 1.0
 */
public class MathUtils {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.##", DecimalFormatSymbols.getInstance(Locale.getDefault()));
    
    /**
     * Devuelve el valor que estara entre {@code min} y {@code max}. Al ser un
     * metodo generico puede compararse cualquier tipo {@link Comparable}
     * 
     * @param <T> Debe ser un tipo que pueda compararse
     * @param val Valor de inicio. Puede exceder de los limites
     * @param min Valor minimo del resultado
     * @param max Valor maximo del resultado
     * @return Valor que se aproxima a {@code val} sin exceder los limites
     */
    public static <T extends Comparable<T>> T horquillar(T val, T min, T max) {
        if (val.compareTo(min) < 0) return min;
        else if (val.compareTo(max) > 0) return max;
        else return val;
    }

    /**
     * Calcula una {@link Collection} de dimension {@code numeroPuntos} de tipo
     * {@link Punto} repartidos por el arco definido por los argumentos pasados
     * 
     * @param tipo {@code T} implementando {@link Vector2d} para usar
     * @param xCentro Coordenada X del centro del arco
     * @param yCentro Coordenada Y del centro del arco
     * @param radio Valor del radio del arco
     * @param numeroPuntos Numero de puntos entre los que divir el arco
     * @param inclinacionInicial Angulo inicial del que empezar a dividir
     * @param anguloArco Angulo del arco (no tiene que ser toda la circunferencia)
     * @param sentidoDelReloj {@code true} para dividir en sentido horario, si no {@code false}
     * @param <T> Tipo {@code T} que debe tener un constructor con parametros (int, int) si no arrojara una excepcion
     * @return {@code Collection<Point>} con los puntos que dividen el arco en partes iguales
     */
    public static <T extends Vector2d> Collection<T> generarPuntosEnArcoCircular(Class<T> tipo,
            double xCentro, double yCentro, double radio, int numeroPuntos,
            double inclinacionInicial, double anguloArco, boolean sentidoDelReloj) {
        Collection<T> coordenadas = new LinkedHashSet<>();
        int[][] coorCrudas = generarPuntosEnArcoCircularCrudos(xCentro, yCentro, radio, numeroPuntos,
                inclinacionInicial, anguloArco, sentidoDelReloj);
        try {
            Constructor<T> constructor = tipo.getConstructor(Float.class, Float.class);
            for (int i = 0; i < coorCrudas.length; i++) {
                coordenadas.add(constructor.newInstance(coorCrudas[i][0] * 1f, coorCrudas[i][1] * 1f));
            }
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e1) {
            e1.printStackTrace();
        }

        return coordenadas;
    }

    /**
     * Metodo auxiliar que reliza el calculo pedido por
     * {@link MathUtils#generarPuntosEnArcoCircular(double, double, double, int, double, double, boolean)}
     * pero devuelve un array bidimensional con las coordenadas de los puntos
     * que luego son pasadas a {@link Point}.
     * 
     * @param xCentro Coordenada X del centro del arco
     * @param yCentro Coordenada Y del centro del arco
     * @param radio Valor del radio del arco
     * @param numeroPuntos Numero de puntos entre los que divir el arco
     * @param inclinacionInicial Angulo inicial del que empezar a dividir
     * @param anguloArco Angulo del arco (no tiene que ser toda la circunferencia)
     * @param sentidoDelReloj {@code true} para dividir en sentido horario, si no {@code false}
     * @return Array bidimensional con las coordenadas que dividen el arco en partes iguales
     */
    private static int[][] generarPuntosEnArcoCircularCrudos(double xCentro, double yCentro, double radio,
            int numeroPuntos, double inclinacionInicial, double anguloArco, boolean sentidoDelReloj) {
        int[][] coordenadas = new int[numeroPuntos][2];
        double anguloEntrePuntos = 0;
        if (numeroPuntos > 1)
            anguloEntrePuntos = anguloArco / (numeroPuntos - 1) * ((sentidoDelReloj) ? -1 : 1);

        for (int i = 0; i < numeroPuntos; i++) {
            double a = i * anguloEntrePuntos + inclinacionInicial;
            // Coordenada X
            coordenadas[i][0] = (int) (radio * Math.sin(a) + xCentro);
            // Coordenada Y
            coordenadas[i][1] = (int) (radio * Math.cos(a) + yCentro);
        }

        return coordenadas;
    }

    public static String floatEnEntero(float f) {
        return floatEnEntero(f, true);
    }

    public static String floatEnEntero(float f, boolean devolverNegativo) {
        if (devolverNegativo) return Math.round(f) + "";
        else return (f > 0) ? Math.round(f) + "" : "";
    }

    // Ver https://stackoverflow.com/a/51256264
    // El redondeo puede hacer que en valores muy cercanos max sea < que min
    // Con los comentarios actuales funciona con tandas largas
    public static float generarFloatRandom(float min, float max) {
        // if (min >= max)
        // throw new IllegalArgumentException("max must be greater than min");
        float result = nextFloat() * (max - min) + min;
        // if (result >= max) // correct for rounding
        // result = Float.intBitsToFloat(Float.floatToIntBits(max) - 1);
        return result;
    }

    public static float nextFloat() {
        return ThreadLocalRandom.current().nextFloat();
    }
    
    public static String decimalToString(Number numero) {
        return DECIMAL_FORMAT.format(numero);
    }
    
    /**
     * Devuelve las soluciones para una ecuacion de segundo grado con los coeficientes
     * pasados por parametro: {@code ax^2 + bx + c = 0}.
     * @param a coeficiente  cuadratico  o  de  segundo  grado
     * @param b coeficiente lineal o de primer grado
     * @param c termino independiente
     * @return array con las dos soluciones si las hay
     */
    public static double[] solucionEcuacionSegundoGrado(double a, double b, double c) {
        double dosA = 2*a;
        double raiz = Math.sqrt(b*b - (2*dosA*c));
        
        return new double[] { (-b + raiz)/dosA, (-b - raiz)/dosA };
    }
    
}
