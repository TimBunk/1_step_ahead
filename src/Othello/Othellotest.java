package Othello;

import Shared.AbstractPlayer;
import Shared.RandomComputer;
import java.util.Date;

class OthelloTest {

    public static void main(String[] args) {
        long startTime = new Date().getTime();

        int i = 0;
        int player1Wins = 0;
        int player2Wins = 0;
        int draws = 0;
        AbstractPlayer player1 = new OthelloComputer2(8, 9500, 1, 5, 3);
        AbstractPlayer player2 = new OthelloComputer1(8, 9500);
        while (i < 10) {
            Othello othello = new Othello(player1, player2, false);
            switch (othello.start()) {
                case 1:
                    player1Wins++;
                    break;
                case -1:
                    player2Wins++;
                    break;
                case 0:
                    draws++;
                    break;
            }
            i++;
        }
        long endTime = new Date().getTime();
        long timeElapsed = endTime - startTime;

        System.out.println("Totaal aantal keer getest: " + i + " keer");
        System.out.println("Player 1 heeft " + player1Wins + " keer gewonnen");
        System.out.println("Player 2 heeft " + player2Wins + " keer gewonnen");
        System.out.println("Er is " + draws + " keer gelijk gespeeld");
        System.out.println("Totale tijd van de test in secondes: " + timeElapsed/1000);
    }
}
