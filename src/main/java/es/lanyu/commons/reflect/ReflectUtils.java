package es.lanyu.commons.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

/**Clase de utilidades para facilitar algunas tareas de {@code java.lang.reflect}
 * y {@code java.lang.annotation}
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public class ReflectUtils {

  /**Devuelve el Field con el nombreCampo pasado (declarado o heredadao)
   * y la accesibilidad que tenga definida.
   * @param clase para la que se busca el campo
   * @param nombreCampo nombre literal de la variable de instancia
   * @return Field que cumpla con las condiciones
   */
  public static Field getCampo(Class<?> clase, String nombreCampo) {
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
  public static Field getCampo(Class<?> clase, String nombreCampo, Boolean accesible) {
    Field campo = null;
    
    do {
      try {
        campo = clase.getDeclaredField(nombreCampo);
        if(accesible != null)
          campo.setAccessible(accesible);
      } catch (Exception e) {}
    //Si no se ha encontrado debo buscar en la clase superior
    //pues el campo puede ser heredado y no publico (ver getField)
    } while ((clase = clase.getSuperclass() )!= null && campo == null);
    
    return campo;
  }
  
  /**Busca la anotacion de un tipo en una clase (y sus subtipos)
   * @param claseDondeBuscar clase mas especializada conde empezar la busqueda
   * @param claseAnotacion tipo de la anotacion
   * @param <A> tipo de la anotacion que implementa Annotation
   * @return anotacion encontrada o null si no existe
   */
  public static <A extends Annotation> A buscarAnotacionEnClase(Class<A> claseAnotacion, Class<?> claseDondeBuscar) {
    Class<?> clase = claseDondeBuscar;
    A anotacion;
    
    //Busco la anotacion recursivamente en su jerarquia de clase
    do {
      anotacion = clase.getAnnotation(claseAnotacion);
    //Si no se ha encontrado debo buscar en la clase superior
    } while(anotacion == null && (clase = clase.getSuperclass() )!= null);
    
    return anotacion;
  }
  
  /**Devuelve una clase {@code Class<T>} desde un array.
   * @param array del tipo deseado
   * @param <T> tipo de los elementos dentro del array
   * @return {@code Class<T>}
   */
  @SuppressWarnings("unchecked")
  public static <T> Class<T> getClaseArray(T[] array) {
    return (Class<T>) array.getClass().getComponentType();
  }

  /**Busca un constructor apropiado de la clase {@code clazz} coincidiento con los parametros {@code initArgs}.
   * @see <a href="https://stackoverflow.com/a/18136892">https://stackoverflow.com/a/18136892</a>
   * @param <C> tipo donde buscar el constructor
   * @param clazz {@link Class} del tipo donde buscar (igual a {@code C.class})
   * @param initArgs parametros ordenados para coincidir los del constructor
   * @return constructor apropiado a la invocacion o lanza una excepcion {@link IllegalArgumentException}
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  static <C> Constructor<C> getConstructorApropiado(Class<C> clazz, Object... initArgs) {
    if (initArgs == null) {
      initArgs = new Object[0];
    }
    for (Constructor con : clazz.getDeclaredConstructors()) {
      Class[] types = con.getParameterTypes();
      if (types.length != initArgs.length) {
        continue;
      }
      boolean match = true;
      for (int i = 0; i < types.length; i++) {
        Class need = types[i], got = initArgs[i].getClass();
        if (!need.isAssignableFrom(got)) {
          if (need.isPrimitive()) {
            match = (int.class.equals(need) && Integer.class.equals(got))
                 || (long.class.equals(need) && Long.class.equals(got))
                 || (char.class.equals(need) && Character.class.equals(got))
                 || (short.class.equals(need) && Short.class.equals(got))
                 || (boolean.class.equals(need) && Boolean.class.equals(got))
                 || (byte.class.equals(need) && Byte.class.equals(got))
                 || (float.class.equals(need) && Float.class.equals(got))
                 || (double.class.equals(need) && Double.class.equals(got));
          } else { match = false; }
        }
        if (!match) { break; }
      }
      if (match) { return con; }
    }
    throw new IllegalArgumentException("No se puede encontrar un constructor apropiado para la clase "
                                        + clazz + " y argumentos " + Arrays.toString(initArgs));
  }
}
