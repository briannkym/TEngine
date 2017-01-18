package com.briannakayama.domain;


public class Type {

	int code = -1;
	Type[] parent = {};

	public Type() {

	}

	Type(int code) {
		this.code = code;
	}

	Type(int code, Type... parent) {
		this(code);
		this.parent = parent;
	}

	void setParents(Type... parent) {
		this.parent = parent;
	}

	public boolean isParent(Type t) {
		boolean parent = false;

		for (Type p : t.parent) {
			parent |= p == this;
			parent |= isParent(p);
		}

		return parent;
	}

	@Override
	public boolean equals(Object o) {
		Type t = (Type) o;
		boolean equals = t.code == this.code;
		equals |= isParent(t);
		equals |= t.isParent(this);

		return equals;
	}

}
