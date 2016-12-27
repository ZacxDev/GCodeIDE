package me.Zacx.GC.Input;

import java.awt.Font;

public enum Fonts {

	small(12), medium(20), large(30);
	
	public Font fnt;
	private Fonts(int size) {
		fnt = new Font("ariel", 0, size);
	}
	
}