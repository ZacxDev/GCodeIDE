package me.Zacx.GC.Sprites;

import java.awt.Color;
import java.awt.Graphics;

import me.Zacx.GC.Main.Access;
import me.Zacx.GC.Main.Core;

public class BasicEmulator extends Sprite {

	public BasicEmulator(int x, int y) {
		super(x, y);
		width = Core.basew * 64;
		height = Core.baseh * 75;
		
		
	}

	public void validate() {
		
		maxx = Core.WIDTH - 50;
		maxy = Core.HEIGHT - 50;
		
		width = Core.basew * 32;
		height = Core.baseh * 35;
		System.out.println(maxx);
		System.out.println(x + width);
		if (x + width > maxx)
			width = maxx - x;
		if (height > maxy)
			height = maxy;
		
		x = Access.c.currentKeySprite.getWidth() + 75;
		//y = 50;
	}

	public void tick() {}
	
	public void render(Graphics g) {
		
		g.setColor(Color.white);
		g.fillRect(x, y, width, height);
		
	}
	
	
}
