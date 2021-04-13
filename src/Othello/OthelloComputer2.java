package Othello;

import Shared.AbstractBoard;

public class OthelloComputer2 extends OthelloComputer {

    private int single_stone;
    private int stable_stone;
    private int mobility;

    /**
     * Constructor
     * @param depth The depth is used for the minimax algorithm
     * @param maxTimeInMilliseconds The amount of time we have to calculate the best possible move
     * @param single_stone how many points is a single_stone worth
     * @param stable_stone how many points is a stable_stone worth
     * @param mobility how many points for every valid move possible
     */
    public OthelloComputer2(int depth, long maxTimeInMilliseconds, int single_stone, int stable_stone, int mobility) {
        super(depth, maxTimeInMilliseconds);
        this.single_stone = single_stone;
        this.stable_stone = stable_stone;
        this.mobility = mobility;
    }

    /**
     * @param board The board to be evaluated
     * @return Returns a evaluation based on amount of stones, stability and mobility
     */
    @Override
    protected int eval(AbstractBoard board) {

        char playerChar = (getCharacter() == 'Z' ? 'W' : 'Z');

        int computerPoints = board.count(getCharacter());
        int playerPoint = board.count(playerChar);
        int single_stones_score = (computerPoints - playerPoint) * single_stone;

        int computerMobility = board.validMoveCount(getCharacter());
        int playerMobility = board.validMoveCount(playerChar);
        int mobility_score = (computerMobility - playerMobility) * mobility;

        int computerStableStones = board.getStableStoneCount(getCharacter());
        int playerStableStones = board.getStableStoneCount(playerChar);
        int stable_stones_score = (computerStableStones - playerStableStones) * stable_stone;

        return single_stones_score + mobility_score + stable_stones_score;
    }
}
