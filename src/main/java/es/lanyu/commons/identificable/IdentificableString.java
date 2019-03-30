package es.lanyu.commons.identificable;

/**Clase abstracta que permite identificar un objeto por su identificador de tipo String.
 * 
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public abstract class IdentificableString implements Identificable<String> {

	protected String id;

	@Override
	public String getIdentificador() {
		if(id == null)
			setIdentificador(Identificable.super.getIdentificador());
		return id;
	}

	public void setIdentificador(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return getHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return isEquals(obj);
	}
}
