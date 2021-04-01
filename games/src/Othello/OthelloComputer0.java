package Othello;

import Shared.AbstractBoard;
import Shared.AbstractComputer;

import java.util.concurrent.ThreadLocalRandom;

public class OthelloComputer0 extends AbstractComputer {

    @Override
    public int doMove(AbstractBoard board) {
        int[] validMoves = board.findValidMoves(getCharacter());
        int rndmMove = validMoves[ThreadLocalRandom.current().nextInt(0, validMoves.length)];
        return rndmMove;
    }

    @Override
    protected int minimax(AbstractBoard board, int depth, boolean maximizing, int alpha, int beta) {
        return 0;
    }
}
