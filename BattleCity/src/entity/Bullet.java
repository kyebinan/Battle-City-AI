package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.TankPanel;

public class Bullet extends Entity {

	public GamePanel gp;
	public TankPanel tp;
	// BULLET PARAMETERS
	public Tank myTank;
	public int grade;
	public int damage;
	public int speed;
	public String direction;
	public int explosionId = 0;

	// DISPLAY PARAMETERS
	public BufferedImage up, down, left, right, exp1, exp2, exp3, no;
	public BufferedImage image;

	public Bullet(GamePanel gp, TankPanel tp, Tank myTank, String direction, int x, int y, int speed) {
		this.gp = gp;
		this.tp = tp;
		this.myTank = myTank;
		this.direction = direction;
		this.x = x + gp.tileSize / 4;
		this.y = y + gp.tileSize / 4;
		this.speed = speed;
		this.getImage();

		this.solidArea = new Rectangle();
		this.solidArea.x = 4;
		this.solidArea.y = 4;
		this.solidArea.width = 8;
		this.solidArea.height = 8;
	}
	
	 public void finalize() {
		 
	 }

	public void getImage() {
		try {
			String filePath = "/bullet";
			this.up = ImageIO.read(getClass().getResourceAsStream(filePath + "/UP.png"));
			this.down = ImageIO.read(getClass().getResourceAsStream(filePath + "/DOWN.png"));
			this.left = ImageIO.read(getClass().getResourceAsStream(filePath + "/LEFT.png"));
			this.right = ImageIO.read(getClass().getResourceAsStream(filePath + "/RIGHT.png"));
			this.exp1 = ImageIO.read(getClass().getResourceAsStream(filePath + "/blow1.png"));
			this.exp2 = ImageIO.read(getClass().getResourceAsStream(filePath + "/blow2.png"));
			this.exp3 = ImageIO.read(getClass().getResourceAsStream(filePath + "/blow3.png"));
			this.no = ImageIO.read(getClass().getResourceAsStream("/materials/empty.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {

		this.collisionOn = false;
		this.gp.cChecker.checkTileBullet(this);
		this.gp.cChecker.checkBulletBullet(this);
		this.gp.cChecker.checkTankBullet(this.myTank, this);

		if (collisionOn == false) {

			switch (direction) {
			case "up":
				this.y -= speed;
				break;
			case "down":
				this.y += speed;
				break;
			case "left":
				this.x -= speed;
				break;
			case "right":
				this.x += speed;
				break;
			}
		}

	}

	public void draw(Graphics2D g2) {
		// BufferedImage image = null;
		if (collisionOn == false) {
			switch (direction) {
			case "up":
				this.image = up;
				break;
			case "down":
				this.image = down;
				break;
			case "left":
				this.image = left;
				break;
			case "right":
				this.image = right;
				break;
			}
		}
		else {
			switch(this.explosionId) {
			case 0,1:
				this.image = exp1;
				this.explosionId++;
				break;
				
			case 2,3:
				this.image = exp2;
				this.explosionId++;
				break;
				
			case 4,5,6:
				this.image = exp3;
				this.explosionId++;
				break;
				
			case 7:
				this.finalize();
				break;
			}
			
		}
		g2.drawImage(this.image, this.x, this.y, gp.tileSize / 2, gp.tileSize / 2, null);
	}

}
