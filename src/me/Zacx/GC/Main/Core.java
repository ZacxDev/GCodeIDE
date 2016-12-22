package me.Zacx.GC.Main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import me.Zacx.GC.Frame.Window;
import me.Zacx.GC.Input.KeyHandle;
import me.Zacx.GC.Sprites.BasicEmulator;
import me.Zacx.GC.Sprites.CodePane;
import me.Zacx.GC.Sprites.KeyInputSprite;
import me.Zacx.GC.Sprites.Sprite;

public class Core extends Canvas implements Runnable {

	private static final long serialVersionUID = 7508225493302619737L;

	private Window window;
	
	private Thread thread;
	private boolean running = false;

	public static int WIDTH = 1080, HEIGHT = 720;
	public static int basew = WIDTH / 100, baseh = HEIGHT / 100;

	private Random r;
	private KeyHandle keyHandle;
	public KeyInputSprite currentKeySprite;
	private BasicEmulator bEm;

	public File pfFolder = new File(System.getenv("ProgramFiles")
			+ "/GCodeIDE/");
	public File resources = new File(System.getenv("ProgramFiles")
			+ "/GCodeIDE/resources/");
	private ArrayList<File> toInstall = new ArrayList<File>();

	public Core() {
		System.setProperty("sun.awt.noerasebackground", "true");
		Toolkit.getDefaultToolkit().setDynamicLayout(true);
		if (!pfFolder.exists()) {
			pfFolder.mkdir();
			resources.mkdir();

			final String path = "resources";
			final File jarFile = new File(getClass().getProtectionDomain()
					.getCodeSource().getLocation().getPath());

			if (jarFile.isFile()) { // Run with JAR file

				JarFile jar = null;
				try {
					jar = new JarFile(jarFile);
				} catch (IOException e) {
					e.printStackTrace();
				}

				installDirFromJar("resources", jar);
				for (int i = 0; i < toInstall.size(); i++) {
					File f = toInstall.get(i);
					System.out.println("INSTALLING: " + f.getPath());
					installDirFromJar(f.getPath(), jar);
					System.out.println("INDEX: " + i);
				}

			} else { // Run with IDE
				installDir("");
				for (int i = 0; i < toInstall.size(); i++) {
					File f = toInstall.get(i);
					String deepPath = f.getPath().replace(
							f.getPath().substring(
									0,
									f.getPath().lastIndexOf("resources")
											+ "resources".length() + 1), "");
					System.out.println(deepPath);
					System.out.println("INSTALLING: " + deepPath);
					installDir(deepPath);
					System.out.println("INDEX: " + i);
				}
			}
		}

		new Access(this);
		keyHandle = Access.keyHandle;
		r = new Random();
		currentKeySprite = new CodePane(50, 50);
		bEm = new BasicEmulator(currentKeySprite.getWidth() + basew * 7, 50);

		window = new Window(WIDTH, HEIGHT, "Game", this);
		this.addKeyListener(keyHandle);
		this.requestFocus();

	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop() {

		try {
			thread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {

		System.out.println("vape");

		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {

				tick();
				delta--;

			}
			if (running) {
				render();
				frames++;

				if (System.currentTimeMillis() - timer > 1000) {

					timer += 1000;
					System.out.println("FPS: " + frames);
					frames = 0;

				}
			}
		}
		stop();

	}

	private long ufto = 60L;
	private void tick() {
		ufto--;
		
		if (ufto <= 0){
			updateFrame();
			ufto = 60L;
		}
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();

		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		do {
			do {
		
		Graphics g = bs.getDrawGraphics();

		// System.out.println("X: " + camera.camX + "Y: " + camera.camY);
		
		// Should not be able to see red
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		Sprite.renderAll(g);
		
		g.dispose();
			} while (bs.contentsRestored());
		bs.show();
		} while (bs.contentsLost());
	}
	
	private void updateFrame() {
		WIDTH = window.getWidth();
		HEIGHT = window.getHeight();
		basew = WIDTH / 100;
		baseh = HEIGHT / 100;
		
		Sprite.validateAll();
	}

	public static void main(String[] args) {
		new Core();
	}

	public static int clamp(int var, int min, int max) {

		if (var >= max) {
			return var = max;
		} else if (var <= min) {
			return var = min;
		} else {
			return var;
		}

	}

	public void installDir(String path) {
		System.out.println(path);
		final URL url = getClass().getResource("/resources/" + path);
		if (url != null) {
			try {
				final File apps = new File(url.toURI());
				for (File app : apps.listFiles()) {
					System.out.println(app);
					System.out.println("copying..." + app.getPath() + " to "
							+ pfFolder.getPath());
					String deepPath = app.getPath().replace(
							app.getPath().substring(
									0,
									app.getPath().lastIndexOf("resources")
											+ "resources".length() + 1), "");
					System.out.println(deepPath);

					try {

						File f = new File(resources.getPath() + "/" + deepPath);
						if (getExtention(app) != null) {
							FileOutputStream resourceOS = new FileOutputStream(
									f);
							byte[] byteArray = new byte[1024];
							int i;
							InputStream classIS = getClass().getClassLoader()
									.getResourceAsStream(
											"resources/" + deepPath);
							// While the input stream has bytes
							while ((i = classIS.read(byteArray)) > 0) {
								// Write the bytes to the output stream
								resourceOS.write(byteArray, 0, i);
							}
							// Close streams to prevent errors
							classIS.close();
							resourceOS.close();
						} else {
							f.mkdir();
							toInstall.add(f);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (URISyntaxException ex) {
				// never happens
			}
		}

	}

	public void installDirFromJar(String path, JarFile jar) {

		final Enumeration<JarEntry> entries = jar.entries(); // gives ALL
																// entries in
																// jar
		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			if (entry.getName().startsWith(path + "/")) {
				if (entry.isDirectory()
						&& entry.getName().endsWith("resources/")) {
					System.out.println("RESOURCES FOLDER");
					continue;
				}
				System.out.println("copying..." + entry.getName() + " to "
						+ pfFolder.getPath());
				// String deepPath = path.replace(path.substring(0,
				// path.lastIndexOf("resources") + "resources".length()), "");
				// System.out.println(deepPath);
				System.out.println("ENTRY: " + entry.getName());
				String deepPath = entry.getName()
						.replaceFirst("resources/", "");
				System.out.println("copying...");
				try {

					// File f = new File(resources.getPath() + "/" + deepPath);
					File f = new File(resources.getPath() + "/" + deepPath);
					if (getExtention(f) != null) {

						FileOutputStream resourceOS = new FileOutputStream(f);
						byte[] byteArray = new byte[1024];
						int i;
						InputStream classIS = getClass().getClassLoader()
								.getResourceAsStream("resources/" + deepPath);
						// While the input stream has bytes
						while ((i = classIS.read(byteArray)) > 0) {
							// Write the bytes to the output stream
							resourceOS.write(byteArray, 0, i);
						}
						// Close streams to prevent errors
						classIS.close();
						resourceOS.close();

					} else {
						System.out.println("NEW DIR: " + f.getPath());
						f.mkdir();
						toInstall.add(f);
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			jar.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getExtention(File f) {
		String name = f.getName();
		if (name.lastIndexOf(".") > 0) {
			System.out.println(name.substring(name.lastIndexOf(".")));
			return name.substring(name.lastIndexOf("."));
		}
		return null;
	}

	public void reset() {
	
	}
	
	public KeyInputSprite getCurrentKeySprite() {
		return currentKeySprite;
	}
}