package com.briannakayama.positions2d;

import com.briannakayama.domain.Type;


/**
 * TODO, test this honker of a class.
 * @author brian
 *
 */
public class UCollisionCirc extends UCollision{
	
	short offsetX = 0, offsetY = 0;
	int radius_2 = 64;	
	
	public UCollisionCirc(Position p, Type type){
		super(p, type);
	}
	
	public UCollisionCirc(Position p, Type type, int radius){
		super(p, type);
		setRadius(radius);
	}
	
	public void setRadius(int radius){
		if (radius > 0){
			this.radius_2 = radius * radius;
		}
	}
	
	
	@Override
	public boolean intersects(UCollisionRect u) {
		boolean collision = false;
		int x1 = p.xPos + offsetX;
		int y1 = p.yPos + offsetY;
		int x2 = u.p.xPos + u.offsetX;
		int y2 = u.p.yPos + u.offsetY;
		
		//Get the distance from the edges of the rectangle.
		int x_diff1 = x1 - x2;
		int y_diff1 = y1 - y2;
		int x_diff2 = x_diff1 - u.width;
		int y_diff2 = y_diff1 - u.height;
		x_diff1 = x_diff1 * x_diff1; 
		y_diff1 = y_diff1 * y_diff1; 
		x_diff2 = x_diff2 * x_diff2; 
		y_diff2 = y_diff2 * y_diff2;

		//Record if we're within the width or height of the rect.
		boolean inX = x2 <= x1 && x1 <= x2 + u.width;
		boolean inY = y2 <= y1 && y1 <= y2 + u.height;
		
		//Check to see if we're in one of the corners.
		collision |= x_diff1 + y_diff1 <= radius_2;
		collision |= x_diff1 + y_diff2 <= radius_2;
		collision |= x_diff2 + y_diff1 <= radius_2;
		collision |= x_diff2 + y_diff2 <= radius_2;

		//Check to see if we're along one of the width borders.
		collision |= inX && y_diff1 <= radius_2;
		collision |= inX && y_diff2 <= radius_2;
		

		//Check to see if we're along one of the height borders.
		collision |= inY && x_diff1 <= radius_2;
		collision |= inY && x_diff2 <= radius_2;
		
		//Check to see if we're inside the rectangle. 
		collision |= inX && inY;
		
		return collision;
	}

	@Override
	public boolean intersects(UCollisionCirc u) {
		int x_diff = p.xPos + offsetX - u.p.xPos - u.offsetX;
		int y_diff = p.yPos + offsetY - u.p.yPos - u.offsetY;
		int distance = x_diff*x_diff + y_diff*y_diff;
		return distance <= radius_2 + u.radius_2;
	}

	@Override
	public boolean intersects(UCollisionTriangle u) {
		boolean collision = false;
		int x1 = p.xPos + offsetX;
		int y1 = p.yPos + offsetY;
		int x2 = u.p.xPos + u.offsetX;
		int y2 = u.p.yPos + u.offsetY;
		int x1dir = x1 - x2;
		int y1dir = y1 - y2;
		int x2dir = u.offsetX2 - u.offsetX;
		int y2dir = u.offsetY2 - u.offsetY;
		int ymid = ((x1dir)*y2dir)/x2dir + y2;
		
		//Check the inside.
		boolean inY = ymid <= y1 && y1 <= y2 + y2dir;
		inY |= y2 + y2dir <= y1 && y1 <= ymid;
		boolean inX = x2 <= x1 && x1 <= x2 + x2dir;
		inX |= x2 + x2dir <= x1 && x1 <= x2;
		collision |= inY && inX;
		
		//Check the slope
		int cnum = (x1dir * x2dir + y1dir * y2dir);
		int cden = (x2dir * x2dir + y2dir * y2dir);
		int ax = x1dir - cnum*x2dir/cden;
		int ay = y1dir - cnum*y2dir/cden;
		ax = ax * ax;
		ay = ay * ay;
		collision |= inX && ax + ay <= radius_2;
		
		int x_diff1 = x1dir * x1dir;
		int y_diff1 = y1dir * y1dir;
		int x_diff2 = x1dir - x2dir;
		int y_diff2 = y1dir - y2dir;
		x_diff2 = x_diff2 * x_diff2;
		y_diff2 = y_diff2 * y_diff2;

		//Check the non-slope sides
		collision |= inX && y_diff2 <= radius_2;
		collision |= inY && x_diff1 <= radius_2;
		
		//Check the corners.
		collision |= x_diff1 + y_diff1 <= radius_2;
		collision |= x_diff1 + y_diff2 <= radius_2;
		collision |= x_diff2 + y_diff2 <= radius_2;
		
		
		return collision;
	}

	@Override
	public boolean accept(UCollision u) {
		return u.intersects(this);
	}

}
