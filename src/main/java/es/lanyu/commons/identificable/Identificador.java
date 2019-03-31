package es.lanyu.commons.identificable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**Anotacion para establecer el nombre del campo que almacena el identificador para un {@link Identificable}.
 * Ejemplo: {@code @Identificador("identificador")} (por defecto "id")
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)//Siendo anotacion de tipo la busqueda es mas rapida y no se puede repetir
public @interface Identificador {
	
	/**El valor por defecto si no se especifica lo contrario es {@code "id"}.
	 */
	static final String VALOR_POR_DEFECTO = "id"; 
	
	/**Nombre del campo que identifica al {@link Identificable}
	 * @return Valor del {@code Identificador}
	 */
	String value() default VALOR_POR_DEFECTO;

}
