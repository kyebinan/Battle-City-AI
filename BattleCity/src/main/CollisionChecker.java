package main;

import java.util.Iterator;

import entity.Bullet;
import entity.Tank;

public class CollisionChecker {
	GamePanel gp;

	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}

	public void checkTileTank(Tank tank) {
		int entityLeftWorldX = tank.x + tank.solidArea.x;
		int entityRightWorldX = tank.x + tank.solidArea.x + tank.solidArea.width;
		int entityTopWorldY = tank.y + tank.solidArea.y;
		int entityBottomWorldY = tank.y + tank.solidArea.y + tank.solidArea.height;

		int entityLeftCol = entityLeftWorldX / gp.tileSize;
		int entityRightCol = entityRightWorldX / gp.tileSize;
		int entityTopRow = entityTopWorldY / gp.tileSize;
		int entityBottomRow = entityBottomWorldY / gp.tileSize;

		int tileNum1, tileNum2;

		switch (tank.direction) {
		case "up":
			entityTopRow = (entityTopWorldY - tank.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				tank.collisionOn = true;
			}
			break;

		case "down":
			entityBottomRow = (entityBottomWorldY + tank.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				tank.collisionOn = true;
			}
			break;

		case "left":
			entityLeftCol = (entityLeftWorldX - tank.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				tank.collisionOn = true;
			}
			break;

		case "right":
			entityRightCol = (entityRightWorldX + tank.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				tank.collisionOn = true;
			}
			break;
		}

	}

	private boolean checkGrade(Bullet bullet, int tile) {
		Tank tank = bullet.myTank;
		if (tile == 4 || tile == 5 || tile == 6 ) {
			if (tank.grade == 0 || tank.grade == 1) {
				return true;
			}
			return false;
		}
		return true;
	}
	
	public void checkTileBullet(Bullet bullet) {
		int entityLeftWorldX = bullet.x + bullet.solidArea.x;
		int entityRightWorldX = bullet.x + bullet.solidArea.x + bullet.solidArea.width;
		int entityTopWorldY = bullet.y + bullet.solidArea.y;
		int entityBottomWorldY = bullet.y + bullet.solidArea.y + bullet.solidArea.height;

		int entityLeftCol = entityLeftWorldX / gp.tileSize;
		int entityRightCol = entityRightWorldX / gp.tileSize;
		int entityTopRow = entityTopWorldY / gp.tileSize;
		int entityBottomRow = entityBottomWorldY / gp.tileSize;

		int tileNum1, tileNum2;

		switch (bullet.direction) {
		case "up":
			entityTopRow = (entityTopWorldY - bullet.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			if (bullet.collisionOn == false) {
				if (gp.tileM.tile[tileNum1].collisionOnBullet == true) {
					bullet.collisionOn = true;
					if (this.checkGrade(bullet, tileNum1) == true) {
						this.degradeWall(tileNum1, entityLeftCol, entityTopRow);
					}
				}
				else if ( gp.tileM.tile[tileNum2].collisionOnBullet == true) {
					bullet.collisionOn = true;
					if (this.checkGrade(bullet, tileNum2) == true) {
						this.degradeWall(tileNum2, entityRightCol, entityBottomRow);
					}
				}
			}
			break;

		case "down":
			entityBottomRow = (entityBottomWorldY + bullet.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if (bullet.collisionOn == false) {
				if (gp.tileM.tile[tileNum1].collisionOnBullet == true) {
					bullet.collisionOn = true;
					if (this.checkGrade(bullet, tileNum1) == true) {
						this.degradeWall(tileNum1, entityLeftCol, entityBottomRow);
					}
				}
				else if (gp.tileM.tile[tileNum2].collisionOnBullet == true) {
					bullet.collisionOn = true;
					if (this.checkGrade(bullet, tileNum2) == true) {
						this.degradeWall(tileNum2, entityRightCol, entityBottomRow);
					}
				}
			}
			break;

		case "left":
			entityLeftCol = (entityLeftWorldX - bullet.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			if (bullet.collisionOn == false) {
				if (gp.tileM.tile[tileNum1].collisionOnBullet == true) {
					bullet.collisionOn = true;
					if (this.checkGrade(bullet, tileNum1) == true) {
						this.degradeWall(tileNum1, entityLeftCol, entityTopRow);
					}
				}
				else if ( gp.tileM.tile[tileNum2].collisionOnBullet == true) {
					bullet.collisionOn = true;
					if (this.checkGrade(bullet, tileNum2) == true) {
						this.degradeWall(tileNum2, entityLeftCol,  entityBottomRow);
					}
				}
			}
			break;

		case "right":
			entityRightCol = (entityRightWorldX + bullet.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if (bullet.collisionOn == false) {
				if (gp.tileM.tile[tileNum1].collisionOnBullet == true) {
					bullet.collisionOn = true;
					if (this.checkGrade(bullet, tileNum1) == true) {
						this.degradeWall(tileNum1, entityRightCol, entityTopRow);
					}
				}
				else if (gp.tileM.tile[tileNum2].collisionOnBullet == true) {
					bullet.collisionOn = true;
					if (this.checkGrade(bullet, tileNum2) == true) {
						this.degradeWall(tileNum2, entityRightCol, entityBottomRow);
					}
				}
			}
			break;
		}

	}
	
	private void degradeWall(int tileNum, int i, int j) {
		if (tileNum == 1 || tileNum == 2 || tileNum == 3 || tileNum == 5 || tileNum == 6) {
			gp.tileM.mapTileNum[i][j] = tileNum - 1;
		}
		else if (tileNum == 4 ) {
			gp.tileM.mapTileNum[i][j] = 0;	
		}
		
	}

	public void checkTankTank(Tank tank) {
		int entityLeftWorldX = tank.x + tank.solidArea.x;
		int entityRightWorldX = tank.x + tank.solidArea.x + tank.solidArea.width;
		int entityTopWorldY = tank.y + tank.solidArea.y;
		int entityBottomWorldY = tank.y + tank.solidArea.y + tank.solidArea.height;

		Iterator<Tank> iter = this.gp.listeTank.iterator();
		while (iter.hasNext()) {
			Tank tmp = iter.next();
			if (tmp != tank) {
				int tmpEntityLeftWorldX = tmp.x + tmp.solidArea.x;
				int tmpEntityRightWorldX = tmp.x + tmp.solidArea.x + tmp.solidArea.width;
				int tmpEntityTopWorldY = tmp.y + tmp.solidArea.y;
				int tmpEntityBottomWorldY = tmp.y + tmp.solidArea.y + tmp.solidArea.height;

				switch (tank.direction) {
				case "up":
					if (entityTopWorldY - tank.speed == tmpEntityBottomWorldY) {
						if ((entityLeftWorldX <= tmpEntityLeftWorldX && entityRightWorldX >= tmpEntityLeftWorldX)
								|| (entityLeftWorldX <= tmpEntityRightWorldX
										&& entityRightWorldX >= tmpEntityRightWorldX)) {
							tank.collisionOn = true;
							tmp.collisionOn = true;
						}
					}
					break;

				case "down":
					if (entityBottomWorldY + tank.speed == tmpEntityTopWorldY) {
						if ((entityLeftWorldX <= tmpEntityLeftWorldX && entityRightWorldX >= tmpEntityLeftWorldX)
								|| (entityLeftWorldX <= tmpEntityRightWorldX
										&& entityRightWorldX >= tmpEntityRightWorldX)) {
							tank.collisionOn = true;
							tmp.collisionOn = true;
						}
					}
					break;

				case "left":
					if (entityLeftWorldX - tank.speed == tmpEntityRightWorldX) {
						if ((entityTopWorldY <= tmpEntityTopWorldY && entityBottomWorldY >= tmpEntityTopWorldY)
								|| (entityTopWorldY <= tmpEntityBottomWorldY
										&& entityBottomWorldY >= tmpEntityBottomWorldY)) {
							tank.collisionOn = true;
							tmp.collisionOn = true;
						}
					}
					break;

				case "right":
					if (entityRightWorldX + tank.speed == tmpEntityLeftWorldX) {
						if ((entityTopWorldY <= tmpEntityTopWorldY && entityBottomWorldY >= tmpEntityTopWorldY)
								|| (entityTopWorldY <= tmpEntityBottomWorldY
										&& entityBottomWorldY >= tmpEntityBottomWorldY)) {
							tank.collisionOn = true;
							tmp.collisionOn = true;
						}
					}
					break;
				}
			}
		}
	}

	public void checkBulletBullet(Bullet bullet) {
		int entityLeftWorldX = bullet.x + bullet.solidArea.x;
		int entityRightWorldX = bullet.x + bullet.solidArea.x + bullet.solidArea.width;
		int entityTopWorldY = bullet.y + bullet.solidArea.y;
		int entityBottomWorldY = bullet.y + bullet.solidArea.y + bullet.solidArea.height;

		Iterator<Tank> iter = this.gp.listeTank.iterator();
		while (iter.hasNext()) {
			Tank tmp = iter.next();

			Iterator<Bullet> iterBullet = tmp.shootedBullet.iterator();
			while (iterBullet.hasNext()) {
				Bullet tmpBullet = iterBullet.next();
				int tmpEntityLeftWorldX = tmpBullet.x + tmpBullet.solidArea.x;
				int tmpEntityRightWorldX = tmpBullet.x + tmpBullet.solidArea.x + tmpBullet.solidArea.width;
				int tmpEntityTopWorldY = tmpBullet.y + tmpBullet.solidArea.y;
				int tmpEntityBottomWorldY = tmpBullet.y + tmpBullet.solidArea.y + tmpBullet.solidArea.height;

				if (bullet != tmpBullet) {
					switch (bullet.direction) {
					case "up":
						if (entityTopWorldY - bullet.speed >= tmpEntityBottomWorldY 
								&& entityTopWorldY - bullet.speed <= tmpEntityTopWorldY) {
							if ((entityLeftWorldX <= tmpEntityLeftWorldX && entityRightWorldX >= tmpEntityLeftWorldX)
									|| (entityLeftWorldX <= tmpEntityRightWorldX
											&& entityRightWorldX >= tmpEntityRightWorldX)) {
								bullet.collisionOn = true;
								tmpBullet.collisionOn = true;
							}
						}
						break;

					case "down":
						if (entityBottomWorldY + bullet.speed >= tmpEntityTopWorldY
								&& entityBottomWorldY + bullet.speed <= tmpEntityBottomWorldY) {
							if ((entityLeftWorldX <= tmpEntityLeftWorldX && entityRightWorldX >= tmpEntityLeftWorldX)
									|| (entityLeftWorldX <= tmpEntityRightWorldX
											&& entityRightWorldX >= tmpEntityRightWorldX)) {
								bullet.collisionOn = true;
								tmpBullet.collisionOn = true;
							}
						}
						break;

					case "left":
						if (entityLeftWorldX - bullet.speed <= tmpEntityRightWorldX 
								&& entityLeftWorldX - bullet.speed >= tmpEntityLeftWorldX) {
							if ((entityTopWorldY <= tmpEntityTopWorldY && entityBottomWorldY >= tmpEntityTopWorldY)
									|| (entityTopWorldY <= tmpEntityBottomWorldY
											&& entityBottomWorldY >= tmpEntityBottomWorldY)) {
								bullet.collisionOn = true;
								tmpBullet.collisionOn = true;
							}
						}
						break;

					case "right":
						if (entityRightWorldX + bullet.speed >= tmpEntityLeftWorldX
								&& entityLeftWorldX - bullet.speed <= tmpEntityRightWorldX) {
							if ((entityTopWorldY <= tmpEntityTopWorldY && entityBottomWorldY >= tmpEntityTopWorldY)
									|| (entityTopWorldY <= tmpEntityBottomWorldY
											&& entityBottomWorldY >= tmpEntityBottomWorldY)) {
								bullet.collisionOn = true;
								tmpBullet.collisionOn = true;
							}
						}
						break;
					}
				}
			}
		}
	}

	public void checkTankBullet(Tank tank, Bullet bullet) {

		int entityLeftWorldX = bullet.x + bullet.solidArea.x;
		int entityRightWorldX = bullet.x + bullet.solidArea.x + bullet.solidArea.width;
		int entityTopWorldY = bullet.y + bullet.solidArea.y;
		int entityBottomWorldY = bullet.y + bullet.solidArea.y + bullet.solidArea.height;

		Iterator<Tank> iter = this.gp.listeTank.iterator();
		while (iter.hasNext()) {
			Tank tmp = iter.next();
			if (tmp != tank) {
				int tmpEntityLeftWorldX = tmp.x + tmp.solidArea.x;
				int tmpEntityRightWorldX = tmp.x + tmp.solidArea.x + tmp.solidArea.width;
				int tmpEntityTopWorldY = tmp.y + tmp.solidArea.y;
				int tmpEntityBottomWorldY = tmp.y + tmp.solidArea.y + tmp.solidArea.height;

				switch (bullet.direction) {
				case "up":
					if (entityTopWorldY - bullet.speed <= tmpEntityBottomWorldY 
						&& entityTopWorldY - bullet.speed >= tmpEntityTopWorldY) {
						if ((entityLeftWorldX <= tmpEntityLeftWorldX && entityRightWorldX >= tmpEntityLeftWorldX)
							|| (entityLeftWorldX <= tmpEntityRightWorldX && entityRightWorldX >= tmpEntityRightWorldX)
							|| (entityLeftWorldX >= tmpEntityLeftWorldX && entityRightWorldX <= tmpEntityRightWorldX)) {
							bullet.collisionOn = true;
							tmp.strikeByBullet();
						}
					}
					break;

				case "down":
					if (entityBottomWorldY + bullet.speed >= tmpEntityTopWorldY
						&& entityBottomWorldY + bullet.speed <= tmpEntityBottomWorldY) {
						if ((entityLeftWorldX <= tmpEntityLeftWorldX && entityRightWorldX >= tmpEntityLeftWorldX)
						|| (entityLeftWorldX <= tmpEntityRightWorldX && entityRightWorldX >= tmpEntityRightWorldX)
						|| (entityLeftWorldX >= tmpEntityLeftWorldX && entityRightWorldX <= tmpEntityRightWorldX)) {
							bullet.collisionOn = true;
							tmp.strikeByBullet();
						}
					}
					break;

				case "left":
					if (entityLeftWorldX - bullet.speed <= tmpEntityRightWorldX 
						&& entityLeftWorldX - bullet.speed >= tmpEntityLeftWorldX) {
						if ((entityTopWorldY <= tmpEntityTopWorldY && entityBottomWorldY >= tmpEntityTopWorldY)
						|| (entityTopWorldY <= tmpEntityBottomWorldY && entityBottomWorldY >= tmpEntityBottomWorldY)
						|| (entityTopWorldY >= tmpEntityTopWorldY && entityBottomWorldY <= tmpEntityBottomWorldY)) {
							bullet.collisionOn = true;
							tmp.strikeByBullet();
						}
					}
					break;

				case "right":
					if (entityRightWorldX + bullet.speed >= tmpEntityLeftWorldX
						&& entityLeftWorldX - bullet.speed <= tmpEntityRightWorldX) {
						if ((entityTopWorldY <= tmpEntityTopWorldY && entityBottomWorldY >= tmpEntityTopWorldY)
						|| (entityTopWorldY <= tmpEntityBottomWorldY && entityBottomWorldY >= tmpEntityBottomWorldY)
						|| (entityTopWorldY >= tmpEntityTopWorldY && entityBottomWorldY <= tmpEntityBottomWorldY)) {
							bullet.collisionOn = true;
							tmp.strikeByBullet();
						}
					}
					break;
				}
			}
		}
	}

}
