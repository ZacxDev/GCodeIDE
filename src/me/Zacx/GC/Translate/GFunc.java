package me.Zacx.GC.Translate;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import me.Zacx.GC.Main.Access;

public class GFunc {

	public static List<GFunc> functions = new LinkedList<GFunc>();
	
	public static int lx, ly;
	public int x, y, width = 200, height = 200, i, j;
	public GCF type;
	
	public GFunc(String s) {
		parse(s);
		functions.add(this);
	}
	
	public GFunc(GCF type, int x, int y, int width, int height, int i, int j) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.i = i;
		this.j = j;
		
		functions.add(this);
	}
	
	public void visualize(Graphics g) {
		
		int emx = Access.c.bEm.getX(), emy = Access.c.bEm.getY() + Access.c.bEm.getHeight();
		
		if (type == GCF.G00) {
			g.setColor(Color.PINK);
			g.drawLine(lx, ly, emx + x, emy + y);
		} else if (type == GCF.G01) {
			g.setColor(Color.black);
			g.drawLine(lx, ly, lx + x, ly + y);
		} else if (type == GCF.G02) {
			g.setColor(Color.green);
			//g.drawArc(lx, ly, x, y, i, j);
			g.drawArc(lx,ly,i,j,x,-y);
		} else if (type == GCF.G03) {
			g.setColor(Color.green);
			g.drawArc(lx,ly,i,j,-x,y);
		}
		lx += x;
		ly += y;
	}
	
	public void parse(String s) {
		for (String split : s.split(" "))
		for (int i = 0; i < split.length(); i++) {
			if (Character.isAlphabetic(split.charAt(i))) {
				if (split.charAt(i) == 'G')
					type = GCF.valueOf(split.substring(i));
				else if (split.charAt(i) == 'X') {
					x = Integer.parseInt(split.substring(i+1));
				} else if (split.charAt(i) == 'Y')
					y = Integer.parseInt(split.substring(i+1))*-1;
				else if (split.charAt(i) == 'I')
					this.i = Integer.parseInt(split.substring(i+1));
				else if (split.charAt(i) == 'J')
					j = Integer.parseInt(split.substring(i+1));
			}
		}
		
	}
	
	public static void renderAll(Graphics g) {
		lx = Access.c.bEm.getX();
		ly = Access.c.bEm.getY() + Access.c.bEm.getHeight();
		for (int i = 0; i < functions.size(); i++)
			functions.get(i).visualize(g);
	}
}