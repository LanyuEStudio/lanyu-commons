package es.lanyu.commons.identificable;

import java.util.Comparator;

public abstract class AbstractNombrable extends IdentificableString implements Nombrable {

	private static final long serialVersionUID = -4738964822375774603L;
	
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


