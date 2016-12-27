package me.Zacx.GC.Sprites;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public abstract class Sprite {

	public static List<Sprite> sprites = new ArrayList<Sprite>();
	
	protected int x, y, width, height, maxx, maxy;
	
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
	
	public void tick() {}
	
	public static void tickAll() {
		for (int i = 0; i < sprites.size(); i++)
			sprites.get(i).tick();
	}
	
	public abstract void render(Graphics g);
	
	public static void renderAll(Graphics g) {
		for (int i = 0; i < sprites.size(); i++) {
			sprites.get(i).render(g);
		}
	}
	
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
}
