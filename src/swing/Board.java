package swing;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board {

    public static final int BOARD_WIDTH = 55;
    public static final int BOARD_HEIGHT = 55;

    public static final int TILE_WIDTH = Window.WIDTH / BOARD_WIDTH;
    public static final int TILE_HEIGHT = Window.HEIGHT / BOARD_HEIGHT;

    public static final int WIN_X = BOARD_WIDTH - 1;
    public static final int WIN_Y = BOARD_HEIGHT - 1;

    private final ArrayList<ArrayList<Tile>> boardTiles;

    private static Board instance;

    public static Board getInstance() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }

    private Board() {
        boardTiles = new ArrayList<>();
        for (int i = 0; i < BOARD_WIDTH; i++) {
            boardTiles.add(new ArrayList<>());
        }
        for (ArrayList<Tile> row : boardTiles) {
            for (int i = 0; i < BOARD_HEIGHT; i++) {
                row.add(null);
            }
        }
    }

    public void generate() {
        for (int i = 0; i < BOARD_WIDTH; i++) setTileAt(i, 0, Tile.TileType.NORMAL, false, false, false, false);
        for (int i = 0; i < BOARD_HEIGHT; i++) setTileAt(0, i, Tile.TileType.NORMAL, false, false, false, false);
        for (int i = 1; i < BOARD_WIDTH; i++) {
            for (int j = 1; j < BOARD_HEIGHT; j++) {
                int placement = (int) (Math.random() * 2);
                setTileAt(i, j,
                        (i == WIN_X && j == WIN_Y) ? Tile.TileType.REWARD : Tile.TileType.NORMAL,
                        placement == 0, false, placement == 1, false);
            }
        }
    }

    public void draw(JPanel toAddOn) {
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_HEIGHT; j++) {
                getTileAt(i, j).setLocation(i * TILE_WIDTH, j * TILE_HEIGHT);
                toAddOn.add(getTileAt(i, j));
            }
        }
    }

    public void putCharacterOn(int width, int height) {
        getTileAt(width, height).setBackground(Color.BLUE);
    }

    public void removeFrom(int width, int height) {
        getTileAt(width, height).configureBackground();
    }

    public Tile getTileAt(int width, int height) {
        return boardTiles.get(width).get(height);
    }

    private void setTileAt(int width, int height, Tile.TileType tileType, boolean wallLeft, boolean wallRight,
                           boolean wallUp, boolean wallDown) {
        boardTiles.get(width).set(height, new Tile(TILE_WIDTH * width, TILE_HEIGHT * height, TILE_WIDTH, TILE_HEIGHT,
                tileType, wallLeft, wallRight, wallUp, wallDown));
    }
}
