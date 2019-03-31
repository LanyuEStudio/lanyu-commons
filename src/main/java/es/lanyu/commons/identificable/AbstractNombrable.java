package es.lanyu.commons.identificable;

/**Clase abstracta que implementa {@code Identificable<String>} (mediante {@link IdentificableString})
 * y {@link Nombrable}.
 * 
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 * @see Identificable
 * @see Nombrable
 */
public abstract class AbstractNombrable extends IdentificableString implements Nombrable {
	
	protected String nombre;
	
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


