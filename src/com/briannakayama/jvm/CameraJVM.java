package com.briannakayama.jvm;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.briannakayama.draw2d.Camera;
import com.briannakayama.positions2d.Position;

public abstract class CameraJVM extends Camera<BufferedImage>{

	Graphics2D buffer;
	BufferedImage bI;
	Position p;
	
	public CameraJVM(int width, int height){
		bI = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		buffer = bI.createGraphics();
	}
	
	public void setPosition(Position p){
		this.p = p;
	}	
	
	public int getWidth(){
		return bI.getWidth();
	}
	
	public int getHeight(){
		return bI.getHeight();
	}

	public CameraJVM(CameraJVM u){
		this.buffer = u.buffer;
		this.bI = u.bI;
		this.p = u.p;
	}

}
