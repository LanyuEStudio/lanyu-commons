package es.lanyu.commons.config;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;

/**Implementacion basica de {@link Configurable}
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public class ConfiguracionBase implements Configurable {
	Propiedades propiedades = new Propiedades();
	Collection<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();
	
	@Override
	public Propiedades getPropiedades() {
		return propiedades;
	}
	
	@Override
	public Collection<PropertyChangeListener> getListeners() {
		return listeners;
	}
	
	public ConfiguracionBase(){
		this(new Propiedades(Configurable.getRutaPorDefecto().getAbsolutePath()));
	}
	
	public ConfiguracionBase(Propiedades propiedades){
		super();
		this.propiedades = propiedades;
	}
	
}
