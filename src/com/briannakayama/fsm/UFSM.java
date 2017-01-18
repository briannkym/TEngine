package com.briannakayama.fsm;

import com.briannakayama.domain.Updatable;

public class UFSM extends Updatable{

	UFState state;
	
	public UFSM(UFState initial){
		this.state = initial;
	}
	
	public UFState getState(){
		return this.state;
	}
	
	public void updateState(UFState state){
		this.state = state;
	}
	
	@Override
	public void update() {
		state.updateState(this);
	}	
}
