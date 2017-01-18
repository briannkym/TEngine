package com.briannakayama.positions2d;

public final class AbsPosition extends Position{


	public AbsPosition(){
		
	}
	
	public AbsPosition(int xPos, int yPos){
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public AbsPosition(Position p){
		this.yPos = p.getXpos();
		this.yPos = p.getYpos();
	}
	

	public void setPos(Position p){
		this.yPos = p.getXpos();
		this.yPos = p.getYpos();
	}
	
	@Override
	public int getXpos() {
		return xPos;
	}

	@Override
	public int getYpos() {
		return yPos;
	}

}
