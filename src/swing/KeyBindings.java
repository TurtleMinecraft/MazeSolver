package swing;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBindings implements KeyListener {

    public static boolean left;
    public static boolean right;
    public static boolean up;
    public static boolean down;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int pressed = e.getKeyCode();
        if (pressed == KeyEvent.VK_LEFT) left = true;
        if (pressed == KeyEvent.VK_RIGHT) right = true;
        if (pressed == KeyEvent.VK_UP) up = true;
        if (pressed == KeyEvent.VK_DOWN) down = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int released = e.getKeyCode();
        if (released == KeyEvent.VK_LEFT) left = false;
        if (released == KeyEvent.VK_RIGHT) right = false;
        if (released == KeyEvent.VK_UP) up = false;
        if (released == KeyEvent.VK_DOWN) down = false;
    }
}
