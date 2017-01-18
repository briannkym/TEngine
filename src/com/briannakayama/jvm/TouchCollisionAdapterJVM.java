package com.briannakayama.jvm;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.briannakayama.domain.TypeFactory;
import com.briannakayama.draw2d.Camera;
import com.briannakayama.listener.TouchCollisionAdapter;

public class TouchCollisionAdapterJVM extends TouchCollisionAdapter implements
		MouseListener {

	public TouchCollisionAdapterJVM(int radius, Camera<?> c, TypeFactory t) {
		super(radius, c, t);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (e.getButton()) {
		case MouseEvent.BUTTON1:
			this.lTouch(e.getX(), e.getY());
			break;
		case MouseEvent.BUTTON3:
			this.rTouch(e.getX(), e.getY());
			break;
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
