import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) 
	{
		int toughness = 1, mines = 0, size = 0;
		
		String choice;
		String Texte = "Choisissez le mode de difficulté : \n";
		Texte+= "1 : Mode facile \n";
		Texte+= "2 : Mode intermediaire \n";
		Texte+= "3 : Mode difficile";
		do
		{
			choice = JOptionPane.showInputDialog(Texte);
			toughness = Integer.parseInt(choice);
		}while(toughness!=1 && toughness!=2 && toughness!=3);
		
		
		switch (toughness) {
			case 0:
				mines = 10;
				size = 9;
				break;
			case 1:
				mines = 40;
				size = 16;
				break;
			case 2:
				mines = 99;
				size = 30;
				break;
			default:
            	System.exit(0);
				break;
		}
		
		Game game = new Game(size,mines);
		game.mainWindow(game, size);
	}
}
