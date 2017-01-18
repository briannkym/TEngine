package com.briannakayama.draw2d;

import com.briannakayama.positions2d.Position;

public abstract class Camera<T> {

	protected Position p;

	public Position getPosition(){
		return p;
	}
	
	public void setPosition(Position p){
		this.p = p;
	}
	
	public abstract void drawImg(UImage<T> image);
}
