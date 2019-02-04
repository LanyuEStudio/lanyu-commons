package es.lanyu.commons.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Propiedades extends Properties {

	private static final long serialVersionUID = -6608379078748934892L;
	
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
	
	public Propiedades(String ruta){
		cargarPropiedades(this, ruta);
	}
	
	/**
	 * @param clave clave asociada al valor
	 * @param valor valor para la clave
	 * @return true si existia anteriormente valor para esa clave, sino false
	 */
	public boolean actualizarPropiedad(String clave, String valor){
		boolean existia = getProperty(clave) != null;
		this.setProperty(clave, valor);
		
		return existia;
	}
	
	public Object leerPropiedad(String clave){
		return getProperty(clave);
	}
	
	/**
	 * @param propiedadesNuevas propiedades para agregar
	 * @return true si existia anteriormente valor para alguna propiedad, sino false
	 */
	public boolean addPropiedades(Properties propiedadesNuevas){
		boolean sobreEscrito = false;
		for(String k : stringPropertyNames()){
			sobreEscrito |= actualizarPropiedad(k, propiedadesNuevas.getProperty(k));
		}
		
		return sobreEscrito;
	}
}
