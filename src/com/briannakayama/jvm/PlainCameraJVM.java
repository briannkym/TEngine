package com.briannakayama.jvm;

import java.awt.image.BufferedImage;

import com.briannakayama.draw2d.UImage;

public class PlainCameraJVM extends CameraJVM{
	
	
	public PlainCameraJVM(int width, int height){
		super(width, height);
	}

	public PlainCameraJVM(CameraJVM u){
		super(u);
	}
	
	@Override
	public void drawImg(UImage<BufferedImage> image) {
		int x = image.getPosition().getXpos()- p.getXpos();
		int y = image.getPosition().getYpos() - p.getYpos();
		buffer.drawImage(image.getImage(), x, y, null);
	}

}
