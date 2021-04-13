package Othello;

import Shared.AbstractBoard;

public class OthelloComputer1 extends OthelloComputer {

    /**
     * Constructor
     * @param depth The depth is used for the minimax algorithm
     * @param maxTimeInMilliseconds The amount of time we have to calculate the best possible move
     */
    public OthelloComputer1(int depth, long maxTimeInMilliseconds) {
        super(depth, maxTimeInMilliseconds);
    }

    /**
     * @param board The board to be evaluated
     * @return Returns the amount of stones player1 has minus the amount of stones player2 has
     */
    @Override
    protected int eval(AbstractBoard board) {
        int computerPoints = board.count(getCharacter());
        int emptyPlaces = board.count('.');
        int playerPoint = board.length() - emptyPlaces - computerPoints;
        return computerPoints - playerPoint;
    }
}
