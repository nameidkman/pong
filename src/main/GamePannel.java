package main;

import javax.swing.*;
import java.awt.*;

public class GamePannel extends JPanel implements Runnable {

    final int originalPixelSize = 16; // Value is 16
    final int increaseTileSize = 3; // Value is 3
    final int tileSize = originalPixelSize * increaseTileSize; // Value is 48 (16 * 3)
    final int maxScreenCol = 16; // Value is 16
    final int maxScreenRow = 12; // Value is 12
    final int screenX = tileSize * maxScreenCol; // Value is 768 (48 * 16)
    final int screenY = tileSize * maxScreenRow; // Value is 576 (48 * 12) 


    Thread thread;
    // spawn points
    int positionX = 0;
    int positionY = screenY/2;
    int positionX2 = 744;
    int positionY2 = screenY/2;
    int ballX  =  (screenX - tileSize) / 2;
    int ballY  =  (screenY - tileSize) / 2;
    int ballSpeedX = (int)(Math.random() * 4 + 1);
    int ballSpeedY = (int)(Math.random() * 4 + 1);
    // speed
    int speed = 4;

    // FPS
    int FPS = 60;
    int p1S = 0;
    int p2S = 0;

    KeyHandler keyH = new KeyHandler();

    public GamePannel(){

        this.setPreferredSize(new Dimension(screenX, screenY));
        // setting the background color to black
        this.setBackground(Color.black);

        this.setDoubleBuffered(true);

        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    public void startGameThread(){
        thread = new Thread(this);
        thread.start();
    }
    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double nextDrawTime = drawInterval + System.nanoTime();

        while(thread != null){
            update();
            repaint();

            try {
                double remainingTime= nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;
                if (remainingTime<0) {
                    remainingTime =0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    public void update() {
        if (keyH.up) {
            positionY -= speed;
            if (positionY < 0) {
                positionY = 0;
            }
        }
        if (keyH.down) {
            positionY += speed;
            if (positionY > screenY - tileSize * 4) {
                positionY = screenY - tileSize * 4;
            }
        }
        if (keyH.up2) {
            positionY2 -= speed;
            if (positionY2 < 0) {
                positionY2 = 0;
            }
        }
        if (keyH.down2) {
            positionY2 += speed;
            if (positionY2 > screenY - tileSize * 4) {
                positionY2 = screenY - tileSize * 4;
            }
        }

        ballX += ballSpeedX;
        ballY += ballSpeedY;

        // Ball collision with top and bottom screen boundaries
        if (ballY <= 0 || ballY >= screenY - tileSize) {
            ballSpeedY = -ballSpeedY;
        }


        if(ballX <= positionX + tileSize /2 && ballY + tileSize >= positionY &&   ballY <= positionY + tileSize*4){
            ballSpeedX = -ballSpeedX;

        }
        if(ballX +  tileSize >= positionX2  && ballY + tileSize >= positionY2 &&   ballY <= positionY2 + tileSize*4){
            ballSpeedX = -ballSpeedX;
        }

        if(ballX <= 0 || ballX >= screenX - tileSize){
            if(ballX <= 0)
                p1S++;
            else p2S++;
            ballX = (screenX - tileSize) / 2;
            ballY = (screenY - tileSize) / 2;

            ballSpeedX = (int)(Math.random() * 4 + 1) * (ballX <= 0 ? 1 : -1);
            ballSpeedY = (int)(Math.random() * 4 + 1);
        }

    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)    g;

        g2.setColor(Color.WHITE);
        g2.fillRect(positionX,positionY,tileSize/ 2,tileSize*4);
        g2.fillRect(positionX2, positionY2, tileSize/2, tileSize*4);
        g2.setColor(Color.RED);
        g2.fillOval(ballX, ballY, tileSize, tileSize);

        g2.setColor(Color.WHITE);
        g2.drawString(String.valueOf(p1S), 100, 100);
        g2.drawString(String.valueOf(p2S), 600, 100);

        if(p1S == 5){
            System.out.println("player 2 won");
            System.exit(0);
        }else if (p2S == 5){
            System.out.println("player 1 won");
            System.exit(0);
        }

    }
}
