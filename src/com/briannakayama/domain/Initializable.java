package com.briannakayama.domain;

import java.io.Externalizable;

public interface Initializable<T> extends Externalizable{

	public void initialize();
	
	public T getInitializable();
}
