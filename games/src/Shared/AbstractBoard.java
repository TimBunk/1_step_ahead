package Shared;

public abstract class AbstractBoard {

    private char[] board;

    public abstract boolean isMoveValid(int move);

    public abstract void initializeBoard(int size);

    public abstract boolean doesCharacterWin(char c);

    public abstract boolean anyTilesAvailable();

    public abstract void printBoard();

    public abstract void placeMove(int move);

}
