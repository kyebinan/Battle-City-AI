package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;


import javax.imageio.ImageIO;

import main.GamePanel;
import main.TankPanel;

public class Asset extends Entity {
	
	public GamePanel gp;
	public TankPanel tp;
	public BufferedImage ad1, ad2, ad3, ad4, ad5, ad6, ad7;
	public BufferedImage image;
	
	public Asset(GamePanel gp) {
		this.solidArea = new Rectangle();
		this.solidArea.x = 4;
		this.solidArea.y = 4;
		this.solidArea.width = 24;
		this.solidArea.height = 24;
		this.x = 512;
		this.y = 512;
	}

	@Override
	public void getImage() {
		try {
			String filePath = "/assets";
			this.ad1 = ImageIO.read(getClass().getResourceAsStream(filePath + "/ad1.png"));
			this.ad2 = ImageIO.read(getClass().getResourceAsStream(filePath + "/ad2.png"));
			this.ad3 = ImageIO.read(getClass().getResourceAsStream(filePath + "/ad3.png"));
			this.ad4 = ImageIO.read(getClass().getResourceAsStream(filePath + "/ad4.png"));
			this.ad5 = ImageIO.read(getClass().getResourceAsStream(filePath + "/ad5.png"));
			this.ad6 = ImageIO.read(getClass().getResourceAsStream(filePath + "/ad6.png"));
			this.ad7 = ImageIO.read(getClass().getResourceAsStream(filePath + "/ad7.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		
	}
	
	public void draw(Graphics2D g2) {
		
	}
	
}
