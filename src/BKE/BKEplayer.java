package BKE;

import Shared.AbstractBoard;
import Shared.AbstractPlayer;

import java.util.Scanner;

public class BKEplayer extends AbstractPlayer {

    /**
     * @param board The board on which the move is placed
     * @return The place on the board where we want to do a move
     */
    @Override
    public int doMove(AbstractBoard board) {
        Scanner sc= new Scanner(System.in);
        int move = 0;
        do {
            // Lees wat de player intypt
            System.out.print("Voer een nummer van 0 tot en met 8 in: ");
            move = sc.nextInt();
            System.out.println(move);
            // En check of de player een valid move kiest om te spelen anders vraag de speler nogmaals een nummer in te vullen
        } while (!board.isMoveValid(move));
        return move;
    }
}
