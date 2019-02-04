package es.lanyu.commons.math;

import java.awt.Point;
import java.util.Collection;
import java.util.LinkedHashSet;

public class Util {

//	public static <T extends Comparable<T>> T clamp2(T val, T min, T max) {
//		return Math.max(min, Math.min(max, val));
//	}
	
//	public static int clamp(int val, int min, int max) {
//		return Math.max(min, Math.min(max, val));
//	}
		
	public static <T extends Comparable<T>> T horquillar (T val, T min, T max) {
	    if (val.compareTo(min) < 0) return min;
	    else if (val.compareTo(max) > 0) return max;
	    else return val;
	}
	
	public static Collection<Point> generarPuntosEnArcoCircular(double xCentro, double yCentro, double radio, int puntos,
			double inclinacionInicial, double anguloArco, boolean sentidoDelReloj){
		Collection<Point> coordenadas = new LinkedHashSet<>();
		int[][] coorCrudas = generarPuntosEnArcoCircularCrudos(xCentro, yCentro, radio, puntos, inclinacionInicial, anguloArco, sentidoDelReloj);
		for(int i = 0; i < coorCrudas.length; i++){
			coordenadas.add(new Point(coorCrudas[i][0], coorCrudas[i][1]));
		}
		
		return coordenadas;
	}
	
	private static int[][] generarPuntosEnArcoCircularCrudos(double xCentro, double yCentro, double radio, int puntos,
			double inclinacionInicial, double anguloArco, boolean sentidoDelReloj){
		int[][] coordenadas = new int[puntos][2];
		double anguloEntrePuntos = 0;
		if(puntos > 1)
			anguloEntrePuntos = anguloArco/(puntos-1) * ((sentidoDelReloj)?-1:1);
		
		for(int i = 0; i < puntos; i++){
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
