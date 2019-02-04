package es.lanyu.commons.identificable;

import java.util.Comparator;

import com.esotericsoftware.jsonbeans.Json;
import com.esotericsoftware.jsonbeans.JsonSerializer;
import com.esotericsoftware.jsonbeans.JsonValue;

public abstract class AbstractNombrable extends IdentificableString implements Nombrable {

	private static final long serialVersionUID = -4738964822375774603L;
	
	static Comparator<AbstractNombrable> comparador = new Comparator<AbstractNombrable>() {
		@Override
		public int compare(AbstractNombrable o1, AbstractNombrable o2) {
			if(o1 == null) return -1;
			if(o2 == null) return 1;
			return o1.getNombre().compareTo(o2.getNombre());
		}
	};
	
	protected String nombre;
		
	public static <T extends AbstractNombrable> Json getSerializador (Class<T> clase) {
		Json json = new Json();
		
		json.setSerializer(clase, new JsonSerializer<T>() {
			@SuppressWarnings("rawtypes")
			public void write (Json json, T nombrable, Class knownType) {
				json.writeObjectStart();
				json.writeValue("id", nombrable.getIdentificador());
				json.writeValue("nombre", nombrable.getNombre());
				json.writeObjectEnd();
			}
			
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public T read (Json json, JsonValue jsonData, Class type) {
				T nombrable;
				try {
					nombrable = (T) type.newInstance();
					nombrable.setIdentificador(jsonData.getString("id"));
					nombrable.setNombre(jsonData.getString("nombre"));
					return nombrable;
				} catch (InstantiationException e) {
					e.printStackTrace();
					return null;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					return null;
				}
			}
		});
		
		return json;
	}
	
	public static Comparator<AbstractNombrable> getComparadorPorNombre() {
		return comparador;
	}
	
	@Override
	public String getNombre() {
		if(nombre != null)
			return nombre;
		else
			return getIdentificador();
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return getNombre();
	}
	
}


