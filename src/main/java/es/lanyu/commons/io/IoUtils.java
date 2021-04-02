package es.lanyu.commons.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import es.lanyu.commons.identificable.GestorIdentificables;
import es.lanyu.commons.identificable.Identificable;

/**
 * Clase de utilidades para trabajar con operaciones sobre ficheros
 * 
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public class IoUtils {

    public static InputStream crearInputStream(String ruta) throws FileNotFoundException {
        return new FileInputStream(ruta);
    }

    public static OutputStream crearOutputStream(String ruta) throws FileNotFoundException {
        return new FileOutputStream(ruta);
    }

    /**
     * Vuelca una cadena en un fichero con la codificacion marcada
     * 
     * @param informe
     *            Cadena a escribir
     * @param rutaArchivo
     *            Ruta del fichero
     * @param charSet
     *            Codificacion a usar
     * @throws IOException
     *             Produced by failed or interrupted I/O operations
     * @see IoUtils#guardarInformeEnFichero(String texto, Writer escritor)
     */
    public static void guardarInformeEnFichero(String informe, String rutaArchivo, String charSet) throws IOException {
        guardarInformeEnFichero(informe, new OutputStreamWriter(crearOutputStream(rutaArchivo), charSet));
    }

    /**
     * Vuelca una cadena en un fichero con la codificacion UTF-8
     * 
     * @param informe
     *            Cadena a escribir
     * @param rutaArchivo
     *            Ruta del fichero
     * @throws IOException
     *             Produced by failed or interrupted I/O operations
     * @see IoUtils#guardarInformeEnFichero(String, String, String)
     */
    public static void guardarInformeEnFichero(String informe, String rutaArchivo) throws IOException {
        guardarInformeEnFichero(informe, rutaArchivo, "UTF-8");
    }

    /**
     * Vuelca una cadena en el {@link File} fichero
     * 
     * @param informe
     *            Cadena a escribir
     * @param fichero
     *            File donde escribir la cadena
     * @throws IOException
     *             Produced by failed or interrupted I/O operations
     * @see IoUtils#guardarInformeEnFichero(String texto, Writer escritor)
     */
    public static void guardarInformeEnFichero(String informe, File fichero) throws IOException {
        guardarInformeEnFichero(informe, new FileWriter(fichero));
    }

    /**
     * Vuelca una cadena en un fichero con la codificacion UTF-8 rodeandolo de
     * un {@link BufferedWriter} (es mas eficiente, ver <a href=
     * "https://docs.oracle.com/javase/8/docs/api/java/io/BufferedWriter.html">
     * documentacion</a>
     * 
     * @param texto
     *            Cadena a escribir
     * @param escritor
     *            {@link Writer} para encapsular
     * @throws IOException
     *             Produced by failed or interrupted I/O operations
     */
    private static void guardarInformeEnFichero(String texto, Writer escritor) throws IOException {
        Writer bWriter = new BufferedWriter(escritor);
        bWriter.write(texto);
        // bWriter.flush();
        bWriter.close();
    }

    /**
     * Devuelve el contenido del archivo en la ruta pasada como un
     * {@code String}. Es lo mismo que invocar {@code leerArchivoComoString(rutaArchivo, false)}
     * 
     * @param rutaArchivo
     *            al {@code File} conteniendo el {@code String}
     * @return {@code String} leido
     */
    public static String leerArchivoComoString(String rutaArchivo) {
        return leerArchivoComoString(rutaArchivo, false);
    }

    /**
     * Devuelve el contenido del archivo en la ruta pasada como un
     * {@code String}
     * 
     * @param rutaArchivo
     *            al {@code File} conteniendo el {@code String}
     * @param desdeClasspath
     *            {@code true} si la ruta es relativa al classpath
     * @return {@code String} leido
     */
    public static String leerArchivoComoString(String rutaArchivo, boolean desdeClasspath) {
        String leido = null;
        try (InputStreamReader input = desdeClasspath
                ? new InputStreamReader(IoUtils.class.getResourceAsStream("/" + rutaArchivo))
                : new InputStreamReader(new FileInputStream(rutaArchivo), "UTF-8");
                BufferedReader buffer = new BufferedReader(input);) {
            leido = stringDesdeBuffer(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return leido;
    }
    
    private static String stringDesdeBuffer(BufferedReader buffer) {
        StringBuilder sb = new StringBuilder();
        buffer.lines().forEach(l -> sb.append(l + System.lineSeparator()));
        
        return sb.toString().trim();
    }

    // TODO Escribir cadena al final del archivo
//    public static void agregarTextoEnFinalArchivo(String texto, Writer escritor) {
//        System.out.println("Hay que implementarlo");
//    }

    /**
     * Copia un archivo de la ruta {@code origen} a la ruta {@code destino}
     * 
     * @param rutaArchivoOrigen
     *            ruta hasta el archivo a copiar
     * @param rutaArchivoDestino
     *            ruta donde hacer la copia
     * @throws IOException
     *             Produced by failed or interrupted I/O operations
     */
    public static void copiarArchivo(String rutaArchivoOrigen, String rutaArchivoDestino) throws IOException {
        Files.copy(FileSystems.getDefault().getPath(rutaArchivoOrigen), FileSystems.getDefault().getPath(rutaArchivoDestino),
                StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Cuenta las lineas de un fichero en la ruta {@code fichero}. Codigo copiado de 
     * <a href="http://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java">
     * stackoverflow</a>
     * 
     * @param rutaArchivo
     *            ruta del fichero
     * @return numero de lineas que contiene el fichero
     * @throws IOException
     *             Produced by failed or interrupted I/O operations
     */
    public static long contarLineas(String rutaArchivo) throws IOException {
        long lineas = -1;

        // Respuesta de @Ernestas Gruodis NO REQUIERE JAVA 8 y es muy rapido
//         try (BufferedInputStream is = new BufferedInputStream(new FileInputStream(fichero), 1024)) {
//             byte[] c = new byte[1024];
//             boolean empty = true, lastEmpty = false;
//             int read;
//             while ((read = is.read(c)) != -1) {
//                 for (int i = 0; i < read; i++) {
//                     if (c[i] == '\n') {
//                         lineas++;
//                         lastEmpty = true;
//                     } else if (lastEmpty) {
//                         lastEmpty = false;
//                     }
//                 }
//                 empty = false;
//             }
//            
//             if (!empty) {
//                 if (lineas == 0) {
//                     lineas = 1;
//                 } else if (!lastEmpty) {
//                     lineas++;
//                 }
//             }
//         } catch (Exception e) {
//            e.printStackTrace();
//        }

        // Respuesta de @msayag REQUIERE JAVA 8 y suele ser aun mas rapido
        try (Stream<String> lines = Files.lines(new File(rutaArchivo).toPath(), Charset.defaultCharset())) {
            lineas = lines.count();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lineas;
    }

    /**
     * Devuelve un {@code File[]} con los archivos en la carpeta
     * {@code rutaDirectorio} con la {@code extension} solicitada.
     * 
     * @param rutaDirectorio
     *            Ruta de la carpeta donde buscar
     * @param extension
     *            Extension de los archivos devueltos (por ejemplo {@code .png})
     * @return Array de {@code File} de los archivos que cumplen con la
     *         extension en la carpeta
     * @throws Exception
     *             Se produce si la ruta no pertenece a un directorio valido
     */
    public static File[] archivosConExtension(String rutaDirectorio, String extension) throws Exception {
        if (rutaDirectorio != null) {
            File[] archivos;
            File dir = new File(rutaDirectorio);
            if (dir.isDirectory()) {
                archivos = dir.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.toLowerCase().endsWith(extension);
                    }
                });
            } else {
                throw new Exception("La ruta no pertenece a un directorio " + rutaDirectorio);
            }

            return archivos;
        } else {
            throw new Exception("La ruta no puede ser nula");
        }
    }

    /**
     * Devuelve el directorio que corresponde a la ubicacion de {@code clase}
     * 
     * @param clase
     *            para la que se quiere conocer su ruta
     * @return {@code File} al directorio que contiene a {@code clase}
     */
    public static File getDirectorioDeClase(Class<?> clase) {
        File directorio = null;
        String Recurso = clase.getSimpleName() + ".class";
        try {
            URL url = clase.getResource(Recurso);
            if (url.getProtocol().equals("file")) {
                File f = new File(url.toURI());
                do {
                    f = f.getParentFile();
                } while (!f.isDirectory());
                directorio = f;
            } else if (url.getProtocol().equals("jar")) {
                String expected = "!/" + Recurso;
                String s = url.toString();
                s = s.substring(4);
                s = s.substring(0, s.length() - expected.length());
                File f = new File(new URL(s).toURI());
                do {
                    f = f.getParentFile();
                } while (!f.isDirectory());
                directorio = f;
            }
        } catch (Exception e) {
            directorio = new File("");// ".");//El punto puede perjudicar
        }

        return directorio;
    }

    /**
     * Carga los recursos de tipo {@link Identificable} almacenados en cada fila
     * del fichero {@code rutaArchivo} en formato .json y los almacena en el
     * {@code gestor} como un recurso del tipo {@code claseMapa}. Puede
     * especializarse el recurso a guardar con {@code claseEspecializacion} si
     * esta serializado un subtipo de {@code claseMapa}
     * 
     * @param <K>
     *            Tipo del identificador del {@code Identificable}
     * @param <T>
     *            Tipo de la {@code claseMapa}
     * @param <S>
     *            Tipo de la especializacion. Puede ser el igual a {@code T}
     * @param deserializador
     *            deserializador de tipo {@link DeserializadorArchivo} que
     *            implementa la lectura y escritura en formato json
     * @param rutaArchivo
     *            ruta al archivo .json
     * @param claseMapa
     *            Clase que sirve para mapear el recurso (normalmente la mas
     *            generica)
     * @param claseEspecializacion
     *            Clase subtipo de claseMapa para gestionar recursos mas
     *            especializados
     * @param leerLineaPorLinea
     *            {@code true} si el archivo trae un objeto aplanado en cada
     *            linea
     * @param gestor
     *            Gestor que administrara los recursos a cargar
     */
    public static <K extends Comparable<K>, T extends Identificable<K>, S extends T> void cargarIdentificables(
            Deserializador deserializador, String rutaArchivo, Class<T> claseMapa, Class<S> claseEspecializacion,
            GestorIdentificables gestor, boolean leerLineaPorLinea) {

        if (leerLineaPorLinea) {
            String linea = null;
            try (BufferedReader buffer = new BufferedReader(
                    new InputStreamReader(new FileInputStream(rutaArchivo), "UTF-8"))) {
                while ((linea = buffer.readLine()) != null) {
                    try {
                        T objeto = deserializador.deserializarJson(claseEspecializacion, linea);
                        gestor.addIdentificable(claseMapa, objeto);
                    } catch (Exception e) {
                        Logger.getLogger(IoUtils.class.getName()).log(Level.WARNING,
                                "Error parseando " + linea + ": Se omite");
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            deserializador.deserializarJson(claseEspecializacion, leerArchivoComoString(rutaArchivo));
        }
    }

    /**
     * Carga los recursos de tipo {@link Identificable} almacenados en cada fila
     * del fichero {@code rutaArchivo} en formato .json y los almacena en el
     * {@code gestor} como un recurso del tipo {@code claseMapa}. Puede
     * especializarse el recurso a guardar con {@code claseEspecializacion} si
     * esta serializado un subtipo de {@code claseMapa}
     * 
     * @param <K>
     *            Tipo del identificador del {@code Identificable}
     * @param <T>
     *            Tipo de la {@code claseMapa}
     * @param <S>
     *            Tipo de la especializacion. Puede ser el igual a {@code T}
     * @param deserializador
     *            deserializador de tipo {@link DeserializadorArchivo} que
     *            implementa la lectura y escritura en formato json
     * @param rutaArchivo
     *            ruta al archivo .json
     * @param claseMapa
     *            Clase que sirve para mapear el recurso (normalmente la mas
     *            generica)
     * @param claseEspecializacion
     *            Clase subtipo de claseMapa para gestionar recursos mas
     *            especializados
     * @param gestor
     *            Gestor que administrara los recursos a cargar
     */
    public static <K extends Comparable<K>, T extends Identificable<K>, S extends T> void cargarIdentificables(
            DeserializadorArchivo deserializador, String rutaArchivo, Class<T> claseMapa, Class<S> claseEspecializacion,
            GestorIdentificables gestor) {
        cargarIdentificables(deserializador, rutaArchivo, claseMapa, claseEspecializacion, gestor, false);
    }

}
