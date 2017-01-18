package com.briannakayama.positions2d;


public interface CollisionMap {
	public boolean addCollisionInstance(UCollision col);
	public boolean hasCollision(UCollision col);
	public boolean checkCollision(UCollision col, boolean notifyCollidables);
	public boolean moveCollision(UCollision col, boolean notifyCollidables);
	public boolean removeCollision(UCollision col);
	public void setContainer(MapContainer cmp);
}
