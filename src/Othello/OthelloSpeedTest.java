package Othello;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class OthelloSpeedTest {

    public static void main(String[] args) throws IOException {
        long startTime = new Date().getTime();


        Othello othello = new Othello(new OthelloComputer2(7, 9500, 1, 6, 6), new OthelloComputer2(7, 9500, 1, 6, 6), false);
        othello.start();


        long endTime = new Date().getTime();
        long timeElapsed = endTime - startTime;

        System.out.println("Totale tijd van de test in milliseconden: " + timeElapsed);
    }
}
