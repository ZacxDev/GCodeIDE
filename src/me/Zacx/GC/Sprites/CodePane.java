package me.Zacx.GC.Sprites;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import me.Zacx.GC.Main.Core;

public class CodePane extends Sprite {
	
	public static List<CodePane> codepanes = new ArrayList<CodePane>();
	
	public CodePane(int x, int y) {
		super(x, y);
		
		width = Core.basew * 64;
		height = Core.baseh * 75;
		
		codepanes.add(this);
	}
	
	public void validate() {
		width = Core.basew * 64;
		height = Core.baseh * 75;
	}
	
	public void tick() {}
	
	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(x, y, width, height);
		
	}
	
}