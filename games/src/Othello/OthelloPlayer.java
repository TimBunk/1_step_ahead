package Othello;

import Shared.AbstractBoard;
import Shared.AbstractPlayer;

import java.util.Scanner;

public class OthelloPlayer extends AbstractPlayer {

    @Override
    public int doMove(AbstractBoard board) {
        Scanner sc= new Scanner(System.in);
        int move;
        do {
            // Lees wat de player intypt
            int[] validMoves = board.findValidMoves(getCharacter());
            System.out.print("De volgende zetten zijn geldig: ");
            for(int i = 0; i < validMoves.length; i++){
                if(i != validMoves.length-1){
                    System.out.print(validMoves[i] + ", ");
                }
                else{
                    System.out.print( "en " + validMoves[i] + ".\nVoer één van deze zetten in: ");
                }
            }
            move = sc.nextInt();
            System.out.println(move);
            // En check of de player een valid move kiest om te spelen anders vraag de speler nogmaals een nummer in te vullen
        } while (!board.isMoveValid(move, getCharacter()));
        return move;
    }
}
