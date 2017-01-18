package com.briannakayama.domain;

public class UNull extends Updatable {

	private static UNull instance = new UNull();

	public UNull() {
		index = Integer.MIN_VALUE;
	}

	public static UNull getInstance() {
		return instance;
	}

	@Override
	public void update() {
	}



}
