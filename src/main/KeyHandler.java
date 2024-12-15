

package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


// key listener is for receving keyboard info
public class KeyHandler implements KeyListener {

    public boolean up, down, up2, down2;

    // we need to add all three of function if we dont do that then it wont work
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // to get the input for the keys
        int code = e.getKeyCode();
        // to see which of the key they have pressed
        if (code == KeyEvent.VK_W){
            up = true;
        } if (code == KeyEvent.VK_S){
            down = true;
        }if(code == KeyEvent.VK_UP){
            up2 = true;
        }if(code == KeyEvent.VK_DOWN){
            down2 = true;
        }
    }

    // to see what key has been released
    @Override
    public void keyReleased(KeyEvent e) {

        // to see which of the key they have pressed
        int code = e.getKeyCode();
        // to see which of the key they have pressed
        if (code == KeyEvent.VK_W){
            up = false;
        } if (code == KeyEvent.VK_S){
            down = false;
        }if(code == KeyEvent.VK_UP){
            up2 = false;
        }if(code == KeyEvent.VK_DOWN){
            down2 = false;
        }
    }
}
