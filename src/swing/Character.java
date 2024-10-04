package swing;

import java.util.ArrayList;

public class Character {

    private enum Move {

        LEFT, RIGHT, UP, DOWN;
    }

    private static final int START_X = 2;
    private static final int START_Y = 2;

    private static final boolean PLAYABLE = false;

    private int boardX;
    private int boardY;

    private boolean canGoLeft;
    private boolean canGoRight;
    private boolean canGoUp;
    private boolean canGoDown;

    private static Character instance;

    public static Character getInstance() {
        if (instance == null) {
            instance = new Character();
        }
        return instance;
    }

    private Character() {
        boardX = START_X / Board.TILE_WIDTH;
        boardY = START_Y / Board.TILE_HEIGHT;
        Board.getInstance().putCharacterOn(0, 0);
    }

    public void update() {
        if (!canGoLeft) {
            if (boardX != 0) {
                canGoLeft =
                        !Board.getInstance().getTileAt(boardX - 1, boardY).hasWallRight() &&
                                !Board.getInstance().getTileAt(boardX, boardY).hasWallLeft() &&
                                !KeyBindings.left;
            }
        }

        if (!canGoRight) {
            if (boardX != Board.BOARD_WIDTH - 1) {
                canGoRight =
                        !Board.getInstance().getTileAt(boardX + 1, boardY).hasWallLeft() &&
                                !Board.getInstance().getTileAt(boardX, boardY).hasWallRight() &&
                                !KeyBindings.right;
            }
        }

        if (!canGoUp) {
            if (boardY != 0) {
                canGoUp =
                        !Board.getInstance().getTileAt(boardX, boardY - 1).hasWallDown() &&
                                !Board.getInstance().getTileAt(boardX, boardY).hasWallUp() &&
                                !KeyBindings.up;
            }
        }

        if (!canGoDown) {
            if (boardY != Board.BOARD_HEIGHT - 1) {
                canGoDown =
                        !Board.getInstance().getTileAt(boardX, boardY + 1).hasWallUp() &&
                                !Board.getInstance().getTileAt(boardX, boardY).hasWallDown() &&
                                !KeyBindings.down;
            }
        }
        if (PLAYABLE) {
            if (canGoLeft && KeyBindings.left) {
                moveOnBoard(-1, 0);
                invalidateMovement();
            }

            if (canGoRight && KeyBindings.right) {
                moveOnBoard(1, 0);
                invalidateMovement();
            }

            if (canGoUp && KeyBindings.up) {
                moveOnBoard(0, -1);
                invalidateMovement();
            }

            if (canGoDown && KeyBindings.down) {
                moveOnBoard(0, 1);
                invalidateMovement();
            }
        } else {
            ArrayList<Move> moveSet = generateMoveSet();
            for (Move move : moveSet) {
                if (move == Move.LEFT) moveOnBoard(-1, 0);
                if (move == Move.RIGHT) moveOnBoard(1, 0);
                if (move == Move.UP) moveOnBoard(0, -1);
                if (move == Move.DOWN) moveOnBoard(0, 1);
                Window.delay(0.2);
            }
        }
    }

    private ArrayList<Move> generateMoveSet() {
        PossibleMoves currentCalculation = new PossibleMoves(boardX, boardY);
        ArrayList<Move> moveSet = new ArrayList<>();
        ArrayList<ArrayList<Move>> failedAttempts = new ArrayList<>();
        Move lastMove = null;
        int lastAmountOfMoves = 0;
        while (!(currentCalculation.getWidth() == Board.WIN_X && currentCalculation.getHeight() == Board.WIN_Y)) {
            boolean moved = false;
            if (currentCalculation.canGoLeft()) {
                boolean happened = false;
                ArrayList<Move> temp = new ArrayList<>(moveSet);
                temp.add(Move.LEFT);
                for (ArrayList<Move> attempt : failedAttempts) {
                    if (!happened) happened = attempt.equals(temp);
                    else break;
                }
                if (!happened) {
                    currentCalculation.translate(-1, 0);
                    moveSet.add(Move.LEFT);
                    moved = true;
                }
            }

            if (currentCalculation.canGoRight() && !moved) {
                boolean happened = false;
                ArrayList<Move> temp = new ArrayList<>(moveSet);
                temp.add(Move.RIGHT);
                for (ArrayList<Move> attempt : failedAttempts) {
                    if (!happened) happened = attempt.equals(temp);
                    else break;
                }
                if (!happened) {
                    currentCalculation.translate(1, 0);
                    moveSet.add(Move.RIGHT);
                    moved = true;
                }
            } else if (currentCalculation.canGoUp() && !moved) {
                boolean happened = false;
                ArrayList<Move> temp = new ArrayList<>(moveSet);
                temp.add(Move.UP);
                for (ArrayList<Move> attempt : failedAttempts) {
                    if (!happened) happened = attempt.equals(temp);
                    else break;
                }
                if (!happened) {
                    currentCalculation.translate(0, -1);
                    moveSet.add(Move.UP);
                    moved = true;
                }
            }

            if (currentCalculation.canGoDown() && !moved) {
                boolean happened = false;
                ArrayList<Move> temp = new ArrayList<>(moveSet);
                temp.add(Move.DOWN);
                for (ArrayList<Move> attempt : failedAttempts) {
                    if (!happened) happened = attempt.equals(temp);
                    else break;
                }
                if (!happened) {
                    currentCalculation.translate(0, 1);
                    moveSet.add(Move.DOWN);
                }
            }

            if (failedAttempts.size() > 1) {
                if (failedAttempts.get(failedAttempts.size() - 1).equals(
                        failedAttempts.get(failedAttempts.size() - 2))) {
                    failedAttempts.remove(failedAttempts.get(failedAttempts.size() - 1));
                    ArrayList<Move> repeatedFailure = failedAttempts.get(failedAttempts.size() - 1);
                    repeatedFailure.remove(repeatedFailure.size() - 1);
                    failedAttempts.add(repeatedFailure);
                }
            }
            if (moveSet.size() > 1) {
                if ((moveSet.get(moveSet.size() - 1) == Move.LEFT && lastMove == Move.RIGHT) ||
                        (moveSet.get(moveSet.size() - 1) == Move.RIGHT && lastMove == Move.LEFT) ||
                        (moveSet.get(moveSet.size() - 1) == Move.UP && lastMove == Move.DOWN) ||
                        (moveSet.get(moveSet.size() - 1) == Move.DOWN && lastMove == Move.UP)) {
                    failedAttempts.add(new ArrayList<>(moveSet));
                    moveSet.clear();
                    lastMove = null;
                    currentCalculation.translate(-currentCalculation.getWidth(), -currentCalculation.getHeight());
                }
            }
            if (lastAmountOfMoves == moveSet.size()) {
                failedAttempts.add(new ArrayList<>(moveSet));
                moveSet.clear();
                lastMove = null;
                currentCalculation.translate(-currentCalculation.getWidth(), -currentCalculation.getHeight());
            }
            if (moveSet.size() != 0) lastMove = moveSet.get(moveSet.size() - 1);
            lastAmountOfMoves = moveSet.size();
        }
        return moveSet;
    }

    private void moveOnBoard(int dx, int dy) {
        Board.getInstance().removeFrom(boardX, boardY);
        boardX += dx;
        boardY += dy;
        Board.getInstance().putCharacterOn(boardX, boardY);
    }

    private void invalidateMovement() {
        canGoLeft = false;
        canGoRight = false;
        canGoUp = false;
        canGoDown = false;
    }
}
