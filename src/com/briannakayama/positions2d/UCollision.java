package com.briannakayama.positions2d;

import java.util.ArrayList;
import java.util.List;

import com.briannakayama.listener.CollisionListener;
import com.briannakayama.domain.Type;
import com.briannakayama.domain.Updatable;


public abstract class UCollision extends Updatable{
	
	public Position p;
	public short offsetX = 0, offsetY = 0;
	public Type type;
	int prevXPos = 0;
	int prevYPos = 0;
	private int HashP = 0;
	CollisionMap map = null;
	private List<CollisionListener> cInner = null;	
	
	public UCollision(Position p, Type type){
		this.p = p;
		this.type = type;
	}
	
	public void notifyMap(UCollision c){
		if(cInner != null){
			for (CollisionListener cl : cInner){
				cl.collision(c.type, c.p);
			}
		}
	}
	
	public void addCollidableListener(CollisionListener cl){
		if(cInner == null){
			cInner = new ArrayList<CollisionListener>();
		}
		cInner.add(cl);
	}
	
	public void removeCollidableListener(CollisionListener cl){
		cInner.remove(cl);
		if(cInner.size() == 0){
			cInner = null;
		}
	}
	
	@Override
	public void update() {
		int nextHash = ((p.getXpos()<<16) ^ (p.getYpos())) ^ type.hashCode();
		switch(HashP - nextHash){
		default:
			map.moveCollision(this, true);
			prevXPos = p.xPos;
			prevYPos = p.yPos;
		case 0:
		}
		
		HashP = nextHash;
	}
	
	public abstract boolean accept(UCollision u);	
	public abstract boolean intersects(UCollisionRect u);
	public abstract boolean intersects(UCollisionCirc u);
	public abstract boolean intersects(UCollisionTriangle u);
	
	public boolean intersects(UCollision u) {
		return false;
	}
	
}
