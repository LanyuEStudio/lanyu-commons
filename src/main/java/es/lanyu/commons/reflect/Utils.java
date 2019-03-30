package es.lanyu.commons.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author isma
 *
 */
public class Utils {

	/**Devuelve el Field con el nombreCampo pasado (declarado o heredadao)
	 * y la accesibilidad que tenga definida.
	 * @param clase para la que se busca el campo
	 * @param nombreCampo nombre literal de la variable de instancia
	 * @return Field que cumpla con las condiciones
	 */
	public static Field getCampo(Class<?> clase, String nombreCampo){
		return getCampo(clase, nombreCampo, null);
	}
	
	/**Devuelve el Field con el nombreCampo pasado (declarado o heredadao)
	 * y la accesibilidad que tenga definida.
	 * La accesibilidad se establece en el valor "accesible" o sin cambios en caso de null
	 * @param clase para la que se busca el campo
	 * @param nombreCampo nombre literal de la variable de instancia
	 * @param accesible true para hacerlo accesible
	 * @return Field que cumpla con las condiciones y con la accesibilidad que se pida
	 */
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
	
	/**Busca la anotacion de un tipo en una clase (y sus subtipos)
	 * @param claseDondeBuscar clase mas especializada conde empezar la busqueda
	 * @param claseAnotacion tipo de la anotacion
	 * @param <A> tipo de la anotacion que implementa Annotation
	 * @return anotacion encontrada o null si no existe
	 */
	public static <A extends Annotation> A buscarAnotacionEnClase(Class<A> claseAnotacion, Class<?> claseDondeBuscar){
		Class<?> clase = claseDondeBuscar;
		A anotacion;
		
		//Busco la anotacion recursivamente en su jerarquia de clase
		do{
			anotacion = clase.getAnnotation(claseAnotacion);
		//Si no se ha encontrado debo buscar en la clase superior
		} while(anotacion == null && (clase = clase.getSuperclass() )!= null);
		
		return anotacion;
	}
	
}
