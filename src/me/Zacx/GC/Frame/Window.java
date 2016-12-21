package me.Zacx.GC.Frame;

import java.awt.Dimension;

import javax.swing.JFrame;

import me.Zacx.GC.Main.Core;
import me.Zacx.GC.Sprites.Sprite;

public class Window extends JFrame {	
	
	private static final long serialVersionUID = 2154556468486978968L;

	public Window(int width, int height, String title, Core c) {
		super(title);
				
		this.setPreferredSize(new Dimension(width, height));
		//frame.setMaximumSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(width, height));
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.add(c);
		this.setVisible(true);
		
		c.start();
	}
	
	@Override
	public void validate() {
		super.validate();
		Sprite.validateAll();
	}
	
}
