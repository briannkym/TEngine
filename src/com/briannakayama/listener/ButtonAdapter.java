package com.briannakayama.listener;

import java.util.ArrayList;
import java.util.List;

public abstract class ButtonAdapter {

	protected List<ButtonListener> bInner = new ArrayList<ButtonListener>();
	
	public void addButtonListener(ButtonListener bl){
		bInner.add(bl);
	}
	
	public void removeButtonListener(ButtonListener bl){
		bInner.remove(bl);
	}
	
}
