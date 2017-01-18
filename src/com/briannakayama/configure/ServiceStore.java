package com.briannakayama.configure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 * Stores services by indexing them by their classes and interfaces that they inherit.
 * 
 * Example usage:
 * 
 * <pre>
 * public static void main(String[] args) {
 * 	ServiceStore s = new ServiceStore();
 * 	s.addInstance(new JFrame());
 * 	System.out.println(s);
 * 	try {
 * 		s.addInstance(new JPanel());
 * 	} catch (Exception e) {
 * 		System.out.println(e);
 * 	}
 * 
 * 	System.out.println(s);
 * }
 * </pre>
 * 
 * @author brian
 * 
 */
public class ServiceStore {

	public static void main(String[] args){
		System.out.println("test");
	}
	
	private Map<Class<?>, Object> services = new HashMap<Class<?>, Object>();

	/**
	 * 
	 * @param service
	 */
	public <T> void addInstance(T service) {
		Class<?> class_ = service.getClass();
		List<Class<?>> inherited = new ArrayList<Class<?>>();
		for (Class<?> c : class_.getInterfaces()) {
			inherited.add(c);
		}
		for (Class<?> current = class_.getSuperclass(); !current.toString()
				.equals(Object.class.toString()); current = current
				.getSuperclass()) {
			inherited.add(current);
			for (Class<?> c : class_.getInterfaces()) {
				inherited.add(c);
			}
		}
		Map<Object, List<Class<?>>> common = new HashMap<Object, List<Class<?>>>();

		for (Class<?> c : inherited) {
			Object value = services.get(c);
			try {
				service.getClass().cast(value);
				services.put(c, service);
			} catch (ClassCastException e) {
				services.remove(c);
				if (!common.containsKey(value)) {
					common.put(value, new ArrayList<Class<?>>());
				}
				common.get(value).add(c);
			}
		}

		if (common.size() != 0) {
			String s = service.getClass()
					+ " shares the following types which cannot be mapped to a service: \n";
			for (Object value : common.keySet()) {
				s += value.getClass().getName() + ":" + common.get(value)
						+ "\n";
			}
			throw new IllegalArgumentException(s);
		}
	}

	/**
	 * n
	 * 
	 * @param service
	 * @return
	 */
	public <T> T getInstance(Class<T> service) {
		return service.cast(services.get(service));
	}

	@Override
	public String toString() {
		String keys = "";
		for (Class<?> c : services.keySet()) {
			keys += c.getName() + ": " + services.get(c).getClass().getName()
					+ "()\n";
		}

		return "Mapped services: " + keys;
	}
}
