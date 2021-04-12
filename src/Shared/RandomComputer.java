package Shared;

import java.util.concurrent.ThreadLocalRandom;

public class RandomComputer extends AbstractPlayer {

    /**
     * @param board The board on which a move is placed
     * @return The place on the board where we want to do a move
     */
    @Override
    public int doMove(AbstractBoard board) {
        int[] validMoves = board.findValidMoves(getCharacter());
        return validMoves[ThreadLocalRandom.current().nextInt(0, validMoves.length)];
    }
}
