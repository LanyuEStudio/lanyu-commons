package es.lanyu.commons.identificable;

public class GeneradorIdentificadoresString<T extends Identificable<String>> extends GeneradorIdentificadores<T, String>{

	private String formatoID;
	private int ultimoNumeroGenerado;
	
	public GeneradorIdentificadoresString(){
		this("#%d");
	}
	
	public GeneradorIdentificadoresString(String formatoIds) {
		formatoID = formatoIds;
	}
	
	public String generarID() {
		String id; 

		do { // Repetir hasta encontrar ID sin usar
			id = String.format(formatoID, ultimoNumeroGenerado++);
		} while (getIdentificable(id) != null);
		
		return id;
	}
	
	public void registrarIdentificable(T identificable){
		getMapaIdentificadores().put(identificable.getIdentificador(), identificable);
	}
	
	protected void reiniciarNumeros(){
		ultimoNumeroGenerado = 1;
	}

}
