package es.lanyu.commons.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

/**Clase de utilidades para trabajar con operaciones sobre ficheros
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public class Util {

	/**Vuelca una cadena en un fichero con la codificacion marcada
	 * @param informe Cadena a escribir
	 * @param fichero Ruta del fichero
	 * @param charSet Codificacion a usar
	 * @throws IOException Produced by failed or interrupted I/O operations
	 * @see Util#guardarInformeEnFichero(String texto, Writer escritor)
	 */
	public static void guardarInformeEnFichero(String informe, String fichero, String charSet) throws IOException{
		guardarInformeEnFichero(informe, new OutputStreamWriter(new FileOutputStream(fichero), charSet));
	}
	/**Vuelca una cadena en un fichero con la codificacion UTF-8
	 * @param informe Cadena a escribir
	 * @param fichero Ruta del fichero
	 * @throws IOException Produced by failed or interrupted I/O operations
	 * @see Util#guardarInformeEnFichero(String, String, String)
	 */
	public static void guardarInformeEnFichero(String informe, String fichero) throws IOException{
		guardarInformeEnFichero(informe, fichero, "UTF-8");
	}
	/**Vuelca una cadena en el {@link File} fichero
	 * @param informe Cadena a escribir
	 * @param fichero File donde escribir la cadena
	 * @throws IOException Produced by failed or interrupted I/O operations
	 * @see Util#guardarInformeEnFichero(String texto, Writer escritor)
	 */
	public static void guardarInformeEnFichero(String informe, File fichero) throws IOException{
		guardarInformeEnFichero(informe, new FileWriter(fichero));
	}
	/**Vuelca una cadena en un fichero con la codificacion UTF-8 rodeandolo de un {@link BufferedWriter}
	 * (es mas eficiente, ver <a href="https://docs.oracle.com/javase/8/docs/api/java/io/BufferedWriter.html">
	 * documentacion</a>
	 * @param texto Cadena a escribir
	 * @param escritor {@link Writer} para encapsular
	 * @throws IOException Produced by failed or interrupted I/O operations
	 */
	private static void guardarInformeEnFichero(String texto, Writer escritor) throws IOException{
		BufferedWriter bWriter = new BufferedWriter(escritor);
		bWriter.write(texto);
//		bWriter.flush();
		bWriter.close();
	}

	//TODO Escribir cadena al final del archivo
	public static void agregarTextoEnFinalArchivo (String texto, Writer escritor){
		System.out.println("Hay que implementarlo");
	}

	/**Copia un archivo de la ruta {@code origen} a la ruta {@code destino}
	 * @param origen ruta hasta el archivo a copiar
	 * @param destino ruta donde hacer la copia
	 * @throws IOException Produced by failed or interrupted I/O operations
	 */
	public static void copiarArchivo(String origen, String destino) throws IOException{
		Files.copy(FileSystems.getDefault().getPath(origen),
	    		FileSystems.getDefault().getPath(destino),
	    		StandardCopyOption.REPLACE_EXISTING);
	}

	/**Cuenta las lineas de un fichero en la ruta {@code fichero}. Codigo copiado de
	 * <a href="http://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java">
	 * stackoverflow</a>
	 * @param fichero ruta del fichero
	 * @return numero de lineas que contiene el fichero
	 * @throws IOException Produced by failed or interrupted I/O operations
	 */
	public static long contarLineas(String fichero) throws IOException {
		long lineas = 0;
		
		//Respuesta de @Ernestas Gruodis NO REQUIERE JAVA 8 y es muy rapido 
//	    try (BufferedInputStream is = new BufferedInputStream(new FileInputStream(fichero), 1024)) {
//
//	        byte[] c = new byte[1024];
//	        boolean empty = true,
//	                lastEmpty = false;
//	        int read;
//	        while ((read = is.read(c)) != -1) {
//	            for (int i = 0; i < read; i++) {
//	                if (c[i] == '\n') {
//	                    lineas++;
//	                    lastEmpty = true;
//	                } else if (lastEmpty) {
//	                    lastEmpty = false;
//	                }
//	            }
//	            empty = false;
//	        }
//
//	        if (!empty) {
//	            if (lineas == 0) {
//	                lineas = 1;
//	            } else if (!lastEmpty) {
//	                lineas++;
//	            }
//	        }
//	    }
		
		//Respuesta de @msayag REQUIERE JAVA 8 y suele ser aun mas rapido
		try (Stream<String> lines = Files.lines(new File(fichero).toPath(), Charset.defaultCharset())) {
			lineas = lines.count();
		}
	    
		return lineas;
	}
	
	/**Devuelve un {@code File[]} con los archivos en la carpeta {@code rutaDirectorio} con la {@code extension}
	 * solicitada.
	 * @param rutaDirectorio Ruta de la carpeta donde buscar
	 * @param extension Extension de los archivos devueltos (por ejemplo {@code .png})
	 * @return Array de {@code File} de los archivos que cumplen con la extension en la carpeta
	 * @throws Exception Se produce si la ruta no pertenece a un directorio valido
	 */
	public static File[] archivosConExtension (String rutaDirectorio, String extension) throws Exception {
		if (rutaDirectorio != null){
			File[] archivos;
			File dir = new File(rutaDirectorio);
			if(dir.isDirectory()){
				archivos = dir.listFiles(new FilenameFilter() {
					@Override
					public boolean accept(File dir, String name) {
						return name.toLowerCase().endsWith(extension);
					}
				});
			}
			else {
				throw new Exception("La ruta no pertenece a un directorio " + rutaDirectorio);
			}
				
			return archivos;
		}
		else {
			throw new Exception("La ruta no puede ser nula");
		}
	}
	
	/**Devuelve el directorio que corresponde a la ubicacion de {@code clase}
	 * @param clase para la que se quiere conocer su ruta
	 * @return {@code File} al directorio que contiene a {@code clase}
	 */
	public static File getDirectorioDeClase (Class<?> clase) {
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
             directorio = new File("");//".");//El punto puede perjudicar
         }
         
	     return directorio;
	}
}
