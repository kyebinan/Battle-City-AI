package main;

public class Main {

	public static void main(String[] args) {
		int numberTank = 3;// by team 
		int initialGrade = 4;// grade max = (0 or S), 1, 2, 3, min = 4 
		GameManager gameManager = new GameManager(numberTank, initialGrade);
		gameManager.runGame();
		
	}

}
