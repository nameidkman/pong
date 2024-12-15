package main;

import javax.swing.*;

public class Frame extends JFrame {
    JFrame frame = new JFrame();;
    public Frame(){

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("The game");

        GamePannel GamePannel = new GamePannel();
        this.add(GamePannel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        GamePannel.startGameThread();
    }
}
