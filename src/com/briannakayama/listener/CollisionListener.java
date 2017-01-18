package com.briannakayama.listener;

import com.briannakayama.domain.Type;
import com.briannakayama.positions2d.Position;

public interface CollisionListener {
	
	public void collision(Type t, Position p);
}
