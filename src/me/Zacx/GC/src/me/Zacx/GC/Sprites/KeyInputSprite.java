package me.Zacx.GC.Sprites;

import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

public abstract class KeyInputSprite extends Sprite {

	protected List<String> body = new LinkedList<String>();
	//line index, body index (of cursor)
	protected int li, bi;
	
	public KeyInputSprite(int x, int y) {
		super(x, y);
		body.add("");
		li = 0;
		bi = 0;
	}
	
	public void handleKeyInput(int key) {
		if (key == KeyEvent.VK_ENTER) {
			body.add("");
			bi++;
			li = 0;
		} else if (key == KeyEvent.VK_UP) {
			if (bi > 0)
				bi--;
		} else if (key == KeyEvent.VK_DOWN) {
			if (bi < body.size()-1)
				bi++;
			if (li > body.get(bi).length())
				li = body.get(bi).length();
		} else if (key == KeyEvent.VK_LEFT) {
			if (li > 0)
				li--;
		} else if (key == KeyEvent.VK_RIGHT) {
			if (li < body.get(bi).length())
				li++;
		} else if (key == KeyEvent.VK_BACK_SPACE) {
			if (body.get(bi).length() > 0 && li > 0) {
				body.set(bi, body.get(bi).substring(0, li-1) + body.get(bi).substring(li, body.get(bi).length()));
				li--;
			}
		} else if (key == KeyEvent.VK_DELETE) {
			if (body.get(bi).length() > 0 || li > 0)
				body.set(bi, body.get(bi).substring(0, li) + body.get(bi).substring(li+1, body.get(bi).length()));
		} else {
			body.set(bi, body.get(bi).substring(0, li) + KeyEvent.getKeyText(key) + body.get(bi).substring(li, body.get(bi).length()));
			li++;
		}
	}
}