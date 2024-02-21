package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];

	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[10];
		mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
		getTileImage();
		loadMap("/maps/stage1");
	}

	public void getTileImage() {
		try {
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/materials/empty.png"));

			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/materials/border.png"));
			tile[1].collision = true;
			tile[1].collisionOnBullet = true;

			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/materials/brike.png"));
			tile[2].collision = true;
			tile[2].collisionOnBullet = true;

			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/materials/steel.png"));
			tile[3].collision = true;
			tile[3].collisionOnBullet = true;

			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/materials/water.png"));
			tile[4].collision = true;

			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/materials/bush.png"));


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadMap(String file) {
		try {
			InputStream is = getClass().getResourceAsStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			int worldCol = 0;
			int worldRow = 0;

			while (worldCol < gp.maxScreenCol && worldRow < gp.maxScreenRow) {
				String line = br.readLine();

				while (worldCol < gp.maxScreenCol) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[worldCol]);

					mapTileNum[worldCol][worldRow] = num;
					worldCol++;
				}
				if (worldCol == gp.maxScreenCol) {
					worldCol = 0;
					worldRow++;
				}
			}
			br.close();
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2) {
		int worldCol = 0;
		int worldRow = 0;

		while (worldCol < gp.maxScreenCol && worldRow < gp.maxScreenRow) {
			int tileNum = mapTileNum[worldCol][worldRow];

			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;

			g2.drawImage(tile[tileNum].image, worldX, worldY, gp.tileSize, gp.tileSize, null);
			worldCol++;

			if (worldCol == gp.maxScreenCol) {
				worldCol = 0;

				worldRow++;

			}
		}

	}
}
