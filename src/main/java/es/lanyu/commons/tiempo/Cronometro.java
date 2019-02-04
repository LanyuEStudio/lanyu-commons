package es.lanyu.commons.tiempo;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public interface Cronometro {
	
	Collection<? extends Cronometrable> getCronometrables();
	Map<Cronometrable, Temporizador> getTemporizadores(); 
	
	default float getTiempoRestante(Cronometrable cronometrable){
		Temporizador t = getTemporizadores().get(cronometrable);
		
		return (t != null) ? t.getTiempoRestante() : 0;
	}
	
	default Temporizador nuevoTemporizador(Cronometrable cronometrable){
		return nuevoTemporizador(cronometrable, cronometrable.getTiempoParaContar());
	}
	
	default Temporizador nuevoTemporizador(Cronometrable cronometrable, float tiempo){
		Temporizador t = new Temporizador();
		getTemporizadores().put(cronometrable, t);
		
		return t.iniciarTemporizador(tiempo);
	}
	
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
	
	public class Temporizador {
		float tiempoRestante = 0;
		
		float getTiempoRestante() {
			return tiempoRestante;
		}
		
		Temporizador iniciarTemporizador(float tiempo){
			tiempoRestante = tiempo;
			return this;
		}
		
		void actualizar(float deltaTime){
			if(tiempoRestante > 0)
				tiempoRestante -= deltaTime;
			if(tiempoRestante < 0)
				tiempoRestante = 0;
		}
		
	}
	
}
