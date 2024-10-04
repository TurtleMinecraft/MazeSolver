package swing;

import javax.swing.*;
import java.awt.*;

public class Window extends JPanel {

    // pixels
    public static final int WIDTH = 680;
    public static final int HEIGHT = 680;

    public static final double PERIODIC_FRAME = 0.02;

    private static final long SECONDS_TO_NANOSECONDS = 1000000000;

    private static final String TITLE = "Maze";
    private static final boolean IS_DOUBLE_BUFFERED = true;

    private final Board board;
    private final Character character;
    private final KeyBindings keyBindings;

    private static Window instance;

    public static Window getInstance() {
        if (instance == null) {
            instance = new Window(WIDTH, HEIGHT);
        }
        return instance;
    }

    private Window(int width, int height) {
        super(IS_DOUBLE_BUFFERED);
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.WHITE);
        setVisible(true);
        setFocusable(true);
        board = Board.getInstance();
        board.generate();
        character = Character.getInstance();
        keyBindings = new KeyBindings();
        addKeyListener(keyBindings);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        board.draw(this);
    }

    public void run() {
        JFrame jFrame = new JFrame(TITLE);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.add(this);
        jFrame.pack();
        while (true) {
            this.update();
        }
    }

    public static void delay(double seconds) {
        long currentTime = System.nanoTime();
        long targetTime = (long) (System.nanoTime() + seconds * SECONDS_TO_NANOSECONDS);
        while (targetTime >= currentTime) {
            currentTime = System.nanoTime();
        }
    }

    private void update() {
        delay(PERIODIC_FRAME);
        character.update();
        repaint();
    }
}
