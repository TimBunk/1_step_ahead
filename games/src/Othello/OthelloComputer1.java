package Othello;

import Shared.AbstractBoard;

public class OthelloComputer1 extends OthelloComputer {

    OthelloComputer1(int depth, long maxTimeInMilliseconds) {
        super(depth, maxTimeInMilliseconds);
    }

    @Override
    protected int eval(AbstractBoard board) {
        int computerPoints = board.count(getCharacter());
        int emptyPlaces = board.count('.');
        int playerPoint = board.length() - emptyPlaces - computerPoints;
        return computerPoints - playerPoint;
    }
}
