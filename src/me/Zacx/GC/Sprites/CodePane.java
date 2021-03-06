package me.Zacx.GC.Sprites;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import me.Zacx.GC.Error.ErrorType;
import me.Zacx.GC.Error.GCError;
import me.Zacx.GC.Main.Core;
import me.Zacx.GC.Translate.GFunc;

public class CodePane extends KeyInputSprite {
	
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
	
	public void tick() {
		
		GFunc.functions.clear();
		GCError.errors.clear();
		for (int i = 0; i < body.size(); i++) {
			String line = body.get(i);
			try {
			new GFunc(line);
			} catch (IllegalArgumentException e) {
				//render errors next TODO
				new GCError(ErrorType.FUNCTION_NOT_FOUND, i);
			} catch (StringIndexOutOfBoundsException e) {
				new GCError(ErrorType.INCOMPLETE_LINE, i);
			}
		}
		
	}
	
	public void render(Graphics g) {
		//g.setFont(Fonts.small.fnt);
		g.setColor(Color.gray);
		g.fillRect(x, y, width, height);
		
		g.setColor(Color.white);
		int iy = 10;
		for (int i = 0; i < body.size(); i++) {
			g.drawString(body.get(i), x, y + iy);
			iy += 10;
		}
		
		g.setColor(Color.green);
		String text = body.get(bi).substring(0, li);
		int tw = g.getFontMetrics().stringWidth(text);
		g.fillRect(x + tw, y + bi*10, 3, 15);		
	}
}