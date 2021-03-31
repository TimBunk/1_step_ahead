package Othello;

import Shared.AbstractBoard;
import Shared.AbstractComputer;

public class OthelloComputer extends AbstractComputer {

    @Override
    protected int minimax(AbstractBoard board, int depth, boolean maximizing) {
        return 0;
    }

    @Override
    public int doMove(AbstractBoard board) {
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
