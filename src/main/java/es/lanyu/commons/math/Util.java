package es.lanyu.commons.math;

import java.awt.Point;
import java.util.Collection;
import java.util.LinkedHashSet;

/**Clase de utilidad para realizar operaciones matematicas como
 * {@link Util#horquillar(Comparable, Comparable, Comparable)} entre dos valores,
 * redondeos o dividir un arco en {@code N} puntos.
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public class Util {
		
	/**Devuelve el valor que estara entre {@code min} y {@code max}. Al ser un metodo generico
	 * puede compararse cualquier tipo {@link Comparable}
	 * @param <T> Debe ser un tipo que pueda compararse
	 * @param val Valor de inicio. Puede exceder de los limites
	 * @param min Valor minimo del resultado
	 * @param max Valor maximo del resultado
	 * @return Valor que se aproxima a {@code val} sin exceder los limites
	 */
	public static <T extends Comparable<T>> T horquillar (T val, T min, T max) {
	    if (val.compareTo(min) < 0) return min;
	    else if (val.compareTo(max) > 0) return max;
	    else return val;
	}
	
	/**Calcula una {@link Collection} de tamaño {@code numeroPuntos} de tipo {@link Point} repartidos
	 * por el arco definido por los argumentos pasados
	 * @param xCentro Coordenada X del centro del arco
	 * @param yCentro Coordenada Y del centro del arco
	 * @param radio Valor del radio del arco
	 * @param numeroPuntos Numero de puntos entre los que divir el arco
	 * @param inclinacionInicial Angulo inicial del que empezar a dividir
	 * @param anguloArco Angulo del arco (no tiene que ser toda la circunferencia)
	 * @param sentidoDelReloj {@code true} para dividir en sentido horario, si no {@code false}
	 * @return {@code Collection<Point>} con los puntos que dividen el arco en partes iguales
	 */
	public static Collection<Point> generarPuntosEnArcoCircular(double xCentro, double yCentro, double radio, int numeroPuntos,
			double inclinacionInicial, double anguloArco, boolean sentidoDelReloj){
		Collection<Point> coordenadas = new LinkedHashSet<>();
		int[][] coorCrudas = generarPuntosEnArcoCircularCrudos(xCentro, yCentro, radio, numeroPuntos, inclinacionInicial, anguloArco, sentidoDelReloj);
		for(int i = 0; i < coorCrudas.length; i++){
			coordenadas.add(new Point(coorCrudas[i][0], coorCrudas[i][1]));
		}
		
		return coordenadas;
	}
	
	/**Metodo auxiliar que reliza el calculo pedido por
	 * {@link Util#generarPuntosEnArcoCircular(double, double, double, int, double, double, boolean)}
	 * pero devuelve un array bidimensional con las coordenadas de los puntos que luego son pasadas a {@link Point}. 
	 * @param xCentro Coordenada X del centro del arco
	 * @param yCentro Coordenada Y del centro del arco
	 * @param radio Valor del radio del arco
	 * @param numeroPuntos Numero de puntos entre los que divir el arco
	 * @param inclinacionInicial Angulo inicial del que empezar a dividir
	 * @param anguloArco Angulo del arco (no tiene que ser toda la circunferencia)
	 * @param sentidoDelReloj {@code true} para dividir en sentido horario, si no {@code false}
	 * @return Array bidimensional con las coordenadas que dividen el arco en partes iguales
	 */
	private static int[][] generarPuntosEnArcoCircularCrudos(double xCentro, double yCentro, double radio, int numeroPuntos,
			double inclinacionInicial, double anguloArco, boolean sentidoDelReloj){
		int[][] coordenadas = new int[numeroPuntos][2];
		double anguloEntrePuntos = 0;
		if(numeroPuntos > 1)
			anguloEntrePuntos = anguloArco/(numeroPuntos-1) * ((sentidoDelReloj)?-1:1);
		
		for(int i = 0; i < numeroPuntos; i++){
			double a = i * anguloEntrePuntos + inclinacionInicial;
			//Coordenada X
			coordenadas[i][0] = (int)(radio * Math.sin(a) + xCentro);
			//Coordenada Y
			coordenadas[i][1] = (int)(radio * Math.cos(a) + yCentro);
		}
		
		return coordenadas;
	}

	public static String floatEnEntero(float f){
		return floatEnEntero(f, true);
	}
	
	public static String floatEnEntero(float f, boolean devolverNegativo){
		if (devolverNegativo)
			return Math.round(f)+"";
		else
			return (f>0)?Math.round(f)+"":"";
	}
}
