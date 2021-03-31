package Shared;

public abstract class AbstractPlayer {

    private char c;

    public abstract int doMove(AbstractBoard board);

    public void setCharacter(char c) {
        this.c = c;
    }

    public char getCharacter() {
        return c;
    }
}
