package BKE;

import Shared.AbstractBoard;
import Shared.AbstractPlayer;

public class BKEcomputer extends AbstractPlayer {

    /**
     * @param board The board on which the move is placed
     * @return The place on the board where we want to do a move
     */
    @Override
    public int doMove(AbstractBoard board) {
        // De computer maximaliseert
        int bestEvaluation = Integer.MIN_VALUE;
        int bestMove = 0;
        int[] validMoves = board.findValidMoves(getCharacter());
        // Loop door alle tiles van het bord
        for (int move : validMoves) {
            // Maak een clone van het board en plaats de move erin
            AbstractBoard cloneOfBoard = board.clone();
            cloneOfBoard.placeMove(move, getCharacter());
            // Roep het minimax algoritme aan om te kijken hoe goed de move was
            int evaluation = minimax(cloneOfBoard, 10, false);
            // Check of deze move beter was dan de laatste move die we hebben geprobeerd
            if (evaluation > bestEvaluation) {
                bestMove = move;
                bestEvaluation = evaluation;
            }
        }
        // Geef een goede move terug
        return bestMove;
    }

    /**
     * @param board      The board the algorithm should consider
     * @param depth      How many iterations the algorithm should look
     * @param maximizing Whether the player whose move is considered wants a maximal or minimal result
     * @return           The evaluation of the moves used
     */
    private int minimax(AbstractBoard board, int depth, boolean maximizing) {
        // Vind de minimizngCharacter
        char minimizingChar = ('X' == getCharacter()) ? 'O' : 'X';

        int evaluation = 0;
        // Als de evaluation zegt dat er een winnaar is dan geven we de evaluation terug
        // of als de diepte 0 is bereikt of als er geen tiles meer vrij zijn
        if ((evaluation = evaluation(board, getCharacter(), minimizingChar, depth)) != 0 || depth == 0 || board.isGameOver()) {
            return evaluation;
        }

        if (maximizing) {
            int max = Integer.MIN_VALUE;
            int[] validMoves = board.findValidMoves(getCharacter());
            // Loop door alle tiles van het bord
            for (int move : validMoves) {
                // Maak een clone van het board
                AbstractBoard cloneOfBoard = board.clone();
                // Plaats de move op het board
                cloneOfBoard.placeMove(move, getCharacter());
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
            int[] validMoves = board.findValidMoves(getCharacter());
            // Loop door alle valid moves van het bord
            for (int move : validMoves) {
                // Maak een clone van het board
                AbstractBoard cloneOfBoard = board.clone();
                // Plaats de move op het board
                cloneOfBoard.placeMove(move, minimizingChar);
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

    /**
     * @param board The board to be evaluated
     * @param maximizingChar The maximizngCharacter
     * @param minimizingChar The minimizingCharacter
     * @param depth the current depth
     * @return How good the board is, evaluated as 1 + depth if the maximizng player wins, -1-depth if the minizingCharacter wins, 0 otherwise.
     */
    private int evaluation(AbstractBoard board, char maximizingChar, char minimizingChar, int depth) {
        // Als de maximizngChar wint return 1 + de depth
        if (board.doesCharacterWin(maximizingChar)) {
            // Door de depth eraf te halen of erbij opte tellen kiest de computer de snelste route om te winnen
            return 1 + depth; // Return -1 als de speler wint en 1 als de computer wint
        }
        // Als de minizingChar wint return -1 - de depth
        else if (board.doesCharacterWin(minimizingChar)) {
            return -1 - depth;
        }
        // Return 0 als het spel nog niet klaar is
        return 0;
    }
}
