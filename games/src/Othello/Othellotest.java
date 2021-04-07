package Othello;

import Shared.AbstractPlayer;
import Shared.RandomComputer;

import java.util.Random;

public class Othellotest {

    public static void main(String[] args) {
        Othellotest othello = new Othellotest(new OthelloComputer1(8, 9500), new OthelloComputer1(8, 9500), 20);
        othello.start();
    }

    private OthelloBoardtest board;
    private AbstractPlayer player1;
    private AbstractPlayer player2;
    private int turnLimit;

    /**
     * @param player1 The first player
     * @param player2 The second player
     */
    public Othellotest(AbstractPlayer player1, AbstractPlayer player2, int turnLimit) {
        // Initialiseer waardes
        board = new OthelloBoardtest();
        board.initializeBoard(64);

        this.player1 = player1;
        this.player2 = player2;
        this.turnLimit = turnLimit;

        //Bepaal wie Witte en Zwarte stenen krijgt
        if (new Random().nextInt(2) == 0) {
            player1.setCharacter('W');
            player2.setCharacter('Z');
        }
        else {
            player1.setCharacter('Z');
            player2.setCharacter('W');
        }
    }

    public void start() {
        //speler met Zwarte stenen begint
        boolean player1Turn = (player1.getCharacter()=='Z');

        boolean gameOver = false;
        boolean player1Passed = false;
        boolean player2Passed = false;

        while(!gameOver && !(player1Passed && player2Passed) && board.getTurnCount()<=turnLimit){
            //players do moves
            //fill up the board
            //if a player passes, change the relevant variable
            //if both pass, the game is over
            //if the other player makes a move, set the variable back to false

            board.printBoard();
            System.out.println("Ronde: " + board.getTurnCount());

            if (player1Turn) {
                if(board.findValidMoves(player1.getCharacter()).length != 0){
                    int move = player1.doMove(board);
                    System.out.println("Player1 placed: " + move);
                    board.placeMove(move, player1.getCharacter());
                }
                else{
                    System.out.println("Player1 passed");
                    player1Passed = true;
                    board.increaseTurnCount(); // Als de speler past dan moeten we ook de turn count omhoog doen
                }
            } else {
                if(board.findValidMoves(player2.getCharacter()).length != 0){
                    int move = player2.doMove(board);
                    System.out.println("Player2 placed: " + move);
                    board.placeMove(move, player2.getCharacter());
                }
                else{
                    System.out.println("Player2 passed");
                    player2Passed = true;
                    board.increaseTurnCount(); // Als de speler past dan moeten we ook de turn count omhoog doen
                }
            }

            player1Turn = !player1Turn;

            gameOver = board.isGameOver();
        }
        //display final board
        //display winner
        board.printBoard();

        int player1Points = board.count(player1.getCharacter());
        int player2Points = board.count(player2.getCharacter());

        if(player1Points > player2Points){
            System.out.println("Player1 wint met " + player1Points + " tegen " + player2Points);
        }
        else if(player2Points > player1Points){
            System.out.println("Player2 wint met " + player2Points + " tegen " + player1Points);
        }
        else {
            System.out.println("Gelijkspel met een score van " + player1Points);
        }


        long startTime = System.currentTimeMillis();
        //OthelloComputer1 computer = new OthelloComputer1(8, 9500);
        //computer.setCharacter('W');
        for(int i = 0; i < 100000; i++){
            board.findValidMoves('W');

        }
        long endTime = System.currentTimeMillis();

        long timeTaken = endTime - startTime;
        long avgTime = timeTaken / 100000;
        System.out.println("De tijd die hiervoor nodig was is in totaal " + timeTaken + " milliseconden.");
    }
}
