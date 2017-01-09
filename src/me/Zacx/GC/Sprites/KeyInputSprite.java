package me.Zacx.GC.Sprites;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import me.Zacx.GC.Input.KeyHandle;
import me.Zacx.GC.Main.Access;

public abstract class KeyInputSprite extends Sprite {

	protected List<String> body = new LinkedList<String>();
	//line index, body index (of cursor)
	protected int li, bi;
	private KeyHandle keyHandle;
	
	public KeyInputSprite(int x, int y) {
		super(x, y);
		body.add("");
		li = 0;
		bi = 0;
		keyHandle = Access.c.keyHandle;
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
			else
				li = body.get(bi).length();
			if (li > body.get(bi).length())
				li = body.get(bi).length();
		} else if (key == KeyEvent.VK_LEFT) {
			if (li > 0)
				li--;
		} else if (key == KeyEvent.VK_RIGHT) {
			if (li < body.get(bi).length())
				li++;
		} else if (key == KeyEvent.VK_SPACE) { 
			body.set(bi, body.get(bi).substring(0, li) + " " + body.get(bi).substring(li, body.get(bi).length()));
			li++;
		} else if (key == KeyEvent.VK_MINUS) {
			body.set(bi, body.get(bi).substring(0, li) + "-" + body.get(bi).substring(li, body.get(bi).length()));
			li++;
		} else if (key == KeyEvent.VK_BACK_SPACE) {
			if (body.get(bi).length() > 0 && li > 0) {
				body.set(bi, body.get(bi).substring(0, li-1) + body.get(bi).substring(li, body.get(bi).length()));
				li--;
			} else if (bi > 0) {
				String lastLine = body.get(bi);
				body.remove(bi);
				bi--;
				li = body.get(bi).length();
				body.set(bi, body.get(bi) + lastLine);
			}
		} else if (key == KeyEvent.VK_DELETE) {
			if ((body.get(bi).length() > 0 || li > 0) && li < body.get(bi).length())
				body.set(bi, body.get(bi).substring(0, li) + body.get(bi).substring(li+1, body.get(bi).length()));
		}else if (key == KeyEvent.VK_V && keyHandle.ctrlDown) {
			try {
				String data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
				body.set(bi, body.get(bi).substring(0, li) + data + body.get(bi).substring(li, body.get(bi).length()));
				li += data.length();
			} catch (HeadlessException e) {
				e.printStackTrace();
			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			if (KeyEvent.getKeyText(key).length() == 1) {
				body.set(bi, body.get(bi).substring(0, li) + KeyEvent.getKeyText(key) + body.get(bi).substring(li, body.get(bi).length()));
				li++;
			}
		}
	}
}