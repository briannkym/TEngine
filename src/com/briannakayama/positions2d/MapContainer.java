package com.briannakayama.positions2d;

import com.briannakayama.listener.MapLoadListener;

public interface MapContainer extends CollisionMap{

	public void load(int x, int y);
	
	public void setMapLoadListener(MapLoadListener mll);
}
