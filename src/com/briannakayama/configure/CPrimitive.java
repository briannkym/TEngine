package com.briannakayama.configure;

public class CPrimitive<T> implements Configurable<T>{

	private T value;
	
	CPrimitive(T value){
		this.value = value;
	}
	
	@Override
	public T configure() {
		return value;
	}

}
