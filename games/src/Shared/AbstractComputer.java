package Shared;

public abstract class AbstractComputer extends AbstractPlayer {

    private int difficulty;

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }

    /**
     * @param board The board the algorithm should consider
     * @param depth How many iterations the algorithm should look
     * @param maximizing Whether the player whose move is considered wants a maximal or minimal result
     * @param alpha Helper argument for pruning
     * @param beta Helper argument for pruning
     * @return The evaluation of the moves used
     */
    protected abstract int minimax(AbstractBoard board, int depth, boolean maximizing, int alpha, int beta);
}
