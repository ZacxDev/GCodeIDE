package me.Zacx.GC.Sprites;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class CodePane extends Sprite {
	
	public static List<CodePane> codepanes = new ArrayList<CodePane>();
	
	public CodePane(int x, int y) {
		super(x, y);
		
		codepanes.add(this);
	}
	
	public void tick() {}
	
	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(x, y, width, height);
		
	}
	
}