package es.lanyu.commons.config;

import java.io.File;

public interface Configurable {

	Propiedades getPropiedades();
	
	default File getRutaPorDefecto() { return new File("data/config.properties"); }
	
	default void guardarConfiguracion(){
		Propiedades.guardarPropiedades(getPropiedades(), getRutaPorDefecto().getAbsolutePath());
	}
}