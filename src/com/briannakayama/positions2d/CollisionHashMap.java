package com.briannakayama.positions2d;

import java.util.ArrayList;
import java.util.List;

public class CollisionHashMap implements CollisionMap {
	private short hash_shift = 6;

	private int offsetX = 0;
	private int offsetY = 0;

	MapContainer container = null;

	private List<UCollision>[][] collisions;

	@SuppressWarnings("unchecked")
	public CollisionHashMap(int width, int height) {
		collisions = new ArrayList[((width - 1) >> hash_shift) + 1][((height - 1) >> hash_shift) + 1];
		for (int x = 0; x < collisions.length; x++) {
			for (int y = 0; y < collisions.length; y++) {
				collisions[x][y] = new ArrayList<UCollision>();
			}
		}
	}

	public CollisionHashMap(int width, int height, int offsetX, int offsetY) {
		this(width, height);
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	
	public CollisionHashMap(int width, int height, int offsetX, int offsetY, short hash_shift) {
		this(width, height, offsetX, offsetY);
		this.hash_shift = hash_shift;
	}
	
	public CollisionHashMap(int width, int height, short hash_shift) {
		this(width, height);
		this.hash_shift = hash_shift;
	}

	@Override
	public boolean addCollisionInstance(UCollision col) {
		if (col.map == null) {
			// Arithmetic shift right.
			int x = (col.p.xPos - offsetX) >> hash_shift;
			int y = (col.p.yPos - offsetY) >> hash_shift;
			if (0 <= x && x <= collisions.length && 0 <= y
					&& y <= collisions[x].length) {
				collisions[x][y].add(col);

				col.map = this;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean checkCollision(UCollision col, boolean notifyCollidables) {
		boolean collided = false;
		int x = col.p.xPos >> hash_shift;
		int y = col.p.yPos >> hash_shift;
		for (int x0 = Math.max(x - 1, 0); x0 < Math.min(x + 1,
				collisions.length); x0++) {
			for (int y0 = Math.max(y - 1, 0); y0 < Math.min(y + 1,
					collisions[x].length); y0++) {
				for (UCollision current : collisions[x0][y0]) {
					if (col != current && current.accept(col)) {
						if (notifyCollidables) {
							col.notifyMap(current);
							current.notifyMap(col);
						}
						collided = true;
					}
				}
			}
		}

		return collided;
	}

	@Override
	public boolean moveCollision(UCollision col, boolean notifyCollidables) {
		if (hasCollision(col)) {
			int x = col.prevXPos >> hash_shift;
			int y = col.prevYPos >> hash_shift;
			collisions[x][y].remove(col);
			checkCollision(col, true);
			x = col.p.xPos >> hash_shift;
			y = col.p.yPos >> hash_shift;
			collisions[x][y].add(col);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeCollision(UCollision col) {
		if (hasCollision(col)) {
			int x = col.p.xPos >> hash_shift;
			int y = col.p.yPos >> hash_shift;
			collisions[x][y].remove(col);
			col.map = null;
			return true;
		}
		return false;
	}

	@Override
	public boolean hasCollision(UCollision col) {
		return col.map == this;
	}

	@Override
	public void setContainer(MapContainer container) {
		this.container = container;
		
	}

}
