package com.briannakayama.positions2d;

public final class RelPosition extends Position{
	
	Position p;
	
	public RelPosition(Position p){
		this.p = p;
	}

	public RelPosition(Position p, int xPos, int yPos){
		this.xPos = xPos;
		this.yPos = yPos;
	}

	@Override
	public int getXpos() {
		return p.getXpos() + xPos;
	}

	@Override
	public int getYpos() {
		return p.getXpos() + yPos;
	}
	
}
