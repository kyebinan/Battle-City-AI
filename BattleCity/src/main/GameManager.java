package main;

import java.util.ArrayList;

import javax.swing.JFrame;

import entity.Tank;
import tile.Tile;

public class GameManager {
	GamePanel gamePanel;
	JFrame window;
	ArrayList<Tank> listeTank = new ArrayList<Tank>();
	
	public GameManager(int numberTank, String map) {
		this.gamePanel = new GamePanel(map);
		this.window = new JFrame();
		this.createTank(numberTank);
	}
	
	
	private void createTank(int numberTank) {
		if (numberTank > 7) {
			numberTank = 7;
		}
		Tank tankYellow, tankPink;
		String yellow = "yellow";
		String pink = "pink";
		int initialGrade = 4; //lowest grade
//		int[][] tankX = {{10, 20, 30, 40, 50, 60, 70},{10, 20, 30, 40, 50, 60, 70}};
//		int[][] tankY = {{10, 20, 30, 40, 50, 60, 70},{10, 20, 30, 40, 50, 60, 70}};
		
		for(int i = 1; i <= numberTank; i++) {
			tankYellow = new Tank(this.gamePanel, yellow, initialGrade, 128, 128, "right");
			this.listeTank.add(tankYellow);
			tankPink  = new Tank(this.gamePanel, pink, initialGrade, 160, 160, "right");
			this.listeTank.add(tankPink);
		}
		gamePanel.listeTank = this.listeTank;
	}
	
	
	public void runGame() {
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.window.setResizable(false);
		this.window.setTitle("Battle City");
		this.window.add(gamePanel);
		this.window.pack();
		this.window.setLocationRelativeTo(null);
		this.window.setVisible(true);
		this.gamePanel.startGameThread();
	}
}
