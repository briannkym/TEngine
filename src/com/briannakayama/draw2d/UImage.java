package com.briannakayama.draw2d;

import com.briannakayama.domain.Updatable;
import com.briannakayama.positions2d.Position;


public class UImage<T> extends Updatable {

	transient T image;
	Position p;
	transient Camera<T> camera;
	
	public UImage(){
		
	}

	public UImage(T image){
		this.image = image;
	}
	
	public T getImage(){
		return this.image;
	}
	
	public void setImage(UImage<T> image){
		this.image = image.image;
	}
	
	@Override
	public void update() {
		camera.drawImg(this);
	}
	
	public Position getPosition(){
		return p;
	}

	public void setPosition(Position p){
		this.p = p;
	}

	/**
	 * @param camera the camera to set
	 */
	public void setCamera(Camera<T> camera) {
		this.camera = (Camera<T>) camera;
	}
	
}
