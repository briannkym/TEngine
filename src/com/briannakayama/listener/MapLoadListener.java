package com.briannakayama.listener;

import com.briannakayama.positions2d.CollisionMap;

public interface MapLoadListener {

	public CollisionMap loadMap(int x, int y);
}
