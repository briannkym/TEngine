package com.briannakayama.configure;

import java.util.HashMap;
import java.util.Map;



public class CObject implements Configurable<Object> {
	private Class<? extends Scriptable> cClass;
	Map<String, Configurable<?>> configurations;

	public CObject(Class<? extends Scriptable> cClass, Map<String, Configurable<?>> configurations) {
		this.cClass = cClass;
		this.configurations = configurations;
	}

	@Override
	public Object configure() {
		try {
			Scriptable cObject = cClass.newInstance();
			Map<String, Object> map = new HashMap<String, Object>();
			for (String s : configurations.keySet()) {
				map.put(s, configurations.get(s).configure());
			}

			return cObject.initialize(map);
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			throw new ConfigurationError("Unable to build object "
					+ cClass.getName(), e);
		}
	}
	
	public CObject copyOveride(Map<String, Configurable<?>> configurations){
		Map<String, Configurable<?>> new_configurations = new HashMap<String, Configurable<?>>(this.configurations);
		for (String s : configurations.keySet()){
			new_configurations.put(s, configurations.get(s));
		}
		return new CObject(this.cClass, new_configurations);
	}
	
}
