import javax.swing.JOptionPane;

import Minesweeper.Game;

public class Main {

	public static void main(String[] args) 
	{
		int toughness = 1, mines = 0, size = 0;
		
		String choice;
		String text = "Choisissez le mode de difficulté : \n";
		text += "1 : Mode facile \n";
		text += "2 : Mode intermediaire \n";
		text += "3 : Mode difficile";
		
		do
		{
			choice = JOptionPane.showInputDialog(text);
			toughness = Integer.parseInt(choice);
		}while(toughness!=1 && toughness!=2 && toughness!=3);
		
		
		switch (toughness) {
			case 1:
				mines = 1;
				size = 9;
				break;
			case 2:
				mines = 40;
				size = 16;
				break;
			case 3:
				mines = 99;
				size = 30;
				break;
			default:
            	System.exit(0);
				break;
		}
		
		Game game = new Game(size,mines);
		game.mainWindow(game);
	}
}
