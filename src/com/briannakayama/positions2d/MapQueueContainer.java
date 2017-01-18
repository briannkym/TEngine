package com.briannakayama.positions2d;

import java.util.HashMap;
import java.util.Map;

import com.briannakayama.listener.MapLoadListener;

public class MapQueueContainer implements MapContainer{

	private MapLoadListener mll;
	private int tileWidth;
	private int tileHeight;
	private int queue_index = 0;
	
	private Map<Integer, CollisionMap> maps = new HashMap<Integer, CollisionMap>();
	private CollisionMap[] queue;
	
	
	
	public MapQueueContainer(int mapWidth, int mapHeight, int tileWidth, int tileHeight, int queue_size){
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
	}
	
	@Override
	public boolean addCollisionInstance(UCollision col) {
		
		return false;
	}

	@Override
	public boolean hasCollision(UCollision col) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkCollision(UCollision col, boolean notifyCollidables) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean moveCollision(UCollision col, boolean notifyCollidables) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeCollision(UCollision col) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void load(int x, int y) {
		mll.loadMap(x,y);
	}

	@Override
	public void setMapLoadListener(MapLoadListener mll) {
		this.mll = mll;
	}

	@Override
	public void setContainer(MapContainer cmp) {
		// TODO Auto-generated method stub
		
	}

}
