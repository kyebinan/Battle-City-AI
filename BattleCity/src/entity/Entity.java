package entity;

import java.awt.Rectangle;

public abstract class Entity {
	public int x, y;
	public String direction;
	public Rectangle solidArea;
	public boolean collisionOn = false;
	public boolean collision = true;
	
	public abstract void getImage();
}
