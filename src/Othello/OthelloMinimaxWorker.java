package Othello;

import Shared.AbstractBoard;

import java.util.concurrent.Callable;

public class OthelloMinimaxWorker implements Callable<Integer> {

    private OthelloComputer computer;
    private int depth;
    private AbstractBoard board;

    /**
     * Constructor
     * @param computer  The computer
     * @param depth     The max depth that the minimax will traverse
     * @param board     The board that is being used to evaluate and place moves on
     */
    OthelloMinimaxWorker(OthelloComputer computer, int depth, AbstractBoard board) {
        this.computer = computer;
        this.depth = depth;
        this.board = board;
    }

    /**
     * @param board         The board the algorithm should consider
     * @param depth         How many iterations the algorithm should look
     * @param maximizing    Whether the player whose move is considered wants a maximal or minimal result
     * @param alpha         Helper argument for pruning
     * @param beta          Helper argument for pruning
     * @return              The evaluation of the moves used
     */
    protected int minimax(AbstractBoard board, int depth, boolean maximizing, int alpha, int beta) {
        // Pak de juiste character
        char c = computer.getCharacter();
        if (!maximizing) {
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
            return computer.eval(board);
        }
        else if (validMoves.length == 0) {
            minimax(board, depth-1, !maximizing, alpha, beta);
        }

        int evaluation = 0;
        if (maximizing) {
            int max = Integer.MIN_VALUE;
            for (int move : validMoves) {
                // Maak een clone van het board
                AbstractBoard cloneOfBoard = board.clone();
                // Plaats de move op het board
                cloneOfBoard.placeMove(move, c);
                // Voer recursief de minimax uit tot de base case is bereikt
                evaluation = minimax(cloneOfBoard, depth-1, false, alpha, beta);
                // Als de evaluatie groter is dan de huidge max dan was dat dus een goede zet en vervangen we max met evaluation
                if (evaluation > max) {
                    max = evaluation;
                }
                if(evaluation > alpha){
                    alpha = evaluation;
                }
                if(beta <= alpha){
                    break;
                }
            }
            // Geef de max waarde terug
            return max;
        }
        else {
            int min = Integer.MAX_VALUE;
            for (int move : validMoves) {
                // Maak een clone van het board
                AbstractBoard cloneOfBoard = board.clone();
                // Plaats de move op het board
                cloneOfBoard.placeMove(move, c);
                // Voer recursief de minimax uit tot de base case is bereikt
                evaluation = minimax(cloneOfBoard, depth-1, true, alpha, beta);
                // Als de evaluatie kleiner is dan de huidge min dan was dat dus een goede zet en vervangen we min met evaluation
                if (evaluation < min) {
                    min = evaluation;
                }
                if(evaluation < beta){
                    beta = evaluation;
                }
                if(beta <= alpha){
                    break;
                }
            }
            // Geef de min waarde terug
            return min;
        }
    }

    @Override
    public Integer call() {
        return minimax(board, depth, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
}
