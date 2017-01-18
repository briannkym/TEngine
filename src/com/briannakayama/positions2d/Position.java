package com.briannakayama.positions2d;


public abstract class Position{
	protected int xPos = 0;
	protected int yPos = 0;
	
	public Position(){
		
	}
	
	public Position(int xPos, int yPos){
		this.xPos = xPos;
		this.yPos = yPos;
	}
	

	public void setXPos(int xPos){
		this.xPos = xPos;
	}
	
	public void setYPos(int yPos){
		this.yPos = yPos;
	}
	
	public abstract int getXpos();
	
	public abstract int getYpos();	
}
