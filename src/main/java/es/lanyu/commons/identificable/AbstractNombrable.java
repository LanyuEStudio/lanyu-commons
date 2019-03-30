package es.lanyu.commons.identificable;

import java.util.Comparator;

/**Clase abstracta que implementa {@code Identificable<String>} (mediante {@link IdentificableString})
 * y {@code Nombrable}.
 * 
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 * @see Identificable
 * @see Nombrable
 */
public abstract class AbstractNombrable extends IdentificableString implements Nombrable {
	
	static Comparator<AbstractNombrable> comparador = new Comparator<AbstractNombrable>() {
		@Override
		public int compare(AbstractNombrable o1, AbstractNombrable o2) {
			if(o1 == null) return -1;
			if(o2 == null) return 1;
			return o1.getNombre().compareTo(o2.getNombre());
		}
	};
	
	protected String nombre;
		
	
	public static Comparator<AbstractNombrable> getComparadorPorNombre() {
		return comparador;
	}
	
	@Override
	public String getNombre() {
		if(nombre != null)
			return nombre;
		else
			return getIdentificador();
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return getNombre();
	}
	
}


