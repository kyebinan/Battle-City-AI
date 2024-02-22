package main;

import java.util.ArrayList;

import javax.swing.JFrame;

import entity.Tank;

public class GameManager {
	GamePanel gamePanel;
	JFrame window;
	ArrayList<Tank> listeTank = new ArrayList<Tank>();
	
	public GameManager(int nbTank, int initGrade) {
		this.gamePanel = new GamePanel();
		this.window = new JFrame();
		
		Tank tank = new Tank(gamePanel,"Pink-1", "pink", initGrade, 32, 256, "up");
		this.listeTank.add(tank);
		
		tank = new Tank(gamePanel,"Pink-2", "pink", initGrade, 1100, 32, "left");
		this.listeTank.add(tank);
		
		tank = new Tank(gamePanel,"Pink-3", "pink", initGrade, 504, 32,"right");
		this.listeTank.add(tank);
		
		tank = new Tank(gamePanel,"Pink-4", "pink", initGrade, 32, 512,"down");
		this.listeTank.add(tank);
		
		for(int i = 0; i < nbTank; i++) {
			Tank tank1 = new Tank(gamePanel,"Yellow-"+i, "yellow", initGrade, 64, 128+32*i, "right");
			this.listeTank.add(tank1);
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
