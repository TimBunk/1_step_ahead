package Othello;

import Shared.AbstractPlayer;
import Shared.RandomComputer;

import java.io.*;
import java.util.Date;

public class OthelloTest {

    public static void main(String[] args) throws IOException {

        long startTime = new Date().getTime();
        BufferedWriter myWriter = new BufferedWriter(new FileWriter("computer2_DATA.txt"));

        int[][] bestComputers = {
                {6, 6, 63},
                {6, 5, 61 },
                {5, 4, 60 },
                {5, 5, 56},
                {4, 3, 52},
                {4, 5, 44},
                {4, 5, 44},
                {5, 7, 43},
                {4, 7, 43},
                {5, 6, 41}
        };


        //for (int i=10;i<15;i+=5) { // 1 Tim
        //for (int i=15;i<20;i+=5) { // 2 Anton
        //for (int i=20;i<25;i+=5) { // 3 Arjan
        //for (int i=25;i<25;i+=5) { // 4 Ids
        //for (int i=30;i<35;i+=5) { // 5 Bjorn
            for (int j=10;j<=30;j+=5) {
                OthelloComputer2 player1 = new OthelloComputer2(7, 10000, 1, i, j);

                int score = 0;
                for (int k=0;k<bestComputers.length;k++) {
                    OthelloComputer2 player2 = new OthelloComputer2(7, 10000, 1, bestComputers[k][0], bestComputers[k][1]);

                    Othello othello = new Othello(player1, player2, false);
                    score += othello.start();
                    othello = new Othello(player2, player1, false);
                    score += (-1 * othello.start());

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