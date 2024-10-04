package swing;

public class PossibleMoves {

    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    private int width;
    private int height;

    public PossibleMoves(int width, int height) {
        this.width = width;
        this.height = height;
        calculatePossibilities();
    }

    public void translate(int dx, int dy) {
        width += dx;
        height += dy;
        calculatePossibilities();
    }

    private void calculatePossibilities() {
        if (width == 0) left = false;
        else {
            left = !Board.getInstance().getTileAt(width, height).hasWallLeft() &&
                    !Board.getInstance().getTileAt(width - 1, height).hasWallRight();
        }

        if (width == Board.BOARD_WIDTH - 1) right = false;
        else {
            right = !Board.getInstance().getTileAt(width, height).hasWallRight() &&
                    !Board.getInstance().getTileAt(width + 1, height).hasWallLeft();
        }

        if (height == 0) up = false;
        else {
            up = !Board.getInstance().getTileAt(width, height).hasWallUp() &&
                    !Board.getInstance().getTileAt(width, height - 1).hasWallDown();
        }

        if (height == Board.BOARD_HEIGHT - 1) down = false;
        else {
            down = !Board.getInstance().getTileAt(width, height).hasWallDown() &&
                    !Board.getInstance().getTileAt(width, height + 1).hasWallUp();
        }
    }

    public boolean canGoLeft() {
        return left;
    }

    public boolean canGoRight() {
        return right;
    }

    public boolean canGoUp() {
        return up;
    }

    public boolean canGoDown() {
        return down;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
