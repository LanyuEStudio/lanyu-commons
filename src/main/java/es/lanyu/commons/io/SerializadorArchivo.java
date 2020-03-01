package es.lanyu.commons.io;

import java.io.File;

public interface SerializadorArchivo extends Serializador {
	
	/**Devuelve un {@code String} con la representacion en JSON del objeto parametro
	 * @param <T> tipo del objeto a serializar
	 * @param objeto para serializar
	 * @param archivo donde guardar el JSON
	 * @return {@code String} con el objeto JSON plano
	 */
	default <T> void guardarJson(T objeto, File archivo) {
		guardarJson(objeto, archivo, false);
	}
	
	/**Devuelve un {@code String} con la representacion en JSON del objeto parametro
	 * @param <T> tipo del objeto a serializar
	 * @param objeto para serializar
	 * @param archivo donde guardar el JSON
	 * @param pretty {@code true} si se quiere JSON en formato pretty print
	 * @return {@code String} con el objeto JSON plano
	 */
	<T> void guardarJson(T objeto, File archivo, boolean pretty);
	
}
