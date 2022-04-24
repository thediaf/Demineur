import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.Random;

public class MainWindow extends JFrame {

    public MainWindow(int size) 
    {
        this.setSize(size*50, size*50 + 50);
        this.setTitle("Demineur");
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    }

    private void setMines(int size) {
        Random rand = new Random();
        
        minedButton = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                minedButton[i][j] = 0;
            }
        }

        int count = 0;
        int xPoint;
        int yPoint;
        while(count<minesCount) {
            xPoint = rand.nextInt(size);
            yPoint = rand.nextInt(size);
            if (minedButton[xPoint][yPoint]!=-1) {
                minedButton[xPoint][yPoint]=-1;  // -1 represents bomb
                count++;
            }
        }
        
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (minedButton[i][j]==-1) {
                        // On remplit les cases adjacentes aux mines avec des chiffres
                        for (int k = -1; k <= 1 ; k++) {
                            for (int l = -1; l <= 1; l++) {
                                try {
                                    if (minedButton[i+k][j+l]!= -1) {
                                        minedButton[i+k][j+l] += 1;
                                    }
                                }
                                catch (Exception e) {
                                    // Do nothing
                                }
                            }
                        }
                }
            }
        }
    }

    public void main(MainWindow frame, int size) {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        recordPanel = new JPanel();
        gamePanel = new JPanel();

        BoxLayout recordBoxLayout = new BoxLayout(recordPanel, BoxLayout.X_AXIS);
        recordPanel.setLayout(recordBoxLayout);
      
        JLabel flagsJLabel = new JLabel(" Mines = ");
        flagsJLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        flagsJLabel.setHorizontalAlignment(JLabel.LEFT);
        flagsLabel = new JLabel("0");

        JLabel TimerJLabel = new JLabel(" Temps :");
        timeLabel = new JLabel("0");
        timeLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        timeLabel.setHorizontalAlignment(JLabel.RIGHT);
    
        recordPanel.add(flagsJLabel);
        recordPanel.add(flagsLabel);
        recordPanel.add(Box.createRigidArea(new Dimension((size-1)*15 - 80,50)));
        recordPanel.add(TimerJLabel);
        recordPanel.add(timeLabel);


        GridLayout gameGridLayout = new GridLayout(size, size);
        gamePanel.setLayout(gameGridLayout);

        buttons = new JButton[size][size];

        for (int i=0; i<size; i++) {
            for (int j=0; j<size ; j++ ) {
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension(12, 12));
                buttons[i][j].setBorder(new LineBorder(Color.BLACK));
                buttons[i][j].setBackground(Color.LIGHT_GRAY);
                buttons[i][j].setBorderPainted(true);
                buttons[i][j].setName(i + " " + j);
                // buttons[i][j].setToolTipText("It's " + Integer.toString(i) + ", " + Integer.toString(j));
                gamePanel.add(buttons[i][j]);
            }
        }

        mainPanel.add(recordPanel);
        mainPanel.add(gamePanel);
        frame.setContentPane(mainPanel);
        this.setVisible(true);

        // Algorithms
        setMines(size);

    }

    private static int minesCount = 0;
    private static int[][] minedButton;
    private static JButton[][] buttons;
    private static JPanel recordPanel;
    private static JPanel gamePanel;
    private static JLabel flagsLabel;
    private static JLabel timeLabel;
} 