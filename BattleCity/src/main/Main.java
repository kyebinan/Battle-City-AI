package main;

public class Main {

	public static void main(String[] args) {
		int numberTank = 1;
		String map = "/maps/stage1";
		GameManager gameManager = new GameManager(numberTank, map);
		gameManager.runGame();
		
	}

}
