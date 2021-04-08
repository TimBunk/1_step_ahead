package Othello;

import Shared.AbstractBoard;

public class OthelloComputer2 extends OthelloComputer {

    private int single_stone;
    private int stable_stone;
    private int mobility;

    public OthelloComputer2(int depth, long maxTimeInMilliseconds, int single_stone, int stable_stone, int mobility) {
        super(depth, maxTimeInMilliseconds);
        this.single_stone = single_stone;
        this.stable_stone = stable_stone;
        this.mobility = mobility;
    }

    @Override
    protected int eval(AbstractBoard board) {

        char playerChar = (getCharacter() == 'Z' ? 'W' : 'Z');

        int computerPoints = board.count(getCharacter());
        int playerPoint = board.count(playerChar);
        int single_stones_score = (computerPoints - playerPoint) * single_stone;

        int computerMobility = board.validMoveCount(getCharacter());
        int playerMobility = board.validMoveCount(playerChar);
        int mobility_score = (computerMobility - playerMobility) * mobility;

        OthelloBoard oBoard = (OthelloBoard)board;
        int computerStableStones = oBoard.getStableStoneCount(getCharacter());
        int playerStableStones = oBoard.getStableStoneCount(playerChar);
        int stable_stones_score = (computerStableStones - playerStableStones) * stable_stone;

        return single_stones_score + mobility_score + stable_stones_score;
    }
}
