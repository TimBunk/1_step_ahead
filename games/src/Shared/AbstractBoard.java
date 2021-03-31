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
        for (int i = 0; i < board.length; i++) {
            if (board[i] == c) {
                counter++;
            }
        }
        return counter;
    }

    public void undoLastMove() {
        for (int i = 0; i < board.length; i++) {
            board[i] = oldBoard[i];
        }
    }

    protected void saveBoard() {
        for (int i = 0; i < board.length; i++) {
            oldBoard[i] = board[i];
        }
    }

    public abstract AbstractBoard clone();

    public abstract boolean isMoveValid(int move);

    public abstract boolean doesCharacterWin(char c);

    public abstract boolean anyTilesAvailable();

    public abstract void printBoard();

    public abstract void placeMove(int move, char c);

    public abstract int[] findValidMoves(char c);
}
