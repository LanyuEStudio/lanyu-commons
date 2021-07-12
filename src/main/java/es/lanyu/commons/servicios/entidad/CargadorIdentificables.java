package es.lanyu.commons.servicios.entidad;

import es.lanyu.commons.identificable.AbstractNombrable;
import es.lanyu.commons.identificable.GestorIdentificables;
import es.lanyu.commons.identificable.Identificable;
import es.lanyu.commons.identificable.Nombrable;
import es.lanyu.commons.io.Deserializador;

/**
 * Interfaz para cargar gestores tipo {@link GestorIdentificables}.
 * 
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public interface CargadorIdentificables {

    /**
     * Devuelve un deserializador por defecto.
     * 
     * @return deserializador por defecto
     */
    Deserializador getDeserializadorDefecto();

    /**
     * Facilita la carga de {@link Identificable} proporcionando un serializador por defecto
     * para los subtipos de {@link AbstractNombrable} ({@code id} y {@code nombre}).
     * 
     * @param <K>
     *            Tipo del identificador para {@code Identificable<K>}
     * @param <T>
     *            Tipo de la {@code claseMapa}
     * @param <S>
     *            Tipo de la especializacion. Puede ser el igual a {@code T}
     * @param deserializador
     *            {@code Deserializador} encargado de deserializar desde archivo
     * @param rutaArchivo
     *            ruta al archivo con los datos
     * @param claseMapa
     *            Clase que sirve para mapear el recurso (normalmente la mas generica)
     * @param claseEspecializacion
     *            Clase subtipo de claseMapa para gestionar recursos mas especializados
     * @param gestor
     *            Gestor que administrara los recursos a cargar
     */
    <K extends Comparable<K>, T extends Identificable<K> & Nombrable, S extends T> void cargarNombrables(Deserializador deserializador,
            String rutaArchivo, Class<T> claseMapa, Class<S> claseEspecializacion, GestorIdentificables gestor);

    /**
     * Facilita la carga de {@link Identificable} proporcionando un serializador por defecto
     * para los subtipos de {@link AbstractNombrable} ({@code id} y {@code nombre}).
     * 
     * @param <K>
     *            Tipo del identificador para {@code Identificable<K>}
     * @param <T>
     *            Tipo de la {@code claseMapa}
     * @param <S>
     *            Tipo de la especializacion. Puede ser el igual a {@code T}
     * @param rutaArchivo
     *            ruta al archivo con los datos
     * @param claseMapa
     *            Clase que sirve para mapear el recurso (normalmente la mas generica)
     * @param claseEspecializacion
     *            Clase subtipo de claseMapa para gestionar recursos mas especializados
     * @param gestor
     *            Gestor que administrara los recursos a cargar
     */
     default <K extends Comparable<K>, T extends Identificable<K> & Nombrable, S extends T> void cargarNombrables(String rutaArchivo,
            Class<T> claseMapa, Class<S> claseEspecializacion, GestorIdentificables gestor) {
        cargarNombrables(getDeserializadorDefecto(), rutaArchivo, claseMapa, claseEspecializacion, gestor);
    }

    /**
     * Sobrecarga de {@link CargadorIdentificables#cargarNombrables(String, Class, Class, GestorIdentificables)}
     * utilizando un {@link ServicioEntidad}.
     * 
     * @param <K>
     *            Tipo del identificador para {@code Identificable<K>}
     * @param <T>
     *            Tipo de la {@code claseMapa}
     * @param <S>
     *            Tipo de la especializacion. Puede ser el igual a {@code T}
     * @param rutaArchivo
     *            ruta al archivo con los datos
     * @param claseMapa
     *            Clase que sirve para mapear el recurso (normalmente la mas generica)
     * @param claseEspecializacion
     *            Clase subtipo de claseMapa para gestionar recursos mas especializados
     * @param servicio
     *            {@code ServicioEntidad} que administrara los recursos a cargar
     */
    default <K extends Comparable<K>, T extends Identificable<K> & Nombrable, S extends T> void cargarNombrables(String rutaArchivo,
            Class<T> claseMapa, Class<S> claseEspecializacion, ServicioEntidad servicio) {
        cargarNombrables(rutaArchivo, claseMapa, claseEspecializacion, servicio.getGestorNombrables());
    }
}
