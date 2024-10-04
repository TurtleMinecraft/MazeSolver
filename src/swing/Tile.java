package swing;

import javax.swing.*;
import java.awt.*;

public class Tile extends JPanel {

    public enum TileType {

        NORMAL(0), REWARD(1), DEATH(2);

        public final int type;

        TileType(int type) {
            this.type = type;
        }
    }

    private static final int WALL_THICKNESS = 2;

    private final TileType type;

    private final boolean wallLeft;
    private final boolean wallRight;
    private final boolean wallUp;
    private final boolean wallDown;

    public Tile(int x, int y, int width, int height, TileType type, boolean wallLeft, boolean wallRight,
                boolean wallUp, boolean wallDown) {
        setPreferredSize(new Dimension(width, height));
        setLocation(x, y);
        setLayout(null);
        this.type = type;
        this.wallLeft = wallLeft;
        this.wallRight = wallRight;
        this.wallUp = wallUp;
        this.wallDown = wallDown;
        configureBackground();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        if (wallLeft) g2.fill(new Rectangle(0, 0, WALL_THICKNESS, getHeight()));
        if (wallRight) g2.fill(new Rectangle(getWidth(), 0, WALL_THICKNESS, getHeight()));
        if (wallUp) g2.fill(new Rectangle(0, 0, getWidth(), WALL_THICKNESS));
        if (wallDown) g2.fill(new Rectangle(0, getHeight(), getWidth(), WALL_THICKNESS));
    }

    public void configureBackground() {
        if (type == TileType.NORMAL) setBackground(Color.WHITE);
        if (type == TileType.REWARD) setBackground(Color.GREEN);
        if (type == TileType.DEATH) setBackground(Color.RED);
    }

    public boolean hasWallLeft() {
        return wallLeft;
    }

    public boolean hasWallRight() {
        return wallRight;
    }

    public boolean hasWallUp() {
        return wallUp;
    }

    public boolean hasWallDown() {
        return wallDown;
    }
}
