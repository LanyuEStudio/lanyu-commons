package es.lanyu.commons.identificable;

public class GeneradorIdentificadoresString extends GeneradorIdentificadores<String>{

	private String formatoID = "#%d";
	private int ultimoNumeroGenerado;
	
	public String generarID() {
		String id; 

		do { // Repetir hasta encontrar ID sin usar
			id = String.format(formatoID, ultimoNumeroGenerado++);
		} while (getIdentificable(id) != null);
		
		return id;
	}
	
	public void registrarIdentificable(Identificable<String> identificable){
		getMapaIdentificadores().put(identificable.getIdentificador(), identificable);
	}
	
	protected void reiniciarNumeros(){
		ultimoNumeroGenerado = 1;
	}

}
