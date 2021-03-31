package Othello;

import BKE.BKEboard;
import Shared.AbstractBoard;

import java.util.Arrays;

public class OthelloBoard extends AbstractBoard {

    public void initializeBoard(int size) {
        super.initializeBoard(size);
        Arrays.fill(board, '.');
    }

    @Override
    public AbstractBoard clone() {
        BKEboard b = new BKEboard();
        board = new char[board.length];
        for (int i = 0; i < board.length; i++) {
            board[i] = this.board[i];
        }
        return b;
    }

    @Override
    public boolean isMoveValid(int move) {
        // De move is valid wanneer die 0 is of lager dan de lengte van de board
        // Ook moet er nog niks geplaatst zijn op die tile van het board. '#' geeft een lege tile aan
        if (move >= 0 && move < board.length && board[move] == '.') {
            return true;
        }
        return false;
    }

    @Override
    public boolean doesCharacterWin(char c) {
        return false;
    }

    @Override
    public boolean anyTilesAvailable() {
        return false;
    }

    @Override
    public void printBoard() {
        String boardString = "";
        for (int i = 0; i < board.length; i++) {
            boardString += board[i];
            if (i % 8 == 7) {
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

    private boolean isGameOver(){
        for(char position : board){
            if(position == '.'){
                return false;
            }
        }
        return true;
    }

    private int[] findValidMoves(char characterToMove){
        boolean[] moves = new boolean[64];
        int validMovesCount = 0;
        for(int position : board){
            if(isValidMove(characterToMove, position)){
                moves[position] = true;
                validMovesCount++;
            }
            else{
                moves[position] = false;
            }
        }
        int[] validMoves = new int[validMovesCount];
        int count = 0;
        for(int i = 0; i < 64; i++){
            if(moves[i]){
                validMoves[count] = i;
                count++;
            }
        }
        return validMoves;
    }

    private boolean isValidMove(char characterToMove, int position){
        return isValidMoveLeft(characterToMove,position) || isValidMoveRight(characterToMove, position) || isValidMoveUp(characterToMove, position) || isValidMoveDown(characterToMove, position) || isValidMoveLeftUp(characterToMove, position) || isValidMoveRightUp(characterToMove, position) || isValidMoveLeftDown(characterToMove, position) || isValidMoveRightDown(characterToMove, position);
    }

    private boolean isValidMoveLeft(char characterToMove, int position){
        if(position % 8 > 1){
            if(board[position-1] != characterToMove && board[position-1] != '.'){
                for(int i = position - 2; i >= position / 8 * 8; i--){
                    if(board[i] == '.'){
                        break;
                    }
                    if(board[i] == characterToMove){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isValidMoveRight(char characterToMove, int position){
        if(position % 8 < 6){
            if(board[position+1] != characterToMove && board[position+1] != '.'){
                for(int i = position + 2; i < ((position/8)+1)*8; i++){
                    if(board[i] == '.'){
                        break;
                    }
                    if(board[i] == characterToMove){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isValidMoveUp(char characterToMove, int position){
        if(position > 15){
            if(board[position-8] != characterToMove && board[position-8] != '.'){
                for(int i = position - 16; i > 0; i-=8){
                    if(board[i] == '.'){
                        break;
                    }
                    if(board[i] == characterToMove){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isValidMoveDown(char characterToMove, int position){
        if(position < 48){
            if(board[position+8] != characterToMove && board[position+8] != '.'){
                for(int i = position + 16; i < 64; i+=8){
                    if(board[i] == '.'){
                        break;
                    }
                    if(board[i] == characterToMove){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isValidMoveLeftUp(char characterToMove, int position){
        if(position > 17){
            if(board[position-9] != characterToMove && board[position-9] != '.'){
                for(int i = position - 18; i > 0; i-=9){
                    if(board[i] == '.'){
                        break;
                    }
                    if(board[i] == characterToMove){
                        return true;
                    }
                    if(i % 8 == 0){
                        break;
                    }
                }
            }
        }
        return false;
    }

    private boolean isValidMoveRightUp(char characterToMove, int position){
        if(position % 8 < 6 && position > 15){
            if(board[position-7] != characterToMove && board[position-7] != '.'){
                for(int i = position - 14; i > 0; i-=7){
                    if(board[i] == '.'){
                        break;
                    }
                    if(board[i] == characterToMove){
                        return true;
                    }
                    if(i % 8 == 7){
                        break;
                    }
                }
            }
        }
        return false;
    }

    private boolean isValidMoveLeftDown(char characterToMove, int position){
        if(position % 8 > 1 && position < 48){
            if(board[position+7] != characterToMove && board[position+7] != '.'){
                for(int i = position + 14; i < 64; i++){
                    if(board[i] == '.'){
                        break;
                    }
                    if(board[i] == characterToMove){
                        return true;
                    }
                    if(i % 8 == 0){
                        break;
                    }
                }
            }
        }
        return false;
    }

    private boolean isValidMoveRightDown(char characterToMove, int position){
        if(position % 8 < 6 && position < 48){
            if (board[position+9] != characterToMove && board[position+9] != '.'){
                for(int i = position + 18; i < 64; i+=9){
                    if(board[i] == '.'){
                        break;
                    }
                    if(board[i] == characterToMove){
                        return true;
                    }
                    if(i % 8 == 7){
                        break;
                    }
                }
            }
        }
        return false;
    }
}
