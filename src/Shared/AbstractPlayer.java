package Shared;

public abstract class AbstractPlayer {

    private char c;

    /**
     * @param board The board on which the move is placed
     * @return The place on the board where we want to do a move
     */
    public abstract int doMove(AbstractBoard board);

    public void setCharacter(char c) {
        this.c = c;
    }

    public char getCharacter() {
        return c;
    }
}
