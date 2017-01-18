package com.briannakayama.jvm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.briannakayama.draw2d.URender;

public class URenderJVM extends URender{

	
	public static final int TOP_SPACE = 40;
	
	BufferedImage bI;
	double rotation = 0.0;
	Graphics2D buffer;
	// The Dimensions of the Projection.
	private int width, height, x = 0, y = 0;
	JFrame jF;
	JPanel jP;

	// True for virtual full screen mode.
	private boolean virtual = false;
	
	public URenderJVM(CameraJVM c, String title){
		this.bI = c.bI;
		this.buffer=c.buffer;
		jF = new JFrame(title);
		jF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jF.setResizable(false);
		jP = new JPanel();
	}
	
	public URenderJVM(CameraJVM c, String title, boolean virtual){
		this(c, title);
		this.virtual = virtual;
	}

	public void windowScreen() {
		Container c = jF.getContentPane();		
		jP.setPreferredSize(new Dimension(bI.getWidth(), bI.getHeight()));
		this.x = 0;
		this.y = 0;
		this.width = bI.getWidth();
		this.height = bI.getHeight();
		c.setLayout(new BorderLayout());
		c.add(jP, BorderLayout.NORTH);
		jF.setIgnoreRepaint(true);
		jF.pack();
		jF.setVisible(true);
	}

	public void fullScreen() {
		Container c = jF.getContentPane();
		c.setLayout(new BorderLayout());
		jF.setUndecorated(true);
		jF.setIgnoreRepaint(true);

		Dimension d;

		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		boolean fullScreen = gd.isFullScreenSupported();

		if (fullScreen && !virtual) {
			d = new Dimension(bI.getWidth(), bI.getHeight());

			DisplayMode currentDisplay = gd.getDisplayMode();
			int dx = currentDisplay.getWidth() - bI.getWidth();
			int dy = currentDisplay.getHeight() - bI.getHeight();

			width = bI.getWidth();
			height = bI.getHeight();
			x = dx;
			y = dy;

			// Find the smallest display mode that fits the projection.
			DisplayMode[] modes = gd.getDisplayModes();
			for (DisplayMode dm : modes) {
				int tdx = dm.getWidth() - bI.getWidth();
				int tdy = dm.getHeight() - bI.getHeight();

				if (tdy + tdx < dx + dy && tdy >= 0 && tdx >= 0) {
					dx = tdx;
					dy = tdy;
					currentDisplay = dm;
				}
			}

			jP.setPreferredSize(d);
			c.add(jP, BorderLayout.CENTER);
			gd.setFullScreenWindow(jF);
			// Attempt to change the display mode, and wait a ms for the screen
			// to change.
			try {
				if (gd.isDisplayChangeSupported()) {
					gd.setDisplayMode(currentDisplay);
					x = dx / 2;
					y = dy / 2;
				}
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			// IF FSEM is not supported just stretch the screen to the native
			// resolution.
			d = Toolkit.getDefaultToolkit().getScreenSize();

			height = d.height - TOP_SPACE * 2;
			width = (bI.getWidth() * height) / bI.getHeight();
			y = TOP_SPACE;
			x = (d.width - width) / 2;

			jP.setPreferredSize(d);
			c.add(jP, BorderLayout.NORTH);
			jF.pack();
			jF.setLocation(0, 0);
			jF.setVisible(true);
		}

		jP.setBackground(Color.BLACK);
		jF.setBackground(Color.BLACK);
		jF.setAlwaysOnTop(true);
	}
	
	public JFrame getFrame(){
		return jF;
	}
	
	@Override
	public void update() {
		try {
			Graphics g = jP.getGraphics();
			if (g != null) {
				AffineTransform at = new AffineTransform();
				at.rotate(rotation,
						bI.getWidth() / 2, bI.getHeight() / 2);
				buffer.setTransform(at);
				g.drawImage(bI, x, y, width, height, null);
				Toolkit.getDefaultToolkit().sync();
				g.dispose();

				buffer.setColor(new Color(0xFFFFFFFF));
				buffer.fillRect(0, 0, bI.getWidth(), bI.getHeight());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

}
