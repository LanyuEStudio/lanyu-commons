package es.lanyu.commons.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import es.lanyu.commons.io.IoUtils;

/**Especializacion de {@link Properties}. Sirve para almacenar pares clave-valor que
 * pueden ser usados en la {@link ConfiguracionBase} de la aplicacion
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public class Propiedades extends Properties {

	private static final long serialVersionUID = -6608379078748934892L;
	
	private String ruta;
	
	protected String getRuta() {
		return ruta;
	}
	
	protected void setRuta(String ruta) {
		this.ruta = ruta;
	}
	
	
	/**Wrapper de {@link IoUtils#crearInputStream} para poder cambiar la forma de resolver
	 * la ruta desde Java nativo a otra implementacion
	 * @param ruta al archivo de propiedades
	 * @return {@link InputStream} del que leer las propiedades 
	 */
	protected InputStream getInputStream(String ruta) {
		InputStream input = null;
		try {
			input = IoUtils.crearInputStream(ruta);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return input;
	}
	
	/**Wrapper de {@link IoUtils#crearOutputStream} para poder cambiar la forma de resolver
	 * la ruta desde Java nativo a otra implementacion
	 * @param ruta al archivo de propiedades
	 * @return {@link OutputStream} del que leer las propiedades 
	 */
	protected OutputStream getOutputStream(String ruta) {
		OutputStream output = null;
		try {
			output = IoUtils.crearOutputStream(ruta);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return output;
	}
	
	public Propiedades() {}
	
	/**Lee los pares clave-valor de la ruta y crea unas propiedades cargadas con ellos
	 * @param ruta Ruta hasta el archivo con los datos
	 */
	public Propiedades(String ruta) {
		cargarPropiedades(ruta);
	}
	
	/**Carga en {@code propiedades} las que se lean desde la {@code ruta} especificada
	 * @param ruta Ruta al archivo donde guardar
	 * @return {@code true} si se guarda correctamente, {@code false} en caso contrario
	 */
	public boolean cargarPropiedades(String ruta) {
		boolean cargado;
		try (InputStream reader = getInputStream(ruta)) {
			load(reader);
			cargado = true;
			setRuta(ruta);
		} catch (IOException e) {
			e.printStackTrace();
			cargado = false;
		}
		
		return cargado;
	}

	/**Guarda las {@code propiedades} en la {@code ruta} especificada
	 * @return {@code true} si se guarda correctamente, {@code false} en caso contrario
	 */
	public boolean guardarPropiedades() {		
		return guardarPropiedades(getRuta());
	}
	
	/**Guarda las {@code propiedades} en la {@code ruta} especificada
	 * @param ruta Ruta al archivo donde guardar
	 * @return {@code true} si se guarda correctamente, {@code false} en caso contrario
	 */
	public boolean guardarPropiedades(String ruta) {
		boolean guardado;
		try (OutputStream writer = getOutputStream(ruta)) {
			store(writer, "Propiedades Lanyu EStudio");
			guardado = true;
			setRuta(ruta);
		} catch (IOException e) {
			e.printStackTrace();
			guardado = false;
		}
		
		return guardado;
	}
	
	/**Actualiza el valor asociado a la clave
	 * @param clave clave asociada al valor
	 * @param valor valor nuevo para la clave
	 * @return {@code true} si existia anteriormente valor para esa clave, sino {@code false}
	 */
	public boolean actualizarPropiedad(String clave, String valor) {
		boolean existia = getProperty(clave) != null;
		this.setProperty(clave, valor);
		
		return existia;
	}
	
	/**Devuelve el valor correspondiente a la clave
	 * @param clave Nombre de la clave
	 * @return Valor correspondiente a la clave
	 */
	public Object leerPropiedad(String clave) {
		return getProperty(clave);
	}
	
	/**Devuelve el valor correspondiente a la clave con el tipo int
	 * @param clave Nombre de la clave
	 * @return Valor correspondiente a la clave
	 */
	public int leerPropiedadInt(String clave) {
		return Integer.parseInt(getProperty(clave));
	}
	
	/**Devuelve el valor correspondiente a la clave con el tipo float
	 * @param clave Nombre de la clave
	 * @return Valor correspondiente a la clave
	 */
	public float leerPropiedadFloat(String clave) {
		return Float.parseFloat(getProperty(clave));
	}
	
	/**Devuelve el valor correspondiente a la clave con el tipo double
	 * @param clave Nombre de la clave
	 * @return Valor correspondiente a la clave
	 */
	public double leerPropiedadDouble(String clave) {
		return Double.parseDouble(getProperty(clave));
	}
	
	/**Devuelve el valor correspondiente a la clave con el tipo boolean
	 * @param clave Nombre de la clave
	 * @return Valor correspondiente a la clave
	 */
	public boolean leerPropiedadBoolean(String clave) {
		return Boolean.parseBoolean(getProperty(clave));
	}
	
	/**Agrega las propiedades nuevas a las anteriores
	 * @param propiedadesNuevas propiedades para agregar
	 * @return {@code true} si existia anteriormente valor para alguna propiedad, sino {@code false}
	 */
	public boolean addPropiedades(Properties propiedadesNuevas) {
		boolean sobreEscrito = false;
		for (String k : propiedadesNuevas.stringPropertyNames()) {
			sobreEscrito |= actualizarPropiedad(k, propiedadesNuevas.getProperty(k));
		}
		
		return sobreEscrito;
	}
}
