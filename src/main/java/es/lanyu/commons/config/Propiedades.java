package es.lanyu.commons.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**Especializacion de {@link Properties}. Sirve para almacenar pares clave-valor que
 * pueden ser usados en la {@link ConfiguracionBase} de la aplicacion
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public class Propiedades extends Properties {

	private static final long serialVersionUID = -6608379078748934892L;
	
	/**Carga en {@code propiedades} las que se lean desde la {@code ruta} especificada
	 * @param propiedades Propiedades a guardar
	 * @param ruta Ruta al archivo donde guardar
	 * @return {@code true} si se guarda correctamente, {@code false} en caso contrario
	 */
	public static boolean cargarPropiedades(Propiedades propiedades, String ruta){
		boolean cargado;
		try(BufferedReader buffer = new BufferedReader(new FileReader(ruta))){
			propiedades.load(buffer);
			cargado = true;
		} catch (IOException e) {
			e.printStackTrace();
			cargado = false;
		}
		
		return cargado;
	}
	
	/**Guarda las {@code propiedades} en la {@code ruta} especificada
	 * @param propiedades Propiedades a guardar
	 * @param ruta Ruta al archivo donde guardar
	 * @return {@code true} si se guarda correctamente, {@code false} en caso contrario
	 */
	public static boolean guardarPropiedades(Propiedades propiedades, String ruta){
		boolean guardado;
		try(BufferedWriter buffer = new BufferedWriter(new FileWriter(ruta))){
			propiedades.store(buffer, "Propiedades Lanyu");
			guardado = true;
		} catch (IOException e) {
			e.printStackTrace();
			guardado = false;
		}
		
		return guardado;
	}
	
	public Propiedades(){}
	
	
	/**Lee los pares clave-valor de la ruta y crea unas propiedades cargadas con ellos
	 * @param ruta Ruta hasta el archivo con los datos
	 */
	public Propiedades(String ruta){
		cargarPropiedades(this, ruta);
	}
	
	/**Actualiza el valor asociado a la clave
	 * @param clave clave asociada al valor
	 * @param valor valor nuevo para la clave
	 * @return {@code true} si existia anteriormente valor para esa clave, sino {@code false}
	 */
	public boolean actualizarPropiedad(String clave, String valor){
		boolean existia = getProperty(clave) != null;
		this.setProperty(clave, valor);
		
		return existia;
	}
	
	/**Devuelve el valor correspondiente a la clave
	 * @param clave Nombre de la clave
	 * @return Valor correspondiente a la clave
	 */
	public Object leerPropiedad(String clave){
		return getProperty(clave);
	}
	
	/**Agrega las propiedades nuevas a las anteriores
	 * @param propiedadesNuevas propiedades para agregar
	 * @return {@code true} si existia anteriormente valor para alguna propiedad, sino {@code false}
	 */
	public boolean addPropiedades(Properties propiedadesNuevas){
		boolean sobreEscrito = false;
		for(String k : stringPropertyNames()){
			sobreEscrito |= actualizarPropiedad(k, propiedadesNuevas.getProperty(k));
		}
		
		return sobreEscrito;
	}
}
