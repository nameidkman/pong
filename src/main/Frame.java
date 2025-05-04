/*
 * Name: Sai 
 * Date: today 
 * Des: frame class main purpose is to create the frame 
 *
 * 
 */





package main;

import javax.swing.*;


public class Frame extends JFrame {

    public Frame() {
        // making it so that if you press X it will clsoe the window as well as the program
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // making it so that the user cant resize the window 
        this.setResizable(false);
        this.setTitle("The game");
        
        GamePannel GamePannel = new GamePannel();
        this.add(GamePannel);
        this.pack();
        // makign it so that it will show up in the main window at the center of the screen 
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }


}
