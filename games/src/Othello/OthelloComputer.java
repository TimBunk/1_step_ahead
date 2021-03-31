package Othello;

import Shared.AbstractBoard;
import Shared.AbstractComputer;

import java.util.concurrent.ThreadLocalRandom;

public class OthelloComputer extends AbstractComputer {

    @Override
    public int doMove(AbstractBoard board) {
        switch (this.getDifficulty()) {
            default:
                return doMove0(board); // Random moves
            case 0:
                return doMove0(board); // Random moves
            case 1:
                return doMove1(board); // Minimax moves
        }
    }

    public int doMove0(AbstractBoard board) {
        int[] validMoves = board.findValidMoves(getCharacter());
        int rndmMove = validMoves[ThreadLocalRandom.current().nextInt(0, validMoves.length)];
        return rndmMove;
    }

    public int doMove1(AbstractBoard board) {
        return 0; // Voer minimax uit
    }

    @Override
    protected int minimax(AbstractBoard board, int depth, boolean maximizing) {
        return 0;
    }

    private int eval(char[] board){
        //evaluate to what degree the board is in favor of the computer
        /*int playerStones = 0;
        int computerStones = 0;
        for(char position : board){
            if(position == computerCharacter){
                computerStones++;
            }
            else if(position == playerCharacter){
                playerStones++;
            }
        }
        return computerStones - playerStones;*/
        return 0;
    }
}
