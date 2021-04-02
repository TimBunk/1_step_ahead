package Shared;

public abstract class AbstractComputer extends AbstractPlayer {

    private int difficulty;

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }
}
