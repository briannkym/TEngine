package com.briannakayama.listener;

import java.util.ArrayList;
import java.util.List;

import com.briannakayama.domain.Type;
import com.briannakayama.domain.TypeFactory;
import com.briannakayama.draw2d.Camera;
import com.briannakayama.positions2d.Position;
import com.briannakayama.positions2d.RelPosition;

public abstract class TouchAdapter {

	public final Type NO_TOUCH;
	public final Type TOUCH;
	public final Type L_TOUCH;
	public final Type R_TOUCH;
	
	protected List<TouchListener> tInner = new ArrayList<TouchListener>();
	protected Position p;
	
	public TouchAdapter(Camera<?> c, TypeFactory t) {
		p = new RelPosition(c.getPosition());
		NO_TOUCH = t.getType("no_touch");
		TOUCH = t.defineType("touch", "no_touch");
		L_TOUCH = t.defineType("l_touch", "touch");
		R_TOUCH = t.defineType("r_touch", "touch");
	}
	
	
	public void lTouch(int xPos, int yPos) {
		p.setXPos(xPos);
		p.setYPos(yPos);
		for (TouchListener t : tInner){
			t.touch(L_TOUCH, xPos, yPos);
		}
	}
	
	public void rTouch(int xPos, int yPos) {
		p.setXPos(xPos);
		p.setYPos(yPos);
		for (TouchListener t : tInner){
			t.touch(R_TOUCH, xPos, yPos);
		}
	}
}
