package Shared;

public abstract class AbstractBoard {

    protected char[] board = null;
    protected char[] oldBoard = null;

    public void initializeBoard(int size) {
        board = new char[size];
        oldBoard = new char[size];
    }

    public int length() {
        return board.length;
    }

    public int count(char c) {
        int counter = 0;
        for (char value : board) {
            if (value == c) {
                counter++;
            }
        }
        return counter;
    }

    public void undoLastMove() {
        System.arraycopy(oldBoard, 0, board, 0, board.length);
    }

    protected void saveBoard() {
        System.arraycopy(board, 0, oldBoard, 0, board.length);
    }

    public abstract AbstractBoard clone();

    public abstract boolean isMoveValid(int move);

    public abstract boolean isMoveValid(int move, char c);

    public abstract boolean doesCharacterWin(char c);

    public abstract boolean isGameOver();

    public abstract void printBoard();

    public abstract void placeMove(int move, char c);

    public abstract int[] findValidMoves(char c);
}
