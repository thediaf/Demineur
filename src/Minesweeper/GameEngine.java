package Minesweeper;

import javax.swing.*;
import java.awt.event.*;

class GameEngine implements ActionListener {
    Game game;

    GameEngine(Game game) {
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object eventSource = e.getSource();
        JButton clickedButton = (JButton) eventSource;
    
        String[] xy = clickedButton.getName().split(" ", 2);
        int x = Integer.parseInt(xy[0]);
        int y = Integer.parseInt(xy[1]);
        game.buttonClicked(x, y);

    }
}
