package BKE;

import Shared.AbstractBoard;
import Shared.AbstractComputer;

import java.util.concurrent.ThreadLocalRandom;

public class BKEcomputer extends AbstractComputer {

    @Override
    public int doMove(AbstractBoard board) {
        switch (this.getDifficulty()) {
            default:
                return doMove0(board);
            case 0:
                return doMove0(board);
            case 1:
                return doMove1(board);
        }
    }

    public int doMove0(AbstractBoard board) {
        int rndmMove = ThreadLocalRandom.current().nextInt(0, board.length());
        while (board.isMoveValid(rndmMove) == false) {
            rndmMove++;
            if (rndmMove >= board.length()) {
                rndmMove = 0;
            }
        }
        return rndmMove;
    }

    public int doMove1(AbstractBoard board) {
        // De computer maximaliseert
        int bestEvaluation = Integer.MIN_VALUE;
        int bestMove = 0;
        // Loop door alle tiles van het bord
        for (int i = 0; i < board.length(); i++) {
            // Kijk of de tile beschikbaar is
            if (board.isMoveValid(i)) {
                // Maak een clone van het board en plaats de move erin
                AbstractBoard cloneOfBoard = board.clone();
                cloneOfBoard.placeMove(i, BoterKaasEieren.COMPUTERS_CHAR);
                // Roep het minimax algoritme aan om te kijken hoe goed de move was
                int evaluation = minimax(cloneOfBoard, 10, false);
                // Check of deze move beter was dan de laatste move die we hebben geprobeerd
                if (evaluation > bestEvaluation) {
                    bestMove = i;
                    bestEvaluation = evaluation;
                }
            }
        }
        // Geef een goede move terug
        return bestMove;
    }

    @Override
    protected int minimax(AbstractBoard board, int depth, boolean maximizing) {
        int evaluation = 0;
        // Als de evaluation zegt dat er een winnaar is dan geven we de evaluation terug
        // of als de diepte 0 is bereikt of als er geen tiles meer vrij zijn
        if ((evaluation = evaluation(board, !maximizing)) != 0 || depth == 0 || board.anyTilesAvailable() == false) {
            return evaluation;
        }

        if (maximizing) {
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < board.length(); i++) {
                // Check of de move valid is
                if (board.isMoveValid(i)) {
                    // Maak een clone van het board
                    AbstractBoard cloneOfBoard = board.clone();
                    // Plaats de move op het board
                    cloneOfBoard.placeMove(i, BoterKaasEieren.COMPUTERS_CHAR);
                    // Voer recursief de minimax uit tot de base case is bereikt
                    evaluation = minimax(cloneOfBoard, depth-1, false);
                    // Als de evaluatie groter is dan de huidge max dan was dat dus een goede zet en vervangen we max met evaluation
                    if (evaluation > max) {
                        max = evaluation;
                    }
                }
            }
            // Geef de max waarde terug
            return max;
        }
        else {
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < board.length(); i++) {
                // Check of de move valid is
                if (board.isMoveValid(i)) {
                    // Maak een clone van het board
                    AbstractBoard cloneOfBoard = board.clone();
                    // Plaats de move op het board
                    cloneOfBoard.placeMove(i, BoterKaasEieren.PLAYERS_CHAR);
                    // Voer recursief de minimax uit tot de base case is bereikt
                    evaluation = minimax(cloneOfBoard, depth-1, true);
                    // Als de evaluatie kleiner is dan de huidge min dan was dat dus een goede zet en vervangen we min met evaluation
                    if (evaluation < min) {
                        min = evaluation;
                    }
                }
            }
            // Geef de min waarde terug
            return min;
        }
    }

    private int evaluation(AbstractBoard board, boolean isComputer) {
        // Pak de juiste character om te evaluaren op het board
        char c = isComputer ? BoterKaasEieren.COMPUTERS_CHAR : BoterKaasEieren.PLAYERS_CHAR;
        // Check of die character kan heeft gewonnen
        if (board.doesCharacterWin(c)) {
            return isComputer ? 1 : -1; // Return -1 als de speler wint en 1 als de computer wint
        }
        // Return 0 als het spel nog niet klaar is
        return 0;
    }
}
