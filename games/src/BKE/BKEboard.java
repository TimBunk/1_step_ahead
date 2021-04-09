package BKE;

import Othello.OthelloBoard;
import Shared.AbstractBoard;

import java.util.ArrayList;

public class BKEboard extends AbstractBoard {

    /**
     * @param size The desired size of the board. Since this board is for Tic-Tac-Toe, this should be 9.
     */
    public void initializeBoard(int size) {
        super.initializeBoard(size);
        for (int i = 0; i < size; i++) {
            board[i] = '#';
        }
    }

    /**
     * @return Another instance of the board
     */
    @Override
    public AbstractBoard clone() {
        BKEboard b = new BKEboard();
        b.board = new char[board.length];
        System.arraycopy(this.board, 0, b.board, 0, board.length);
        return b;
    }

    /**
     * @param move The move whose validity should be checked
     * @return Whether the move is valid
     */
    @Override
    public boolean isMoveValid(int move) {
        // De move is valid wanneer die 0 is of lager dan de lengte van de board
        // Ook moet er nog niks geplaatst zijn op die tile van het board. '#' geeft een lege tile aan
        if (move >= 0 && move < board.length && board[move] == '#') {
            return true;
        }
        return false;
    }

    /**
     * @return If there are no free places on the board then return true
     */
    @Override
    public boolean isGameOver() {
        // Zolang er nog '#' op het board staat is het board nog niet leeg
        for (char c : board) {
            if (c == '#') {
                return false;
            }
        }
        return true;
    }

    /**
     * Prints the state of the board to the screen
     */
    @Override
    public void printBoard() {
        String boardString = "";
        for (int i = 0; i < board.length; i++) {
            boardString += board[i];
            if ((i+1) % 3 == 0) {
                boardString += "\n";
            }
            else {
                boardString += "-";
            }
        }
        System.out.println(boardString);
    }

    /**
     * @param move Where the move is to be placed
     * @param c    The character to be placed
     */
    @Override
    public void placeMove(int move, char c) {
        increaseTurnCount();
        board[move] = c;
    }

    /**
     * @param c Check for all valid moves of a given character
     * @return Returns a int[] of all the valid moves
     */
    @Override
    public int[] findValidMoves(char c) {
        ArrayList<Integer> validMoves = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            if (board[i] == '#') {
                validMoves.add(i);
            }
        }
        return validMoves.stream().mapToInt(Integer::intValue).toArray();
    }

    @Override
    public int validMoveCount(char c) {
        return length() - count('#');
    }

    /**
     * @param c The character to be checked
     * @return Whether the player using that character has won
     */
    @Override
    public boolean doesCharacterWin(char c) {
        // Check alle mogelijke win scenarios
        if (doesCharacterWinHorizontally(c) || doesCharacterWinVertically(c) || doesCharacterWinDiagonally(c)) {
            return true;
        }
        return false;
    }

    /**
     * @param c The character to be checked
     * @return Whether the player using that character has won horizontally
     */
    private boolean doesCharacterWinHorizontally(char c) {
        for(int i = 0; i < board.length; i += 3){
            if(board[i] == board[i+1] && board[i+1] == board[i+2] && board[i] == c){
                return true;
            }
        }
        return false;
    }

    /**
     * @param c The character to be checked
     * @return Whether the player using that character has won vertically
     */
    private boolean doesCharacterWinVertically(char c) {
        for(int i = 0; i < 3; i++){
            if(board[i] == board[i+3] && board[i+3] == board[i+6] && board[i+6] == c){
                return true;
            }
        }
        return false;
    }

    /**
     * @param c The character to be checked
     * @return Whether the player using that character has won diagonally
     */
    private boolean doesCharacterWinDiagonally(char c) {
        if(board[0] == board[4] && board[4] == board[8] && board[8] == c){
            return true;
        }
        if(board[2] == board[4] && board[4] == board[6] && board[6] == c){
            return true;
        }
        return false;
    }
}
