package es.lanyu.commons.reflect;

import java.lang.reflect.Field;

public class Utils {

	public static Field getCampo(Class<?> clase, String nombreCampo){
		return getCampo(clase, nombreCampo, null);
	}
	
	//Devuelve un campo en "clase" declarado o heredado, con "nombreCampo"
	//La accesibilidad se establece en el valor "accesible" o sin cambios en caso de null
	public static Field getCampo(Class<?> clase, String nombreCampo, Boolean accesible){
		Field campo = null;
		
		do{
			try {
				campo = clase.getDeclaredField(nombreCampo);
				if(accesible != null)
					campo.setAccessible(accesible);
			} catch (Exception e) {}
		//Si no se ha encontrado debo buscar en la clase superior
		//pues el campo puede ser heredado y no publico (ver getField)
		} while((clase = clase.getSuperclass() )!= null && campo == null);
		
		return campo;
	}
}
