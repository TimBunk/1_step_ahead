package Othello;

import Shared.AbstractPlayer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class OthelloTest {

    public static void main(String[] args) throws IOException {

        long startTime = new Date().getTime();
        BufferedWriter myWriter = new BufferedWriter(new FileWriter("computer2_DATA.txt"));

        AbstractPlayer player1 = null;
        AbstractPlayer player2 = null;
        for (int i=0;i<2;i++) { // 1
        // for (int i=2;i<4;i++) { // 2
        // for (int i=4;i<6;i++) { // 3
        // for (int i=6;i<7;i++) { // 4
        // for (int i=7;i<8;i++) { // 5
            for (int j=0;j<8;j++) {
                player1 = new OthelloComputer2(7, 29500, 1, i, j);
                int score = 0;
                for (int k=0;k<8;k++) {
                    for (int l=0;l<8;l++) {
                        player2 = new OthelloComputer2(7, 29500, 1, k, l);
                        Othello othello = new Othello(player1, player2, false);
                        score += othello.start();
                        othello = new Othello(player2, player1, false);
                        score += (-1 * othello.start());
                    }
                }
                myWriter.write("" + i + " " + j + " " + score + "\n");
                myWriter.flush();
            }
        }
        long endTime = new Date().getTime();
        long timeElapsed = endTime - startTime;

        /*System.out.println("Totaal aantal keer getest: " + i + " keer");
        System.out.println("Player 1 heeft " + player1Wins + " keer gewonnen");
        System.out.println("Player 2 heeft " + player2Wins + " keer gewonnen");
        System.out.println("Er is " + draws + " keer gelijk gespeeld");*/
        System.out.println("Totale tijd van de test in secondes: " + timeElapsed/1000);

        myWriter.close();
    }
}
