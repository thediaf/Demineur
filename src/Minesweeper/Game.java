package Minesweeper;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.imageio.ImageIO;

public class Game extends JFrame {

    public Game(int size, int mines) 
    {
        minesCount = mines;
        this.size = size;
        this.setSize(this.size*50, this.size*50 + 50);
        this.setTitle("Demineur");
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    }

    private void setMines(int size) 
    {
        Random rand = new Random();
        
        minedButton = new int[this.size][this.size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                minedButton[i][j] = 0;
            }
        }

        int count = 0;
        int xPoint;
        int yPoint;
        while(count < minesCount) {
            xPoint = rand.nextInt(this.size);
            yPoint = rand.nextInt(this.size);
            if (minedButton[xPoint][yPoint]!=-1) {
                minedButton[xPoint][yPoint]=-1;  // -1 represents bomb
                count++;
            }
        }
        
        
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
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

    public void mainWindow(Game frame) {
        GameEngine gameEngine = new GameEngine(frame);
        MyMouseListener myMouseListener = new MyMouseListener(frame);

        revealed = new boolean[this.size][this.size];
        flagged = new boolean[this.size][this.size];
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                revealed[i][j] = false;
                flagged[i][j] = false;
            }
        }


        this.loadImages();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        recordPanel = new JPanel();
        gamePanel = new JPanel();

        BoxLayout recordBoxLayout = new BoxLayout(recordPanel, BoxLayout.X_AXIS);
        recordPanel.setLayout(recordBoxLayout);
      
        JLabel flagsJLabel = new JLabel();
        flagsJLabel.setIcon(new ImageIcon(this.newMine));
        flagsJLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        flagsJLabel.setHorizontalAlignment(JLabel.LEFT);
        flagsLabel = new JLabel("" + this.minesCount);

        JLabel TimerJLabel = new JLabel();
        timeLabel = new JLabel("0");
        timeLabel.setIcon(new ImageIcon(newTimeIcon));
        timeLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        timeLabel.setHorizontalAlignment(JLabel.RIGHT);
    
        recordPanel.add(flagsJLabel);
        recordPanel.add(flagsLabel);
        recordPanel.add(Box.createRigidArea(new Dimension((this.size-1)*15 - 80,50)));
        recordPanel.add(TimerJLabel);
        recordPanel.add(timeLabel);


        GridLayout gameGridLayout = new GridLayout(this.size, this.size);
        gamePanel.setLayout(gameGridLayout);

        buttons = new JButton[this.size][this.size];

        for (int i=0; i<this.size; i++) {
            for (int j=0; j<this.size ; j++ ) {
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension(12, 12));
                buttons[i][j].setBorder(new LineBorder(Color.BLACK));
                buttons[i][j].setBackground(Color.DARK_GRAY);
                buttons[i][j].setBorderPainted(true);
                buttons[i][j].setName(i + " " + j);
                buttons[i][j].addActionListener(gameEngine);
                buttons[i][j].addMouseListener(myMouseListener);
                // buttons[i][j].setToolTipText("It's " + Integer.toString(i) + ", " + Integer.toString(j));
                gamePanel.add(buttons[i][j]);
            }
        }

        mainPanel.add(gamePanel);
        mainPanel.add(recordPanel);
        frame.setContentPane(mainPanel);
        this.setVisible(true);

        // Algorithms
        setMines(this.size);
        
        timeThread timer = new timeThread(this);
        timer.start();
    }

    public void loadImages() 
    {
        // Images
        try {
            flag = ImageIO.read(getClass().getResource("../images/flag.png"));
            newFlag = flag.getScaledInstance(MAGIC_SIZE, MAGIC_SIZE, java.awt.Image.SCALE_SMOOTH);
            mine = ImageIO.read(getClass().getResource("../images/mine.png"));
            newMine = mine.getScaledInstance(MAGIC_SIZE, MAGIC_SIZE, java.awt.Image.SCALE_SMOOTH);
            timeIcon = ImageIO.read(getClass().getResource("../images/time.png"));
            newTimeIcon = timeIcon.getScaledInstance(MAGIC_SIZE, MAGIC_SIZE, java.awt.Image.SCALE_SMOOTH);
        }
        catch (Exception e){
        }   
    }

    // Increase timer every second
    public void timer() {
        String[] time = this.timeLabel.getText().split(" ");
        int time0 = Integer.parseInt(time[0]);
        ++time0;
        this.timeLabel.setText(Integer.toString(time0) + " s");
    }

    private boolean gameWon() {
        // noOfRevealed + noOfMines must be equal to the total no. of boxes
        return (this.revealedCount) == (Math.pow(this.minedButton.length, 2) - this.minesCount);
    }

    // If a block is right clicked
    public void buttonRightClicked(int x, int y) {
        if(!revealed[x][y]) {
            if (flagged[x][y]) {
                buttons[x][y].setIcon(null);
                flagged[x][y] = false;
                int old = Integer.parseInt(this.flagsLabel.getText());
                ++old;
                this.flagsLabel.setText(""+old);
            }
            else {
                if (Integer.parseInt(this.flagsLabel.getText())>0) {
                    buttons[x][y].setIcon(new ImageIcon(newFlag));
                    flagged[x][y] = true;
                    int old = Integer.parseInt(this.flagsLabel.getText());
                    --old;
                    this.flagsLabel.setText(""+old);
                }
            }
        }
    }

    public void buttonClicked(int x, int y) {

        if(!revealed[x][y]) {
            revealed[x][y] = true;

            switch (minedButton[x][y]) {
                case -1:
                    try {
                        buttons[x][y].setIcon(new ImageIcon(newMine));
                    } catch (Exception e) {
                    
                    }
                    buttons[x][y].setSelected(false);
                    buttons[x][y].setBackground(Color.RED);
                    // JOptionPane.showMessageDialog(this, "Vous avez perdu !", null, JOptionPane.ERROR_MESSAGE);
                    // System.exit(0);
                    defeat = true;
                    replay = true;
                    this.gameOver();
                    break;
                case 0:
                    ++this.revealedCount;
                    buttons[x][y].setSelected(false);
                    buttons[x][y].setBackground(Color.lightGray);
                    if (gameWon()) 
                    {
                        // JOptionPane.showMessageDialog(rootPane,"Felicitations! Vous avez remporte la partie");
                        // System.exit(0);

                        replay = true;
                        this.gameOver();
                    } 

                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            try {
                                buttonClicked(x + i, y + j);
                            }
                            catch (Exception e3) {
                                // Do nothing
                            }
                        }
                        }
                    break;
                default:
                    ++this.revealedCount;
                    buttons[x][y].setText(Integer.toString(minedButton[x][y]));
                    buttons[x][y].setBackground(Color.lightGray);
                    if (gameWon()) 
                    {
                        // JOptionPane.showMessageDialog(rootPane,"Felicitations! Vous avez remporte la partie");
                        // System.exit(0);
                        replay = true;
                        this.gameOver();
                    }
                    break;
            }
        }

    }

    public void gameOver()
    {
        if(replay){
            replay=false;
            Object[] choix = {"Rejouer", "Arreter"};
            String textefin;
            if(defeat){
                textefin="Dommage, c'est perdu";
            }
            else{
                textefin ="Felicitations, c'est gagné";
            }
             
            defeat=false;
            int boutton = JOptionPane.showOptionDialog(this, textefin, "Jeu fini", 
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choix, choix[1]);
                             
            if(boutton == 0){   
                Game game = new Game(this.size, minesCount);
                setVisible(false);
                game.mainWindow(game);
                // Vue nouvellevue = new Vue(new GameBoard(this.tailleX, this.tailleY, this.nbMines));
                // this.platteau=nouvellevue.platteau;
                // this.cases=nouvellevue.cases;
                // update(o,arg);

                // nouvellevue.setVisible(true);
            }
            if(boutton == 1){
                System.exit(0);
            }           
        }
    }

    private int size = 0;
    private int minesCount = 0;
    private int revealedCount = 0;
    private int[][] minedButton;
    private boolean[][] revealed;
    private boolean[][] flagged;

    private boolean defeat = false;
    private boolean replay = false;

    private Image flag;
    private Image newFlag;
    private Image mine;
    private Image newMine;
    private Image timeIcon;
    private Image newTimeIcon;

    private JButton[][] buttons;
    private JPanel recordPanel;
    private JPanel gamePanel;
    private JLabel flagsLabel;
    private JLabel timeLabel;


    public static final int MAGIC_SIZE = 30;
} 

class MyMouseListener implements MouseListener {
    Game parent;

    MyMouseListener(Game parent) {
        this.parent = parent;
    }

    public void mouseExited(MouseEvent arg0){
    }
    public void mouseEntered(MouseEvent arg0){
    }
    public void mousePressed(MouseEvent arg0){
    }
    public void mouseClicked(MouseEvent arg0){
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        if(SwingUtilities.isRightMouseButton(arg0)){
            Object eventSource = arg0.getSource();
            JButton clickedButton = (JButton) eventSource;
            String[] xy = clickedButton.getName().split(" ", 2);
            int x = Integer.parseInt(xy[0]);
            int y = Integer.parseInt(xy[1]);
            parent.buttonRightClicked(x, y);
        }
    }
}

class timeThread implements Runnable {
    private Thread t;
    private Game frame;

    timeThread(Game frame) {
        this.frame = frame;
    }

    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
                frame.timer();
            }
            catch (InterruptedException e) {
                System.exit(0);
            }
        }
    }

    public void start() {
        if (t==null) {
            t = new Thread(this);
            t.start();
        }
    }
}