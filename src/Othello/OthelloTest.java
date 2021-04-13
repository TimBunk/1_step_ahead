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



        // 10*10 = 100 keere doorlopen
        //for (int i=10;i<=15;i+=5) { Tim
        //for (int i=20;i<=25;i+=5) { Ids
        //for (int i=30;i<=35;i+=5) { Anton
        //for (int i=40;i<=45;i+=5) { Arjan
        //for (int i=50;i<=55;i+=5) { Bjorn
            for (int j=10;j<=55;j+=5) {
                OthelloComputer2 player1 = new OthelloComputer2(7, 30000, 1, i, j);

                int score = 0;
                // 10*10 = 100 keer doorlopen
                for (int k=10;k<=55;k+=5) { // Stability
                    for (int l = 10; l <= 55; l += 5) { // Mobility
                        OthelloComputer2 player2 = new OthelloComputer2(7, 30000, 1, k, l);

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