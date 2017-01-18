package com.briannakayama.positions2d;

import com.briannakayama.domain.Updatable;

public class UPhysics extends Updatable{
	
	public static final byte MAX_VELOCITY = 16;
	Position p;
	byte xVel, yVel, xAcc, yAcc;


	public UPhysics(Position p){
		this.p = p;
	}
	
	public Position getPosition(){
		return p;
	}
	
	/**
	 * @return the xVel
	 */
	public byte getxVel() {
		return xVel;
	}

	/**
	 * @param xVel the xVel to set
	 */
	public void setxVel(byte xVel) {
		this.xVel = xVel;
	}

	/**
	 * @return the yVel
	 */
	public byte getyVel() {
		return yVel;
	}

	/**
	 * @param yVel the yVel to set
	 */
	public void setyVel(byte yVel) {
		this.yVel = yVel;
	}

	/**
	 * @return the xAcc
	 */
	public byte getxAcc() {
		return xAcc;
	}

	/**
	 * @param xAcc the xAcc to set
	 */
	public void setxAcc(byte xAcc) {
		this.xAcc = xAcc;
	}

	/**
	 * @return the yAcc
	 */
	public byte getyAcc() {
		return yAcc;
	}

	/**
	 * @param yAcc the yAcc to set
	 */
	public void setyAcc(byte yAcc) {
		this.yAcc = yAcc;
	}
	
	@Override
	public void update() {
		p.xPos = p.getXpos() + xVel;
		p.yPos = p.getYpos() + yVel;
		xVel += xAcc;
		yVel += yAcc;
		if (xVel > MAX_VELOCITY){
			xVel = MAX_VELOCITY;
		} else if (xVel < -MAX_VELOCITY){
			xVel = (byte)(-MAX_VELOCITY);
		}
		if (yVel > MAX_VELOCITY){
			yVel = MAX_VELOCITY;
		} else if (yVel < -MAX_VELOCITY){
			yVel = (byte)(-MAX_VELOCITY);
		}
	}

}
