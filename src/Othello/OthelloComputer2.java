package Othello;

import Shared.AbstractBoard;

public class OthelloComputer2 extends OthelloComputer {

    private int single_stone;
    private int stable_stone;
    private int mobility;

    /**
     * @param depth                 The depth used for the minimax algorithm
     * @param maxTimeInMilliseconds The amount of time allowed to calculate the best possible move
     * @param single_stone          The value given to any stone
     * @param stable_stone          The value given to stable stones, i.e. stones that can't be flipped by the opponent
     * @param mobility              The value given to mobility, i.e. how many moves are possible
     */
    public OthelloComputer2(int depth, long maxTimeInMilliseconds, int single_stone, int stable_stone, int mobility) {
        super(depth, maxTimeInMilliseconds);
        this.single_stone = single_stone;
        this.stable_stone = stable_stone;
        this.mobility = mobility;
    }

    /**
     * @param board The board to be evaluated
     * @return      How good the board is, evaluated based on the number of stones, the number of stable stones,
     *              and the number of valid moves left
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
