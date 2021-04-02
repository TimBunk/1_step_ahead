package Othello;

import Shared.AbstractPlayer;

import java.util.Random;

public class Othello {

    public static void main(String[] args) {
        Othello othello = new Othello(new OthelloComputer0(), new OthelloComputer1(8, 9500));
        othello.start();
    }

    private OthelloBoard board;
    private AbstractPlayer player1;
    private AbstractPlayer player2;

    /**
     * @param player1 The first player
     * @param player2 The second player
     */
    public Othello(AbstractPlayer player1, AbstractPlayer player2) {
        // Initialiseer waardes
        board = new OthelloBoard();
        board.initializeBoard(64);

        this.player1 = player1;
        this.player2 = player2;

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

        int turnCount = 1;
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
            System.out.println("Ronde: " + turnCount);

            if (player1Turn) {
                if(board.findValidMoves(player1.getCharacter()).length != 0){
                    int move = player1.doMove(board);
                    System.out.println("Player1 placed: " + move);
                    board.placeMove(move, player1.getCharacter());
                }
                else{
                    System.out.println("Player1 passed");
                    player1Passed = true;
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
                }
            }

            player1Turn = !player1Turn;

            turnCount++;

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
    }
}
