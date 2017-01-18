package com.briannakayama.listener;

import com.briannakayama.domain.TypeFactory;
import com.briannakayama.draw2d.Camera;
import com.briannakayama.positions2d.UCollision;
import com.briannakayama.positions2d.UCollisionCirc;

public abstract class TouchCollisionAdapter extends TouchAdapter {

	private UCollision collision;

	public TouchCollisionAdapter(int radius, Camera<?> c, TypeFactory t) {
		super(c, t);
		collision = new UCollisionCirc(p,
				NO_TOUCH, radius);
	}

	public void lTouch(int xPos, int yPos) {
		super.lTouch(xPos, yPos);
		collision.type = L_TOUCH;
		collision.update();
		collision.type = NO_TOUCH;
	}

	public void rTouch(int xPos, int yPos) {
		super.lTouch(xPos, yPos);
		collision.type = R_TOUCH;
		collision.update();
		collision.type = NO_TOUCH;
	}
	
	public UCollision getCollision(){
		return collision;
	}
}
