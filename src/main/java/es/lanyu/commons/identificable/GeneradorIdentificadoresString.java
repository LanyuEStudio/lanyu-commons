package es.lanyu.commons.identificable;

/**Genera claves de tipo String de manera autoincremental (con base 1) 
 * y con un formato a establecer ({@code #%d} por defecto)
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 * @see GeneradorIdentificadores
 */
public class GeneradorIdentificadoresString implements GeneradorIdentificadores<String>{

	private String formatoID;
	private int ultimoNumeroGenerado;

	public GeneradorIdentificadoresString(){
		this("#%d");
	}

	public GeneradorIdentificadoresString(String formatoIds) {
		formatoID = formatoIds;
	}

	public String generarID() {
		return String.format(formatoID, ultimoNumeroGenerado++);
	}

	protected void reiniciarNumeros(){
		ultimoNumeroGenerado = 1;
	}

}
