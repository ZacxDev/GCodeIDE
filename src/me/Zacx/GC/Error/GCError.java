package me.Zacx.GC.Error;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import me.Zacx.GC.Main.Access;
import me.Zacx.GC.Main.Core;

public class GCError {
	
	public static List<GCError> errors = new LinkedList<GCError>();
	
	private static int x, y, maxx, maxy, width, height;
	private int line;
	private ErrorType type;
	
	public GCError(ErrorType type, int line) {
		
		for (int i = 0; i < errors.size(); i++) {
			if (errors.get(i).line == line && errors.get(i).type == type)
				return;
		}
		
		this.type = type;
		this.line = line;
		
		width = Core.basew * 64;
		height = Core.baseh * 10;
		
		errors.add(this);
	}
	
	public static void validate() {
		width = Core.basew * 64;
		height = Core.baseh * 10;
	}
	
	public void render(Graphics g, int x, int y) {
		
		g.setColor(Color.red);
		g.drawString("Runtime exception " + type.name().toLowerCase() + " on line " + (line+1), x, y);
	}
	
	public static void renderAll(Graphics g) {
		x = 50;
		y = Access.c.currentKeySprite.getHeight() + 75;
		maxy = Core.HEIGHT - 50;
		
		g.setColor(Color.gray);
		g.drawRect(x, y, width, height);
		
		int iy = y;
		for (int i = 0; i < errors.size(); i++) {
			iy += 12;
			if (maxy > iy)
				errors.get(i).render(g, x, iy);
			else
				break;
		}
	}	
}