package BKE;

import Shared.AbstractBoard;

public class BKEboard extends AbstractBoard {

    public void initializeBoard(int size) {
        super.initializeBoard(size);
        for (int i = 0; i < size; i++) {
            board[i] = '#';
        }
    }

    @Override
    public AbstractBoard clone() {
        BKEboard b = new BKEboard();
        b.board = new char[board.length];
        for (int i = 0; i < board.length; i++) {
             b.board[i] = this.board[i];
        }
        return b;
    }

    @Override
    public boolean isMoveValid(int move) {
        // De move is valid wanneer die 0 is of lager dan de lengte van de board
        // Ook moet er nog niks geplaatst zijn op die tile van het board. '#' geeft een lege tile aan
        if (move >= 0 && move < board.length && board[move] == '#') {
            return true;
        }
        return false;
    }

    @Override
    public boolean isMoveValid(int move, char c) {
        // Of een move valid is hangt niet af van het character dat je wilt plaatsen
        return isMoveValid(move);
    }

    @Override
    public boolean anyTilesAvailable() {
        // Zolang er nog '#' op het board staat is het board nog niet leeg
        for (char c : board) {
            if (c == '#') {
                return true;
            }
        }
        return false;
    }

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

    @Override
    public void placeMove(int move, char c) {
        board[move] = c;
    }

    @Override
    public int[] findValidMoves(char c) {
        return new int[0];
    }

    @Override
    public boolean doesCharacterWin(char c) {
        // Check alle mogelijke win scenarios
        if (doesCharacterWinHorizontally(c) || doesCharacterWinVertically(c) || doesCharacterWinDiagonally(c)) {
            return true;
        }
        return false;
    }

    private boolean doesCharacterWinHorizontally(char c) {
        for(int i = 0; i < board.length; i += 3){
            if(board[i] == board[i+1] && board[i+1] == board[i+2] && board[i] == c){
                return true;
            }
        }
        return false;
    }

    private boolean doesCharacterWinVertically(char c) {
        for(int i = 0; i < 3; i++){
            if(board[i] == board[i+3] && board[i+3] == board[i+6] && board[i+6] == c){
                return true;
            }
        }
        return false;
    }

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
