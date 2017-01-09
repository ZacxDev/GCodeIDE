package me.Zacx.GC.Frame;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar {

	private JMenuBar menuBar;
	private Window window;
	
	public MenuBar(final Window window) {
		menuBar = new JMenuBar();
		this.window = window;
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_C);
		
		JMenuItem open = new JMenuItem("Open");
		open.setMnemonic(KeyEvent.VK_O);
		open.setToolTipText("Open a fgc file");
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openFileDialog(window);
			}
		});
		
		fileMenu.add(open);
		
		menuBar.add(fileMenu);
		
	}
	
	public JMenuBar getJMenuBar() {
		return menuBar;
	}
		
	public void openFileDialog(Component c) {
		JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showOpenDialog(c) == JFileChooser.APPROVE_OPTION) {
		  File file = fileChooser.getSelectedFile();
		  // load from file
		}
	}
}