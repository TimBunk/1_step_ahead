package OthelloTest;

import Othello.Othello;
import Othello.OthelloComputer2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class OthelloDepthTest {

    public static void main(String[] args) throws IOException {

        long startTime = new Date().getTime();
        BufferedWriter myWriter = new BufferedWriter(new FileWriter("computer2_Depth_DATA.txt"));

        for(int t = 5; t <=10; t++){
            for(int depth = 5; depth <=10; depth++) {
                OthelloComputer2 player1 = new OthelloComputer2(depth, t*1000, 1, 6,6);
                OthelloComputer2 player2 = new OthelloComputer2(depth, t*1000, 1, 6,6);

                Othello othello = new Othello(player1, player2, false);
                othello.start();

                int totalThreads = player1.getTotalThreads() + player2.getTotalThreads();
                int totalCompletedWorkers = player1.getTotalCompletedWorkers() + player2.getTotalCompletedWorkers();
                int totalCompletedWorkersOverTime = player1.getTotalCompletedWorkersOverTime() + player2.getTotalCompletedWorkersOverTime();

                myWriter.write("Time: " + t + " seconds, depth: " + depth + ", created " + totalThreads + " threads, with " + totalCompletedWorkers + " finishing, " + totalCompletedWorkersOverTime + " of which in overtime.\n");
                myWriter.flush();
            }
        }

        long endTime = new Date().getTime();
        long timeElapsed = endTime - startTime;

        System.out.println("Totale tijd van de test in secondes: " + timeElapsed/1000);

        myWriter.close();
    }
}
