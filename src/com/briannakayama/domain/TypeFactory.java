package com.briannakayama.domain;

import java.util.HashMap;
import java.util.Map;

public class TypeFactory {
	
	private int nextCode = -1;
	private Map<String, Type> nameTypes = new HashMap<String, Type>();
	
	
	public Type getType(String name){
		Type inst = nameTypes.get(name);
		if (inst == null) {
			nextCode += 1;
			inst = new Type(nextCode);
			nameTypes.put(name, inst);
		}
		return inst;
	}	
	
	public Type defineType(String name, String... parents){
		Type inst = nameTypes.get(name);
		if(parents.length != 0){
			Type[] parent = new Type[parents.length];
			for (int i = 0; i < parents.length; i++){
				parent[i] = getType(parents[i]);
			}
			if(inst == null){
				nextCode += 1;
				inst = new Type(nextCode, parent);
				nameTypes.put(name, inst);
			} else {
				inst.setParents(parent);
			}
		} else if (inst == null) {
			nextCode += 1;
			inst = new Type(nextCode);
			nameTypes.put(name, inst);
		}
		return inst;
	}
}
