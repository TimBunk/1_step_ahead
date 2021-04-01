package Othello;

import Shared.AbstractBoard;
import Shared.AbstractComputer;

import java.util.concurrent.ThreadLocalRandom;

public class OthelloComputer0 extends AbstractComputer {

    /**
     * @param board The board on which a move is placed
     * @return The place on the board where we want to do a move
     */
    @Override
    public int doMove(AbstractBoard board) {
        int[] validMoves = board.findValidMoves(getCharacter());
        int rndmMove = validMoves[ThreadLocalRandom.current().nextInt(0, validMoves.length)];
        return rndmMove;
    }

    /**
     * Not implemented. If you want the computer to use minimax to decide its moves, use a different class.
     */
    @Override
    protected int minimax(AbstractBoard board, int depth, boolean maximizing, int alpha, int beta) {
        return 0;
    }
}
