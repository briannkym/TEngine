package com.briannakayama.positions2d;

import com.briannakayama.domain.Type;


public class UCollisionRect  extends UCollision{

	short offsetX = 0, offsetY = 0, height = 16, width = 16;
		
	public UCollisionRect(Position p, Type type){
		super(p, type);
	}
	
	public UCollisionRect(Position p, Type type, int height, int width){
		super(p, type);
		setHeight(height);
		setWidth(width);
	}
	
	public void setHeight(int height){
		if (height > 0){
			this.height = (short) height;
		}
	}

	public void setWidth(int width){
		if (width > 0){
			this.width = (short) width;
		}
	}
	
	
	@Override
	public boolean intersects(UCollisionRect u) {
		int x1 = p.xPos + offsetX;
		int y1 = p.yPos + offsetY;
		int x2 = u.p.xPos + u.offsetX;
		int y2 = u.p.yPos + u.offsetY;
		boolean inX = false;
		inX |= x1 <= x2 && x2 <= x1 + width;
		inX |= x2 <= x1 && x1 <= x2 + u.width;
		boolean inY = false;
		inY |= y1 <= y2 && y2 <= y1 + height;
		inY |= y2 <= y1 && y1 <= y2 + u.height;
		return inX && inY;
	}

	@Override
	public boolean intersects(UCollisionCirc u) {
		return u.intersects(this);
	}

	@Override
	public boolean intersects(UCollisionTriangle u) {
		return u.intersects(this);
	}

	@Override
	public boolean accept(UCollision u) {
		return u.intersects(this);
	}


}
