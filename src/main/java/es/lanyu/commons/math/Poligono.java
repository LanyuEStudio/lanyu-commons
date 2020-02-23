package es.lanyu.commons.math;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import es.lanyu.commons.reflect.ReflectUtils;

public interface Poligono<T extends Vector2d> {

	//TODO Metodos para offSetX e Y
	Collection<T> getPuntos();
	
	default void addPunto(T punto) {
		getPuntos().add(punto);
	}
	
	default void removePunto(T punto) {
		getPuntos().remove(punto);
	}
	
	/**Calcula las esquinas inferior izquierda y superior derecha de la caja que envuelve el poligono.<br><br>
	 * En una implementacion se puede hacer un lazy loading y usar este metodo para calcularlo cuando sea null
	 * o cada vez que haya una modificacion en la coleccion.
	 * @param arrayTipo del tipo de los puntos. El tipo T debe tener un constructor sin parametros
	 * @return esquina inferior izquierda y superior derecha de la caja externa. null si no se puede calcular. 
	 */
	default T[] getBoundingBox(T[] arrayTipo) {
		T[] esquinas = Arrays.copyOf(arrayTipo, 2);
		Class<T> clase = ReflectUtils.getClaseArray(arrayTipo);
		try {
			T infIzq = esquinas[0]= clase.newInstance();
			T supDcha = esquinas[1] = clase.newInstance();
			//TODO Dejar redondeo? Separar en uno mas exacto y otro menos 
			List<Integer> xs = this.getPuntos().stream().map(p -> (int)p.getX()).distinct().sorted().collect(Collectors.toList());
			List<Integer> ys = this.getPuntos().stream().map(p -> (int)p.getY()).distinct().sorted().collect(Collectors.toList());
			System.out.println(xs);
			System.out.println(ys);
			infIzq.setX(xs.get(0));
			infIzq.setY(ys.get(0));
			supDcha.setX(xs.get(xs.size() - 1));
			supDcha.setY(ys.get(ys.size() - 1));
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			esquinas = null;
		}
		
		return esquinas;
	}
}
