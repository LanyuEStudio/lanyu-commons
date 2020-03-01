package es.lanyu.commons.io;

public interface Deserializador {

	/**Devuelve un objeto del tipo {@code T} a partir de su representacion en JSON
	 * @param <T> tipo del objeto a deserializar
	 * @param tipo del objeto a deserializar
	 * @param json con la informacion del objeto
	 * @return objeto de tipo T deserializado o {@code null} si se produjo un error
	 */
	<T> T deserializarJson(Class<T> tipo, String json);

}