import javax.swing.JFrame;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// JFrame window = new JFrame();
		
		// window.setTitle("Jeu de demineur");
		
		// window.setSize(400, 300);
		
		// window.setLocationRelativeTo(null);
		
		// window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// window.setVisible(true);
		MainWindow window = new MainWindow(8);
		window.main(window, 9);
	}

	// public static void running(int size) {
    //     System.out.println("Good Going man! You're number " + size);
    //     game.main(size);
    // }

}
