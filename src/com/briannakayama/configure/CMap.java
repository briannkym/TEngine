package com.briannakayama.configure;

import java.util.HashMap;
import java.util.Map;

public class CMap implements Configurable<Map<?, ?>> {
	Map<CPrimitive<?>, Configurable<?>> configurations;

	public CMap() {
		this(new HashMap<CPrimitive<?>, Configurable<?>>());
	}

	public CMap(Map<CPrimitive<?>, Configurable<?>> map) {
		this.configurations = map;
	}

	@Override
	public Map<?, ?> configure() {
		Map<Object, Object> map = new HashMap<Object, Object>();

		for (CPrimitive<?> p : configurations.keySet()) {
			map.put(p.configure(), configurations.get(p).configure());
		}

		return map;
	}
}
