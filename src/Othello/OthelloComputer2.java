package Othello;

import Shared.AbstractBoard;

public class OthelloComputer2 extends OthelloComputer {

    private int single_stone;
    private int stable_stone;
    private int mobility;
    private final int corner_value = 10000;

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

        int corner_score = getPointsForCorners(board);

        return single_stones_score + mobility_score + stable_stones_score + corner_score;
    }

    private int getPointsForCorners(AbstractBoard board) {
        int points = 0;
        char upperLeftCorner = board.getCharAtPosition(0);
        char upperRightCorner = board.getCharAtPosition(7);
        char lowerLeftCorner = board.getCharAtPosition(56);
        char lowerRightCorner = board.getCharAtPosition(63);
        // Check upper left corner
        if (upperLeftCorner != '.') {
            if (upperLeftCorner == getCharacter()) {
                points += corner_value;
            }
            else {
                points -= corner_value;
            }
        }
        // Check upper right corner
        if (upperRightCorner != '.') {
            if (upperRightCorner == getCharacter()) {
                points += corner_value;
            }
            else {
                points -= corner_value;
            }
        }
        // Check lower left corner
        if (lowerLeftCorner != '.') {
            if (lowerLeftCorner == getCharacter()) {
                points += corner_value;
            }
            else {
                points -= corner_value;
            }
        }
        // Check lower right corner
        if (lowerRightCorner != '.') {
            if (lowerRightCorner == getCharacter()) {
                points += corner_value;
            }
            else {
                points -= corner_value;
            }
        }
        return points;
    }
}
