package com.briannakayama.jvm;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import com.briannakayama.listener.ButtonAdapter;
import com.briannakayama.listener.ButtonListener;
import com.briannakayama.domain.Type;

public class ButtonAdapterJVM extends ButtonAdapter implements KeyListener{

	private Map<Integer,Type> map;
	
	public ButtonAdapterJVM(Map<Integer,Type> map){
		this.map = map;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Type t = map.get(e.getKeyCode());
		if (t!=null){
			for (ButtonListener b : bInner){
				b.buttonPressed(t);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Type t = map.get(e.getKeyCode());
		if (t!=null){
			for (ButtonListener b : bInner){
				b.buttonReleased(t);
			}
		}
	}

}
