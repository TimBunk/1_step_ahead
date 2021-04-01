package Othello;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Othello {

    public static void main(String[] args) {
        Othello othello = new Othello();
        othello.start();
    }

    private OthelloBoard board;
    private OthelloPlayer player1;
    private OthelloPlayer player2;
    private OthelloComputer computer;

    private final int PLAYER_NUMBER = 1;
    private final int COMPUTER_NUMBER = 2;
    Random rand = new Random();

    public Othello() {
        // Initialiseer waardes
        board = new OthelloBoard();
        board.initializeBoard(64);
        player1 = new OthelloPlayer();
        player2 = new OthelloPlayer();
        computer = new OthelloComputer();
        computer.setDifficulty(2);

        //Bepaal of het speler tegen speler is of speler tegen computer
        if (rand.nextInt(2) == 0) {
            //Bepaal wie Witte en Zwarte stenen krijgt
            if (rand.nextInt(2) == 0) {
                player1.setCharacter('W');
                player2.setCharacter('Z');
            }
            else {
                player1.setCharacter('Z');
                player2.setCharacter('W');
            }
        }
        else {
            //Bepaal wie Witte en Zwarte stenen krijgt
            if(rand.nextInt(2) == 0) {
                player1.setCharacter('W');
                computer.setCharacter('Z');
            }
            else {
                player1.setCharacter('Z');
                computer.setCharacter('W');
            }
        }
    }

    public void start(){
        //speler met Zwarte stenen begint
        boolean player1Turn = (player1.getCharacter()=='Z');
        boolean player2Turn = (player2.getCharacter()=='Z');

        int turnCount = 1;
        boolean gameOver = false;
        boolean player1Passed = false;
        boolean player2Passed = false;
        boolean computerPassed = false;

        while(!gameOver && !(player1Passed && computerPassed || player2Passed)){
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
                    System.out.println("Player placed: " + move);
                    board.placeMove(move, player1.getCharacter());
                }
                else{
                    System.out.println("Player passed");
                    player1Passed = true;
                }
            } else if (player2Turn) {
                if(board.findValidMoves(player2.getCharacter()).length != 0){
                    int move = player2.doMove(board);
                    System.out.println("Player placed: " + move);
                    board.placeMove(move, player2.getCharacter());
                }
                else{
                    System.out.println("Player passed");
                    player2Passed = true;
                }
            }
            else {
                if(board.findValidMoves(computer.getCharacter()).length != 0){
                    int move = computer.doMove(board);
                    System.out.println("Computer placed: " + move);
                    board.placeMove(move, computer.getCharacter());
                }
                else {
                    System.out.println("Computer passed");
                    computerPassed = true;
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
        int computerPoints = board.count(computer.getCharacter());

        if(player1Points > computerPoints && computerPoints > 0){
            System.out.println("Je hebt gewonnen met een score van " + player1Points + " tegen " + computerPoints + "!\nGefeliciteerd!");
        }
        else if(player1Points > player2Points && player2Points > 0){
            System.out.println("Je hebt gewonnen met een score van " + player1Points + " tegen " + player2Points + "!\nGefeliciteerd!");
        }
        else if(computerPoints > player1Points){
            System.out.println("Je hebt verloren met een score van " + player1Points + " tegen " + computerPoints + ".\nVolgende keer beter!");
        }
        else if(player2Points > player1Points){
            System.out.println("Je hebt verloren met een score van " + player1Points + " tegen " + player2Points + "!\nVolgende keer beter!");
        }
        else if (player1Points == computerPoints){
            System.out.println("Je hebt gelijkgespeeld met een score van " + player1Points + " tegen " + computerPoints + "!\nBijna gewonnen!");
        }
        else {
            System.out.println("Je hebt gelijkgespeeld met een score van " + player1Points + " tegen " + player2Points + "!\nBijna gewonnen!");
        }
    }
}
