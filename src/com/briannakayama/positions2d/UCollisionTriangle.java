package com.briannakayama.positions2d;

import com.briannakayama.domain.Type;


public class UCollisionTriangle extends UCollision{

	short offsetX2 = 16, offsetY2 = 16;
		
	public UCollisionTriangle(Position p, Type type){
		super(p, type);
	}
	
	public UCollisionTriangle(Position p, Type type, int offsetX2, int offsetY2){
		super(p, type);
		setOffsetX2(offsetX2);
		setOffsetY2(offsetY2);
	}
	
	public void setOffsetX2(int offsetX2){
		this.offsetX2 = (short) offsetX2;
	}
	
	public void setOffsetY2(int offsetY2){
		this.offsetY2 = (short) offsetY2;
	}
	
	@Override
	public boolean intersects(UCollisionRect u) {
		int x1 = p.xPos + offsetX;
		int y1 = p.yPos + offsetY;
		int x2 = u.p.xPos + u.offsetX;
		int y2 = u.p.yPos + u.offsetY;
		int xdir = offsetX2 - offsetX;
		int ydir = offsetY2 - offsetY;
		boolean inX1 = x1 <= x2 && x2 <= x1 + xdir;
		inX1 |= x1 + xdir <= x2 + u.width && x2 + u.width <= x1;
		boolean inX2 = x2 <= x1 && x1 <= x2 + u.width;
		int ymid;
		if (xdir > 0){
			ymid = (x2 - x1) * ydir/xdir + y1;
		} else {
			ymid = (x2 + u.width - x1) * ydir/xdir + y1;
		}
		boolean inY = ymid <= y2 && y2 <= y1 + ydir;
		inY |= y1 + ydir <= y2 + u.height && y2 + u.height <= ymid;
		inY |= y2 <= y1 && y1 <= y2 + u.height && inX2;
		inY |= y2 <= ymid && ymid <= y2 + u.height && inX1;
		inX1 |= inX2;
		return inX1 && inY;
	}

	@Override
	public boolean intersects(UCollisionCirc u) {
		return u.intersects(this);
	}

	public boolean intersects(UCollisionTriangle u) {
		return oneWayIntersect(u) || u.oneWayIntersect(this);
	}
	
	private static int[][] points = {{0,0},{0,0},{0,0}};	
	private boolean oneWayIntersect(UCollisionTriangle u){
		boolean collision = false;
		int x1 = p.xPos + offsetX;
		int y1 = p.yPos + offsetY;
		int x2 = u.p.xPos + u.offsetX;
		int y2 = u.p.yPos + u.offsetY;
		int ydir = offsetY2 - offsetY;
		int xdir = offsetX2 - offsetX;
		points[1][1] = u.offsetY2 - u.offsetY; //y2dir
		points[2][0] = u.offsetX2 - u.offsetX; //x2dir
		points[2][1] = points[1][1];
		
		for(int[] point : points){
			int x = x2 + point[0];
			int y = y2 + point[1];
			int ymid = ((x - x1)*ydir)/xdir + y1;
			boolean inY = ymid <= y && y <= y1 + ydir;
			inY |= y1 + ydir <= y && y <= ymid;
			boolean inX = x1 <= x && x <= x1 + xdir;
			inX |= x1 + xdir <= x && x <= x1;
			collision |= inX && inY;
		}
		
		//Check the two cases where a point may not be lying on the inside.
		int ymid = ((x1 - x2) * points[1][1]) / points[2][0] + y2;
		boolean inY = y1 <= ymid && ymid <= y1 + ydir;
		inY |= y1 + ydir <= ymid && ymid <= y1;
		boolean inX = x2 <= x1 && x1 <= x2 + points[2][0];
		inX |= x2 + points[2][0] <= x1 && x1 <= x2;
		collision |= inY && inX;
		
		return collision;
	}
	
	@Override
	public boolean accept(UCollision u) {
		return u.intersects(this);
	}
	
}
