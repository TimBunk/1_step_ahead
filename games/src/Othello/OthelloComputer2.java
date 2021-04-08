package Othello;

import Shared.AbstractBoard;

public class OthelloComputer2 extends OthelloComputer {

    private final int SINGLE_STONE = 1;
    private final int STABLE_STONE = 5;
    private final int MOBILITY = 3;

    public OthelloComputer2(int depth, long maxTimeInMilliseconds) {
        super(depth, maxTimeInMilliseconds);
    }

    @Override
    protected int eval(AbstractBoard board) {

        char playerChar = (getCharacter() == 'Z' ? 'W' : 'Z');

        int computerPoints = board.count(getCharacter());
        int playerPoint = board.count(playerChar);
        int single_stones_score = (computerPoints - playerPoint) * SINGLE_STONE;

        int computerMobility = board.validMoveCount(getCharacter());
        int playerMobility = board.validMoveCount(playerChar);
        int mobility_score = (computerMobility - playerMobility) * MOBILITY;

        OthelloBoard oBoard = (OthelloBoard)board;
        int computerStableStones = oBoard.getStableStoneCount(getCharacter());
        int playerStableStones = oBoard.getStableStoneCount(playerChar);
        int stable_stones_score = (computerStableStones - playerStableStones) * STABLE_STONE;

        return single_stones_score + mobility_score + stable_stones_score;
    }
}
