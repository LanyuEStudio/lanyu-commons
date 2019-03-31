package es.lanyu.commons.identificable;

import java.util.Comparator;

/**Interfaz para tipos que tienen nombre como una de sus propiedades.
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public interface Nombrable {

	/**getter para el nombre del objeto.
	 * @return Devuelve el {@code String} con el nombre del objeto
	 */
	String getNombre();
	
	/**Devuelve un {@link Comparator} para ordenar por {@link Nombrable#getNombre()}
	 * @return {@code Comparator} por {@code getNombre()}
	 */
	static Comparator<Nombrable> getComparadorPorNombre() {
		return new Comparator<Nombrable>() {
			@Override
			public int compare(Nombrable o1, Nombrable o2) {
				if(o1 == null) return -1;
				if(o2 == null) return 1;
				return o1.getNombre().compareTo(o2.getNombre());
			}
		};
	}
}
