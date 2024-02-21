package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import entity.Tank;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	// SCREEN SETTINGS
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 2;
	public final int tileSize = originalTileSize * scale; // 32x32 tile
	public final int maxScreenCol = 46;
	public final int maxScreenRow = 29;
	public final int screenWidth = tileSize * maxScreenCol; // pixels
	public final int screenHeight = tileSize * maxScreenRow; // pixels
	final int FPS = 60;
	
	public ArrayList<Tank> listeTank;
	Thread gameThread;
	public TileManager tileM = new TileManager(this);
	public CollisionChecker cChecker = new CollisionChecker(this);

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
	}

	@Override
	public void run() {
		double drawInterval = 1_000_000_000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;

		while (gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				repaint();
				delta--;
			}
		}
	}

	public void startGameThread() {
		Iterator<Tank> iter = this.listeTank.iterator();
		while (iter.hasNext()) {
			Tank tmp = iter.next();
			tmp.startTankPanel();
			//tmp.getEnvForOwnPanel(this.listeTank);
			tmp.listeTank = this.listeTank;
		}

		gameThread = new Thread(this);
		gameThread.start();
	}

	public void update() {
		Iterator<Tank> iter = this.listeTank.iterator();
		while (iter.hasNext()) {
			iter.next().update();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		Iterator<Tank> iter = this.listeTank.iterator();
		while (iter.hasNext()) {
			iter.next().draw(g2);
		}
		
		tileM.draw(g2);
		g2.dispose();
	}

}
