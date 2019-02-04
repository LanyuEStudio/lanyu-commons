package es.lanyu.commons.config;

public class ConfiguracionBase implements Configurable {
	Propiedades propiedades = new Propiedades();
	
	@Override
	public Propiedades getPropiedades() {
		return propiedades;
	}
	
	public ConfiguracionBase(){
		super();
		propiedades = new Propiedades(getRutaPorDefecto().getAbsolutePath());
	}
	
	public ConfiguracionBase(Propiedades propiedades){
		this.propiedades = propiedades;
	}
	
}
