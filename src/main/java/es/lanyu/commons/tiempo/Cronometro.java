package es.lanyu.commons.tiempo;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**Interfaz para cronometrar {@link Cronometrable}s
 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
 * @version 1.0
 * @since 1.0
 */
public interface Cronometro {
	
	/**Devuelve una {@link Collection} de todos los {@link Cronometrable}s
	 * que esta cronometrando.
	 * @return {@code Collection} de {@code Cronometrable} gestionados
	 */
	Collection<? extends Cronometrable> getCronometrables();
	
	/**Devuelve un {@code Map} que relaciona cada {@link Cronometrable}
	 * con su {@link Temporizador} restante.
	 * @return {@code Map<Cronometrable, Temporizador>} que gestiona este cronometro
	 */
	Map<Cronometrable, Temporizador> getTemporizadores(); 
	
	/**Devuelve el tiempo restante a cronometrar o cero si ha acabado.
	 * @param cronometrable {@link Cronometrable} del que se quiere conocer el tiempo restante
	 * @return Tiempo restante del objeto {@code cronometrable}
	 */
	default float getTiempoRestante(Cronometrable cronometrable){
		Temporizador t = getTemporizadores().get(cronometrable);
		
		return (t != null) ? t.getTiempoRestante() : 0;
	}
	
	/**Inicia un {@link Temporizador} para llevar la cuenta del tiempo de {@code cronometrable}.
	 * @param cronometrable {@link Cronometrable} a controlar
	 * @return {@code Temporizador} creado para cronometrar a {@code cronometrable}
	 */
	default Temporizador nuevoTemporizador(Cronometrable cronometrable){
		return nuevoTemporizador(cronometrable, cronometrable.getTiempoParaCronometrar());
	}
	
	/**Inicia un {@link Temporizador} para llevar la cuenta del tiempo de {@code cronometrable} pero
	 * iniciando la cuenta con {@code tiempo} en vez de {@link Cronometrable#getTiempoParaCronometrar()}. 
	 * @param cronometrable Objeto a cronometrar
	 * @param tiempo Tiempo inicial del nuevo {@code Temporizador}
	 * @return {@code Temporizador} asociado a {@code cronometrable} que se inicia con {@code tiempo}
	 */
	default Temporizador nuevoTemporizador(Cronometrable cronometrable, float tiempo){
		Temporizador t = new Temporizador();
		getTemporizadores().put(cronometrable, t);
		
		return t.iniciarTemporizador(tiempo);
	}
	
	/**Actualiza todos los {@link Temporizador}es de {@link Cronometro#getTemporizadores()}. En caso
	 * de terminar el tiempo de un {@link Cronometrable} se le elimina de los temporizadores a controlar.
	 * @param deltaTime Tiempo para actualizar en cada {@code Temporizador}
	 */
	default void actualizarTiempos(float deltaTime){
		if(!getTemporizadores().isEmpty()) {
			Iterator<Temporizador> it = getTemporizadores().values().iterator();
			while(it.hasNext()){
				Temporizador t = it.next();
				t.actualizar(deltaTime);
				if(t.tiempoRestante <= 0)
					it.remove();
			}
		}
	}
	
	/**Clase auxiliar para cronometrar los {@link Cronometrable}s asociados.
	 * @author <a href="https://github.com/Awes0meM4n">Awes0meM4n</a>
	 * @version 1.0
	 * @since 1.0
	 */
	public class Temporizador {
		float tiempoRestante = 0;
		
		/**Devuelve el tiempo restante del {@code Temporizador}.
		 * @return Tiempo restante del {@code Temporizador}
		 */
		float getTiempoRestante() {
			return tiempoRestante;
		}
		
		/**Devuelve el {@code Temporizador} estableciendo el {@code tiempo}
		 * pasado como el tiempo restante.
		 * @param tiempo Tiempo para controlar
		 * @return {@code Temporizador} con el tiempo restante modificado a {@code tiempo}
		 */
		Temporizador iniciarTemporizador(float tiempo){
			tiempoRestante = tiempo;
			return this;
		}
		
		/**Resta {@code deltaTime} del tiempo restante en el {@code Temporizador} hasta llegar a cero.
		 * @param deltaTime Tiempo a actualizar
		 */
		void actualizar(float deltaTime){
			if(tiempoRestante > 0)
				tiempoRestante -= deltaTime;
			if(tiempoRestante < 0)
				tiempoRestante = 0;
		}
		
	}
	
}
