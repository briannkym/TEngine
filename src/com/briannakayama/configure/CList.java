package com.briannakayama.configure;

import java.util.ArrayList;
import java.util.List;

public class CList implements Configurable<List<?>> {
	private List<Configurable<?>> configurations;
	
	public CList(List<Configurable<?>> configurations){
		this.configurations = configurations;
	}

	@Override
	public List<?> configure() {
		List<Object> list = new ArrayList<Object>(configurations.size());
		for (Configurable<?> c : configurations){
			list.add(c.configure());
		}
		return list;
	}
	
}
