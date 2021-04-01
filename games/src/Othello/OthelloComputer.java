package Othello;

import BKE.BoterKaasEieren;
import Shared.AbstractBoard;
import Shared.AbstractComputer;

import java.util.concurrent.ThreadLocalRandom;

public class OthelloComputer extends AbstractComputer {

    @Override
    public int doMove(AbstractBoard board) {
        switch (this.getDifficulty()) {
            default:
                return doMove0(board); // Random moves
            case 0:
                return doMove0(board); // Random moves
            case 1:
                return doMove1(board); // Minimax moves
        }
    }

    public int doMove0(AbstractBoard board) {
        int[] validMoves = board.findValidMoves(getCharacter());
        int rndmMove = validMoves[ThreadLocalRandom.current().nextInt(0, validMoves.length)];
        return rndmMove;
    }

    public int doMove1(AbstractBoard board) {
        // De computer maximaliseert
        int bestEvaluation = Integer.MIN_VALUE;
        int bestMove = 0;
        // Loop door alle tiles van het bord
        int[] validMoves = board.findValidMoves(getCharacter());
        for (int i = 0; i < validMoves.length; i++) {
            // Maak een clone van het board en plaats de move erin
            AbstractBoard cloneOfBoard = board.clone();
            cloneOfBoard.placeMove(i, getCharacter());
            // Roep het minimax algoritme aan om te kijken hoe goed de move was
            int evaluation = minimax(cloneOfBoard, 10, false);
            // Check of deze move beter was dan de laatste move die we hebben geprobeerd
            if (evaluation > bestEvaluation) {
                bestMove = i;
                bestEvaluation = evaluation;
            }
        }
        // Geef een goede move terug
        return bestMove;
    }

    @Override
    protected int minimax(AbstractBoard board, int depth, boolean maximizing) {
        int evaluation = 0;
        // Pak de juiste character
        char c = getCharacter();
        if (maximizing == false) {
            if (c == 'W') {
                c = 'Z';
            }
            else {
                c = 'W';
            }
        }
        // Pak alle valid moves voor die character
        int[] validMoves = board.findValidMoves(c);
        if (board.isGameOver() || depth == 0) {
            return eval(board);
        }
        else if (validMoves.length == 0) {
            minimax(board, depth-1, !maximizing);
        }


        if (maximizing) {
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < validMoves.length; i++) {
                // Maak een clone van het board
                AbstractBoard cloneOfBoard = board.clone();
                // Plaats de move op het board
                cloneOfBoard.placeMove(i, c);
                // Voer recursief de minimax uit tot de base case is bereikt
                evaluation = minimax(cloneOfBoard, depth-1, false);
                // Als de evaluatie groter is dan de huidge max dan was dat dus een goede zet en vervangen we max met evaluation
                if (evaluation > max) {
                    max = evaluation;
                }
            }
            // Geef de max waarde terug
            return max;
        }
        else {
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < validMoves.length; i++) {
                // Maak een clone van het board
                AbstractBoard cloneOfBoard = board.clone();
                // Plaats de move op het board
                cloneOfBoard.placeMove(i, c);
                // Voer recursief de minimax uit tot de base case is bereikt
                evaluation = minimax(cloneOfBoard, depth-1, true);
                // Als de evaluatie kleiner is dan de huidge min dan was dat dus een goede zet en vervangen we min met evaluation
                if (evaluation < min) {
                    min = evaluation;
                }
            }
            // Geef de min waarde terug
            return min;
        }
    }

    private int eval(AbstractBoard board){
        int computerPoints = board.count(getCharacter());
        int emptyPlaces = board.count('.');
        int playerPoint = board.length() - emptyPlaces - computerPoints;
        return computerPoints - playerPoint;
    }

}
