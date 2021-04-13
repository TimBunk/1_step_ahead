package Othello;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class OthelloSpeedTest {

    public static void main(String[] args) throws IOException {

        ArrayList<Long> times = new ArrayList<>();

        for (int i=0;i<10;i++) {
            long startTime = new Date().getTime();
            Othello othello = new Othello(new OthelloComputer2(7, 9500, 1, 6, 6), new OthelloComputer2(7, 9500, 1, 6, 6), false);
            othello.start();
            long endTime = new Date().getTime();
            long timeElapsed = endTime - startTime;
            times.add(timeElapsed);
        }
        long totalTime = 0;
        for (int i=0;i<times.size();i++) {
            totalTime += times.get(i);
        }
        System.out.println("Gemiddelde tijd is: " + (totalTime/times.size()));
    }
}
