package Othello;

import Shared.AbstractPlayer;
import Shared.RandomComputer;

import java.util.Random;
import java.util.Date;

public class OthelloTest {

    public static void main(String[] args) {
        long startTime = new Date().getTime();

        OthelloTest othello = new OthelloTest(new RandomComputer(), new OthelloComputer1(8, 9500));
        int i = 0;
        while (i < 100) {
            othello.start();
            i++;
        }

        long endTime = new Date().getTime();
        long timeElapsed = endTime - startTime;
        
        System.out.println("Totaal aantal keer getest: " + i + " keer");
        System.out.println("Player 1 heeft " + othello.player1Wins + " keer gewonnen");
        System.out.println("Player 2 heeft " + othello.player2Wins + " keer gewonnen");
        System.out.println("Er is " + othello.draw + " keer gelijk gespeeld");
        System.out.println("Totale tijd van de test in secondes: " + timeElapsed/1000);
    }

    private OthelloBoard board;
    private AbstractPlayer player1;
    private AbstractPlayer player2;

    private int player1Wins;
    private int player2Wins;
    private int draw;

    /**
     * @param player1 The first player
     * @param player2 The second player
     */
    public OthelloTest(AbstractPlayer player1, AbstractPlayer player2) {
        // Initialiseer waardes
        board = new OthelloBoard();
        board.initializeBoard(64);

        this.player1 = player1;
        this.player2 = player2;

        player1Wins = 0;
        player2Wins = 0;
        draw = 0;
    }

    public void start() {
        //Bepaal wie Witte en Zwarte stenen krijgt
        if (new Random().nextInt(2) == 0) {
            player1.setCharacter('W');
            player2.setCharacter('Z');
        }
        else {
            player1.setCharacter('Z');
            player2.setCharacter('W');
        }
        //speler met Zwarte stenen begint
        boolean player1Turn = (player1.getCharacter()=='Z');

        boolean gameOver = false;
        boolean player1Passed = false;
        boolean player2Passed = false;

        while(!gameOver && !(player1Passed && player2Passed)){
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
            player1Wins++;
        }
        else if(player2Points > player1Points){
            System.out.println("Player2 wint met " + player2Points + " tegen " + player1Points);
            player2Wins++;
        }
        else {
            System.out.println("Gelijkspel met een score van " + player1Points);
            draw++;
        }
        board.initializeBoard(64);
    }
}
