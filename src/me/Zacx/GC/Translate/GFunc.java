package me.Zacx.GC.Translate;

import java.awt.Color;
import java.awt.Graphics;

public class GFunc {

	public static int lx, ly;
	public int x, y, width, height, i, j;
	public GCF type;
	
	public GFunc(String s) {
		parse(s);
	}
	
	public GFunc(GCF type, int x, int y, int width, int height, int i, int j) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.i = i;
		this.j = j;
	}
	
	public void visualize(Graphics g) {
		if (type == GCF.G00) {
			g.setColor(Color.pink);
			g.drawLine(lx, ly, x, y);
		} else if (type == GCF.G01) {
			g.setColor(Color.black);
			g.drawLine(lx, ly, x, y);
		} else if (type == GCF.G02) {
			g.setColor(Color.black);
			g.drawArc(lx, ly, width, height, i, j);
		} else if (type == GCF.G03) {
			g.setColor(Color.black);
			g.drawArc(lx, ly, width, height, -i, -j);
		}
	}
	
	public int parse(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (Character.isAlphabetic(s.charAt(i))) {
				if (s.charAt(i) == 'G')
					type = GCF.valueOf(s);
				else if (s.charAt(i) == 'X')
					x = Integer.parseInt(s.substring(i+1));
				else if (s.charAt(i) == 'Y')
					y = Integer.parseInt(s.substring(i+1));
				else if (s.charAt(i) == 'I')
					this.i = Integer.parseInt(s.substring(i+1));
				else if (s.charAt(i) == 'J')
					j = Integer.parseInt(s.substring(i+1));
			}
		}
		return -1;
	}
	
}