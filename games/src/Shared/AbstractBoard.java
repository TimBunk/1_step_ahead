package Shared;

public abstract class AbstractBoard {

    protected char[] board = null;
    protected char[] oldBoard = null;

    /**
     * @param size The desired size of the board. This should usually be a square.
     */
    public void initializeBoard(int size) {
        board = new char[size];
        oldBoard = new char[size];
    }

    /**
     * @return The length of the board, i.e. how many places there are on the board
     */
    public int length() {
        return board.length;
    }

    /**
     * @param c The character to be counted
     * @return How many of those characters there are on the board
     */
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

    /**
     * @return Another instance of the board
     */
    public abstract AbstractBoard clone();

    /**
     * @param move The move whose validity should be checked
     * @return Whether the move is valid
     */
    public abstract boolean isMoveValid(int move);

    /**
     * @param move The position of the move whose validity should be checked
     * @param c The character for which we want to check the move's validity
     * @return Whether the move is allowed by the rules of Othello
     */
    public abstract boolean isMoveValid(int move, char c);

    /**
     * @param c The character we want to check
     * @return Whether the player using that character has won
     */
    public abstract boolean doesCharacterWin(char c);

    /**
     * @return Whether the game has ended
     */
    public abstract boolean isGameOver();

    /**
     * Prints the state of the board to the screen
     */
    public abstract void printBoard();

    /**
     * @param move Where the move is to be placed
     * @param c The character to be placed
     */
    public abstract void placeMove(int move, char c);

    /**
     * @param c The character for which valid moves should be found
     * @return An array of the positions of the valid moves
     */
    public abstract int[] findValidMoves(char c);
}
