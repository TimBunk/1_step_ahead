package Othello;

import Shared.AbstractBoard;

public class OthelloComputer1 extends OthelloComputer {

    /**
     * Constructor
     * @param depth                 The depth used for the minimax algorithm
     * @param maxTimeInMilliseconds The amount of time allowed to calculate the best possible move
     */
    public OthelloComputer1(int depth, long maxTimeInMilliseconds) {
        super(depth, maxTimeInMilliseconds);
    }

    /**
     * @param board The board to be evaluated
     * @return      How good the board is, evaluated as how many stones more are controlled than by the opponent
     */
    @Override
    protected int eval(AbstractBoard board) {
        int computerPoints = board.count(getCharacter());
        int emptyPlaces = board.count('.');
        int playerPoint = board.length() - emptyPlaces - computerPoints;
        return computerPoints - playerPoint;
    }
}
