/*
 * Name: Sai 
 * Date: today 
 * Des: Gamepannel class where all the logic is done and soem other things as well 
 *
 */






package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePannel extends JPanel implements Runnable {
    


    // setting up the basic setting for the screen
    final int originalPixelSize = 16;
    final int increaseTileSize = 3;
    final int tileSize = originalPixelSize * increaseTileSize;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenX = tileSize * maxScreenCol;
    final int screenY = tileSize * maxScreenRow;

    Thread thread;
    boolean gameStarted = false;

    // Game objects
    int positionX = 0;
    int positionY = screenY / 2;
    int positionX2 = screenX - tileSize / 2;
    int positionY2 = screenY / 2;
    int ballX = (screenX - tileSize) / 2;
    int ballY = (screenY - tileSize) / 2;
    int ballSpeedX = (int) (Math.random() * 4 + 2);
    int ballSpeedY = (int) (Math.random() * 4 + 2);
    int speed = 4;
    int FPS = 60;
    int p1S = 0;
    int p2S = 0;

    boolean gameOver = false;
    long winTime = 0;
    final int GAME_OVER_DELAY = 3000; // milliseconds

    KeyHandler keyH = new KeyHandler();

    Rectangle paddle1 = new Rectangle();
    Rectangle paddle2 = new Rectangle();
    Rectangle ball = new Rectangle();

    // Buttons
    Rectangle button1 = new Rectangle(300, 200, 150, 50);
    Rectangle button2 = new Rectangle(300, 270, 150, 50);

    public GamePannel() {
        // setting up the size for the window 
        this.setPreferredSize(new Dimension(screenX, screenY));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        // makign it so that it will check for what the mouse is doing
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // if the mouse Press any of the button it will do the regrading thing to this
                if (!gameStarted) {
                    Point p = e.getPoint();
                    if (button1.contains(p)) {
                        gameStarted = true;
                        startGameThread();
                    } else if (button2.contains(p)) {
                        System.exit(0);
                    }
                }
            }
        });
    }
    // for starting th game like when you are going to have to start the game 
    public void startGameThread() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        // the basic game loop to make it so that the max fps which can be is 60
        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = drawInterval + System.nanoTime();

        while (thread != null) {
            if (gameStarted) {
                update();
                if (gameOver) {
                    if (System.currentTimeMillis() - winTime >= GAME_OVER_DELAY) {
                        // Reset game
                        p1S = 0;
                        p2S = 0;
                        ballX = (screenX - tileSize) / 2;
                        ballY = (screenY - tileSize) / 2;
                        ballSpeedX = (int) (Math.random() * 4 + 2);
                        ballSpeedY = (int) (Math.random() * 4 + 2);
                        positionY = screenY / 2;
                        positionY2 = screenY / 2;
                        gameOver = false;
                        gameStarted = false;
                    }
                }
            }

            repaint();

            try {
                // logic for the clock
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;
                if (remainingTime < 0) remainingTime = 0;
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    // the update method for the runnable 
    public void update() {
        if (gameOver) return;

        // the logic for what is going to have for each of the keys
        if (keyH.up) positionY = Math.max(0, positionY - speed);
        if (keyH.down) positionY = Math.min(screenY - tileSize * 4, positionY + speed);
        if (keyH.up2) positionY2 = Math.max(0, positionY2 - speed);
        if (keyH.down2) positionY2 = Math.min(screenY - tileSize * 4, positionY2 + speed);
        if (keyH.exit) gameStarted = false;

        ballX += ballSpeedX;
        ballY += ballSpeedY;
        // setting up the place for the paddles
        paddle1.setBounds(positionX, positionY, tileSize / 2, tileSize * 4);
        paddle2.setBounds(positionX2, positionY2, tileSize / 2, tileSize * 4);
        
        // making it so that ball will span in the center every single time
        ball.setBounds(ballX, ballY, tileSize, tileSize);
        

        // the collision code for the top and bottom
        if (ballY <= 0 || ballY >= screenY - tileSize) {
            ballSpeedY = -ballSpeedY;
        }
        // code for what is going to happen when collision ahppen with the paddles 
        if (ball.intersects(paddle1) || ball.intersects(paddle2)) {
            ballSpeedX = -ballSpeedX;
        }
        

        // logic for what is going to happen if it hits one of the sides 
        // menaing that the other player got a point
        if (ballX <= 0 || ballX >= screenX - tileSize) {
            if (ballX <= 0) p2S++;
            else p1S++;
            // spanning the ball in the center of the creen
            ballX = (screenX - tileSize) / 2;
            ballY = (screenY - tileSize) / 2;

            // with random x and y speeds
            ballSpeedX = (int) (Math.random() * 4 + 2) * (ballX <= 0 ? 1 : -1);
            ballSpeedY = (int) (Math.random() * 4 + 2);
        }
        // what is going to happen if one of them won the gmae
        if (p1S == 5 || p2S == 5) {
            if (!gameOver) {
                gameOver = true;
                winTime = System.currentTimeMillis();
            }
        }
    }
    // paint method
    public void paintComponent(Graphics g) {

        // using Graphics2d for the painting
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // check what is going too hapeen if the game hasnt started 
        if (!gameStarted) {

            // goign to have two button which are start and exit 
            // they are pretty self explantory 
            g2.setColor(Color.WHITE);
            g2.fill(button1);
            g2.fill(button2);

            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 20));
            g2.drawString("Start Game", button1.x + 20, button1.y + 30);
            g2.drawString("   Exit  ", button2.x + 30, button2.y + 30);
            return;
        }
        
        // code for whne its actually runnning the game
        //
        // drawing the two paddles
        g2.setColor(Color.WHITE);
        g2.fill(paddle1);
        g2.fill(paddle2);
        
        // drawing the ball
        g2.setColor(Color.RED);
        g2.fillOval(ball.x, ball.y, ball.width, ball.height);
        

        // the score for the game
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        g2.drawString("P1: " + p1S, 100, 100);
        g2.drawString("P2: " + p2S, 600, 100);

        // what is going to happen in case of one person got 5 points
        if (gameOver) {

            // make the screen black 
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, screenX, screenY);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 24));
            // and then put who won the game on the center of the screen
            g2.drawString((p1S == 5 ? "Player 1 Wins!" : "Player 2 Wins!"), screenX / 2 - 100, screenY / 2);
        }
    }
}
