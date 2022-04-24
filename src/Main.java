import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) 
	{
		int toughness = 1;
        Object[] options = {"Débutant", "Intermédiaire", "Difficile"};
        toughness = JOptionPane.showOptionDialog(null,
                "Quel est votre niveau de difficulite ?", "Niveau",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        if(toughness == -1)
            System.exit(0);
		Game game = new Game(8);
		game.mainWindow(game, 8);
	}
}
