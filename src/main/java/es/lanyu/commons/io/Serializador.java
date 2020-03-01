package es.lanyu.commons.io;

public interface Serializador {

	/**Devuelve un {@code String} con la representacion en JSON del objeto parametro
	 * @param <T> tipo del objeto a serializar
	 * @param objeto para serializar
	 * @return {@code String} con el objeto JSON plano
	 */
	default <T> String serializarJson(T objeto) {
		return serializarJson(objeto, false);
	}

	/**Devuelve un {@code String} con la representacion en JSON del objeto parametro
	 * @param <T> tipo del objeto a serializar
	 * @param objeto para serializar
	 * @param pretty {@code true} si se quiere JSON en formato pretty print
	 * @return {@code String} con el objeto JSON plano
	 */
	<T> String serializarJson(T objeto, boolean pretty);
	
//	<T> String serializarJson(boolean pretty, T[] objeto);

}