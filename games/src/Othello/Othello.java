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

    private char playerCharacter;
    private char computerCharacter;
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
            playerCharacter = 'W';
            computerCharacter = 'Z';
        }
        else{
            playerCharacter = 'Z';
            computerCharacter = 'W';
        }
    }

    public void start(){
        //speler met Zwarte stenen begint
        boolean playersTurn = (playerCharacter=='Z');

        int turnCount = 0;
        boolean gameOver = false;
        boolean playerPassed = false;
        boolean computerPassed = false;

        while(!gameOver && !(playerPassed && computerPassed)){
            //players do moves
            //fill up the board
            //if a player passes, change the relevant variable
            //if both pass, the game is over
            //if the other player makes a move, set the variable back to false
            gameOver = isGameOver();
        }
        //display final board
        //display winner

    }
}
