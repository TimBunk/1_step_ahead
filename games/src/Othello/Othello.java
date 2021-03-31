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
    private OthelloPlayer player;
    private OthelloComputer computer;

    private final int PLAYER_NUMBER = 1;
    private final int COMPUTER_NUMBER = 2;
    Random rand = new Random();

    public Othello(){
        // Initialiseer waardes
        board = new OthelloBoard();
        board.initializeBoard(64);
        player = new OthelloPlayer();
        computer = new OthelloComputer();
        computer.setDifficulty(0);

        //Bepaal wie Witte en Zwarte stenen krijgt
        if(rand.nextInt(2) == 0){
            player.setCharacter('W');
            computer.setCharacter('Z');
        }
        else{
            player.setCharacter('Z');
            computer.setCharacter('W');
        }
    }

    public void start(){
        //speler met Zwarte stenen begint
        boolean playersTurn = (player.getCharacter()=='Z');

        int turnCount = 1;
        boolean gameOver = false;
        boolean playerPassed = false;
        boolean computerPassed = false;

        while(!gameOver && !(playerPassed && computerPassed)){
            //players do moves
            //fill up the board
            //if a player passes, change the relevant variable
            //if both pass, the game is over
            //if the other player makes a move, set the variable back to false

            board.printBoard();
            System.out.println("Ronde: " + turnCount);

            if (playersTurn) {
                if(board.findValidMoves(player.getCharacter()).length != 0){
                    int move = player.doMove(board);
                    board.placeMove(move, player.getCharacter());
                }
                else{
                    playerPassed = true;
                }
            }
            else {
                if(board.findValidMoves(computer.getCharacter()).length != 0){
                    int move = computer.doMove(board);
                    board.placeMove(move, computer.getCharacter());
                }
                computerPassed = true;
            }
            playersTurn = !playersTurn;

            turnCount++;

            gameOver = board.isGameOver();
        }
        //display final board
        //display winner
        board.printBoard();

        int playerPoints = 0;
        int computerPoints = 0;
        for(char character : board.getBoard()){
            if(character == player.getCharacter()){
                playerPoints++;
            }
            else if(character == computer.getCharacter()){
                computerPoints++;
            }
        }

        if(playerPoints > computerPoints){
            System.out.println("Je hebt gewonnen met een score van " + playerPoints + " tegen " + computerPoints + "!\nGefeliciteerd!");
        }
        else if(computerPoints > playerPoints){
            System.out.println("Je hebt verloren met een score van " + playerPoints + " tegen " + computerPoints + ".\nVolgende keer beter!");
        }
        else{
            System.out.println("Je hebt gelijkgespeeld met een score van " + playerPoints + " tegen " + computerPoints + "!\nBijna gewonnen!");
        }
    }
}
