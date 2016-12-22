package me.Zacx.GC.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import me.Zacx.GC.Main.Access;
import me.Zacx.GC.Main.Core;

public class KeyHandle implements KeyListener {

	private Core c;
	
	public KeyHandle() {
		c = Access.c;
	}
	
	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_ESCAPE)
			System.exit(0);
		else
			c.getCurrentKeySprite().handleKeyInput(key);
	}

	public void keyReleased(KeyEvent e) {
		
	}

	public void keyTyped(KeyEvent e) {
		
	}

	
	
}
