package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import entity.Tank;

public class TankPanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	// SCREEN SETTINGS
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 2;
	public final int tileSize = originalTileSize * scale; // 32x32 tile
	public final int maxScreenCol = 13;
	public final int maxScreenRow = 13;
	public final int screenWidth = tileSize * maxScreenCol; // pixels
	public final int screenHeight = tileSize * maxScreenRow; // pixels
	final int FPS = 60;

	Thread gameThread;
	Tank tank;

	public TankPanel(Tank tank) {
		this.tank = tank;
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
		gameThread = new Thread(this);
		gameThread.start();

	}

	public void update() {
		tank.updateOnOwnPanel();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		tank.drawOnOwnPanel(g2);
		this.draw(g2);
		g2.dispose();
	}
	
	public void draw(Graphics2D g2) {
		int mapTileNum[][] = new int[tank.gp.maxScreenCol][tank.gp.maxScreenRow];
		mapTileNum = tank.gp.tileM.mapTileNum;
		
		int worldCol = 0;
		int worldRow = 0;
		int xx = this.screenWidth/2 - (this.tileSize/2);
		int yy = this.screenHeight/2 - (this.tileSize/2);

		while (worldCol < tank.gp.maxScreenCol && worldRow < tank.gp.maxScreenRow) {
			int tileNum = mapTileNum[worldCol][worldRow];

			int worldX = worldCol * this.tileSize;
			int worldY = worldRow * this.tileSize;

			int screenX = worldX - this.tank.x + xx;
			int screenY = worldY - this.tank.y + yy;

			if (worldX + this.tileSize > this.tank.x - xx && 
				worldX - this.tileSize < this.tank.x + xx && 
				worldY + this.tileSize > this.tank.y - yy && 
				worldY - this.tileSize < this.tank.y + yy) {
				g2.drawImage(tank.gp.tileM.tile[tileNum].image, screenX, screenY, this.tileSize, this.tileSize, null);
			}
			//g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			worldCol++;

			if (worldCol == tank.gp.maxScreenCol) {
				worldCol = 0;

				worldRow++;

			}
		}
		
	}
	
	public void screenShot() {
		// Take a screenshot of the panel
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        this.paint(g2d);
        g2d.dispose();

        // Save the screenshot to a file or perform other actions
        this.saveScreenshot(image, this.tank.name+".png");
		
	}
	
	private void saveScreenshot(BufferedImage image, String fileName) {
        try {
            ImageIO.write(image, "png", new File(fileName));
            System.out.println("Screenshot saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
