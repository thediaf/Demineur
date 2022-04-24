import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) 
	{
		int toughness = 1, mines = 0, size = 0;
		
        Object[] options = {"Débutant", "Intermédiaire", "Difficile"};
        toughness = JOptionPane.showOptionDialog(null,
						"Quel est votre niveau de difficulite ?", "Niveau",
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						options,
						options[0]
					);
		
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
