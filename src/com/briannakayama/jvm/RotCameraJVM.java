package com.briannakayama.jvm;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import com.briannakayama.draw2d.RotCamera;
import com.briannakayama.draw2d.UImage;

public class RotCameraJVM extends CameraJVM implements RotCamera{

	double rotation = 0;
	
	public RotCameraJVM(int width, int height){
		super(width, height);
	}

	public RotCameraJVM(CameraJVM u){
		super(u);
	}
	
	@Override
	public void drawImg(UImage<BufferedImage> image) {
		int x = image.getPosition().getXpos()- p.getXpos();
		int y = image.getPosition().getYpos() - p.getYpos();
		BufferedImage bI = image.getImage();
		
		double midX = bI.getWidth() >> 1;
		double midY = bI.getHeight() >> 1;
		AffineTransform aT = AffineTransform.getRotateInstance(rotation, midX, midY);
		AffineTransformOp aTO = new AffineTransformOp(aT, AffineTransformOp.TYPE_BILINEAR);
		buffer.drawImage(bI, aTO, x, y);
	}

	@Override
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

}
