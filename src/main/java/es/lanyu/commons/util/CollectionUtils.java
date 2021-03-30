package es.lanyu.commons.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Collectors;

/**Utilidades sobre {@link Collection} o para generar {@link Collection}
 * @author isma
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0.3
 * @since 1.0.3
 */
public class CollectionUtils {

    /**Genera una {@link Collection} (implementacion {@link ArrayList} partiendo de un
     * {@link Iterator}. Los tipos de entrada {@code T} y salida {@code R} pueden ser
     * distintos aplicando la funcion {@code mapeo}
     * @param <T> tipo de los elementos de {@code iterador}
     * @param <R> tipo de los elementos de la coleccion resultante
     * @param iterador con los elementos iniciales
     * @param mapeo de un elemento origen a elemento destino: {@code (cliente -> cliente.getNombre()} 
     * @return {@link Collection} con los elementos mapeados reunidos.
     */
    public static <T, R> Collection<R> getElementosDesdeIterator(Iterator<T> iterador, Function<T, R> mapeo) {
        Collection<T> ents = new ArrayList<>();
        iterador.forEachRemaining(ents::add);

        Collection<R> temps = ents.stream()
            .map(mapeo)
            .collect(Collectors.toList());

        return temps;
    }
    
    /**Utiliza {@link #getElementosDesdeIterator(Iterator, Function)} para reunir
     * en la salida los propios elementos iniciales.
     * @param <T> tipo de los elementos de {@code iterador} y de la coleccion resultante
     * @param iterador con los elementos iniciales
     * @return {@link Collection} con los elementos iniciales reunidos.
     */
    public static <T> Collection<T> getElementosDesdeIterator(Iterator<T> iterador) {
        return getElementosDesdeIterator(iterador, i -> i);
    }
    
}
