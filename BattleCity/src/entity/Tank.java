package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import main.GamePanel;
import main.TankPanel;

public class Tank extends Entity {
	
	public GamePanel gp;
	public TankPanel tp;
	// TANK PARAMETERS
	public String name;
	public String color;
	public int grade;
	public int life;
	public int fire;
	public int speed;
	public boolean spwan = true;
	public int counterSpwan = 0;

	// DISPLAY PARAMETERS
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public BufferedImage image;
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public ArrayList<Tank> listeTank;
	public ArrayList<Bullet> shootedBullet; 

	// METHODS
	public Tank(GamePanel gp, String name, int grade, int x, int y, String direction) {
		this.solidArea = new Rectangle();
		this.solidArea.x = 4;
		this.solidArea.y = 4;
		this.solidArea.width = 24;
		this.solidArea.height = 24;
		
		this.tp = new TankPanel(this); //TODO create an interface Tankpanel
		this.gp = gp;
		this.name = name;
		this.color =name;
		this.grade = grade;
		this.life = 3;
		this.changeGrade(grade);
		
		this.getImage();
		this.direction = direction;
		this.image = right1;
		this.x = x;
		this.y = y;
		this.speed = 2;
		
		this.shootedBullet = new ArrayList<Bullet>();
		this.fire();
	}
	
	public void changeGrade(int newGrade) {
		switch(newGrade) {
		case 0:
			this.setUpGrade0();
			break;
		case 1:
			this.setUpGrade1();
			break;
		case 2:
			this.setUpGrade2();
			break;
		case 3:
			this.setUpGrade3();
			break;
		case 4:
			this.setUpGrade4();
			break;
		}
		this.getImage();
	}
	
	public void strikeByBullet() {
		if (this.grade < 4) {
			this.changeGrade(this.grade + 1);
		}
		else if (this.grade == 4 && this.life > 0){
			this.life--;
		}
		else {
			//TODO destroy the tank when there aren't any life
		}
	}

	private void setUpGrade0() {
		this.grade = 0;
	}
	
	private void setUpGrade1() {
		this.grade = 1;
	}
	
	private void setUpGrade2() {
		this.grade = 2;
	}
	
	private void setUpGrade3() {
		this.grade = 3;
	}
	
	private void setUpGrade4() {
		this.grade = 4;
	}


	
	public void fire() {
		Bullet bullet = new Bullet(this.gp, this.tp, this, this.direction, this.x, this.y, this.speed+1);
		this.shootedBullet.add(bullet);
	}
		
	public void getImage() {
		try {
			String filePath = "/tank_" + this.color + "_gr" + this.grade;
			this.up1 = ImageIO.read(getClass().getResourceAsStream(filePath + "/UP1.png"));
			this.up2 = ImageIO.read(getClass().getResourceAsStream(filePath + "/UP2.png"));
			this.down1 = ImageIO.read(getClass().getResourceAsStream(filePath + "/DOWN1.png"));
			this.down2 = ImageIO.read(getClass().getResourceAsStream(filePath + "/DOWN2.png"));
			this.left1 = ImageIO.read(getClass().getResourceAsStream(filePath + "/LEFT1.png"));
			this.left2 = ImageIO.read(getClass().getResourceAsStream(filePath + "/LEFT2.png"));
			this.right1 = ImageIO.read(getClass().getResourceAsStream(filePath + "/RIGHT1.png"));
			this.right2 = ImageIO.read(getClass().getResourceAsStream(filePath + "/RIGHT2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		collisionOn = false;
		gp.cChecker.checkTileTank(this);
		gp.cChecker.checkTankTank(this);

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

		spriteCounter++;
		if (spriteCounter > 12) {
			spriteNum = (spriteNum + 1) % 2;
			spriteCounter = 0;
		}
		
		Iterator<Bullet> iter = this.shootedBullet.iterator();
		while (iter.hasNext()) {
			iter.next().update();
		}
		
	}

	public void draw(Graphics2D g2) {
		switch (direction) {
		case "up":
			if (spriteNum == 0) {
				this.image = up1;
			}
			if (spriteNum == 1) {
				this.image = up2;
			}
			break;

		case "down":
			if (spriteNum == 0) {
				this.image = down1;
			}
			if (spriteNum == 1) {
				this.image = down2;
			}
			break;

		case "left":
			if (spriteNum == 0) {
				this.image = left1;
			}
			if (spriteNum == 1) {
				this.image = left2;
			}
			break;

		case "right":
			if (spriteNum == 0) {
				this.image = right1;
			}
			if (spriteNum == 1) {
				this.image = right2;
			}
			break;
		}
	
		g2.drawImage(this.image, this.x, this.y, gp.tileSize, gp.tileSize, null);
		
		Iterator<Bullet> iter = this.shootedBullet.iterator();
		while (iter.hasNext()) {
			iter.next().draw(g2);
		}
		
		iter = this.shootedBullet.iterator();
		while (iter.hasNext()) {
			Bullet tmp = iter.next();
			if (tmp.explosionId == 7) {
				iter.remove();
			}
		}
	}
	
	public void updateOnOwnPanel() {
		
	}
	
	public void drawOnOwnPanel(Graphics2D g2) {
		int xx = this.tp.screenWidth/2 - (this.tp.tileSize/2);
		int yy = this.tp.screenHeight/2 - (this.tp.tileSize/2);
		g2.drawImage(this.image, xx, yy,  this.tp.tileSize,  this.tp.tileSize, null);

		Iterator<Tank> iter = this.listeTank.iterator();
		while (iter.hasNext()) {
			Tank tmp = iter.next();
			if (tmp != this) {
				int xxx = tmp.x - this.x + xx;
				int yyy = tmp.y - this.y + yy;
				g2.drawImage(tmp.image, xxx, yyy,  this.tp.tileSize,  this.tp.tileSize, null);
			}
			
			Iterator<Bullet> iterBullet = tmp.shootedBullet.iterator();
			while (iterBullet.hasNext()) {
				Bullet tmpBullet = iterBullet.next();
				int xxx = tmpBullet.x - this.x + xx;
				int yyy = tmpBullet.y - this.y + yy;
				g2.drawImage(tmpBullet.image, xxx, yyy,  this.tp.tileSize/2,  this.tp.tileSize/2, null);
			}
		}
	}
	
	public void getEnvForOwnPanel(ArrayList<Tank> listeTank) {
		this.listeTank = listeTank;
	}
	
	public void startTankPanel() {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Battle City - " + this.name);

		window.add(this.tp);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		this.tp.startGameThread();
	}
}
