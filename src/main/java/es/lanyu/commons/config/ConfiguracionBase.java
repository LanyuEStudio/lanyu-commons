package es.lanyu.commons.config;

/**Implementacion basica de {@link Configurable}
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
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
