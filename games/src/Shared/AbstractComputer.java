package Shared;

public abstract class AbstractComputer extends AbstractPlayer {

    private int difficulty;

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }

    protected abstract int minimax(AbstractBoard board, int depth, boolean maximizing, int alpha, int beta);
}
