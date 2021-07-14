package es.lanyu.commons.identificable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Gestiona mapas de nombrables para extender la funcionalidad de
 * {@link GestorIdentificables} a {@link Nombrable}.
 * 
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public class GestorNombrables extends GestorIdentificables {

    /**
     * Devuelve una {@code List} de {@link Identificable} &amp; {@link Nombrable} almacenados
     * como recursos del tipo {@code T} que coincida con el {@code nombre} pasado.
     * 
     * @param <T>
     *            Tipo del recurso que corresponda
     * @param clase
     *            Tipo del recurso que corresponde al mapa donde se va a buscar
     * @param nombre
     *            Nombre que tiene que tiene que devolver {@link Nombrable#getNombre()}
     * @return {@code List<T>} con todos los objetos administrador que cumplen la condicion
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <T extends Identificable & Nombrable> List<T> getIdentificablePorNombre(Class<T> clase, String nombre) {
        return ((Map<?, T>) getMapaParaClase(clase)).values().stream()
                .filter(n -> n.getNombre().equals(nombre))
                .collect(Collectors.toList());
    }

    /**
     * Extiende las operaciones de agregacion para usar {@link Nombrable}s.
     * 
     * @param <K>
     *            Tipo del identificador usando por el tipo {@code T}
     * @param <T>
     *            Tipo del recurso al que se quiere agregar
     * @param clase
     *            De tipo compatible con {@code Identificable & Nombrable}
     * @param nombrable
     *            {@code Nombrable} para agregar
     */
    public <K extends Comparable<K>, T extends Identificable<K> & Nombrable> void addNombrable(Class<T> clase,
            T nombrable) {
        super.addIdentificable(clase, nombrable);
    }

}
