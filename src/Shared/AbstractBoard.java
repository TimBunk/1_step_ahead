package Shared;

public abstract class AbstractBoard {

    protected char[] board = null;
    private int turnCount = 1;

    /**
     * @param size The desired size of the board. This should usually be a square.
     */
    public void initializeBoard(int size) {
        board = new char[size];
    }

    /**
     * @return Gives a integer that represent the current turn
     */
    public int getTurnCount() { return turnCount; }

    /**
     * Increases the turnCount by 1
     */
    public void increaseTurnCount() {
        turnCount++;
    }

    /**
     * @return Gives the actual data back that is being used for the board
     */
    public char[] getBoard(){
        return board;
    }

    /**
     * @return The length of the board, i.e. how many places there are on the board
     */
    public int length() {
        return board.length;
    }

    /**
     * @param c The character to be counted
     * @return  How many of those characters there are on the board
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

    /**
     * @return Another instance of the board
     */
    public abstract AbstractBoard clone();

    /**
     * @param move  The move whose validity should be checked
     * @return      Whether the move is valid
     */
    public abstract boolean isMoveValid(int move);

    /**
     * @param move  The position of the move whose validity should be checked
     * @param c     The character for which we want to check the move's validity
     * @return      Whether the move is allowed by the rules
     */
    public boolean isMoveValid(int move, char c) {
        // By default wordt isMoveValid(int move) gebruikt
        return isMoveValid(move);
    }

    /**
     * @param c The character we want to check
     * @return  Whether the player using that character has won
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
     * @param move  Where the move is to be placed
     * @param c     The character to be placed
     */
    public abstract void placeMove(int move, char c);

    /**
     * @param c The character for which valid moves should be found
     * @return  An array of the positions of the valid moves
     */
    public abstract int[] findValidMoves(char c);

    /**
     * @param c The character for which valid moves should be counted
     * @return  The number of valid moves for that character
     */
    public abstract int validMoveCount(char c);

    /**
     * @param c The character for which stable stones should be counted
     * @return  The number of stable stones, i.e. stones that can't be flipped by the opponent
     */
    public int getStableStoneCount(char c) {
        return 0;
    }

    public char getCharAtPosition(int position) {
        return board[position];
    }
}
