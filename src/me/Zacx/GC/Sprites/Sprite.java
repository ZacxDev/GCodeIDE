package me.Zacx.GC.Sprites;

import java.util.ArrayList;
import java.util.List;

import me.Zacx.GC.Main.Core;

public abstract class Sprite {

	public static List<Sprite> sprites = new ArrayList<Sprite>();
	
	protected int x, y, width, height;
	
	public Sprite(int x, int y) {
		this.x = x;
		this.y = y;
		
		sprites.add(this);
	}
	
	public abstract void validate();
	
	public static void validateAll() {
		for (int i = 0; i < sprites.size(); i++) {
			sprites.get(i).validate();
		}
	}
	
}
