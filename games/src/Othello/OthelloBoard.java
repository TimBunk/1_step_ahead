package Othello;

import BKE.BKEboard;
import Shared.AbstractBoard;

import java.util.Arrays;

public class OthelloBoard extends AbstractBoard {
    
    public void initializeBoard(int size) {
        super.initializeBoard(size);
        Arrays.fill(board, '.');
        board[27] = 'Z';
        board[28] = 'W';
        board[35] = 'W';
        board[36] = 'Z';
    }

    public char[] getBoard(){
        return board;
    }

    @Override
    public AbstractBoard clone() {
        OthelloBoard b = new OthelloBoard();
        b.board = new char[board.length];
        b.oldBoard = new char[board.length];
        for (int i = 0; i < board.length; i++) {
            b.board[i] = this.board[i];
        }
        return b;
    }

    @Override
    public boolean isMoveValid(int move) {
        // De move is valid wanneer die op een lege plaats wordt gedaan
        // Daarnaast moet er door de move minstens 1 steen worden omgedraaid
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
    public void printBoard() {
        String boardString = "";
        for (int i = 0; i < board.length; i++) {
            if (board[i] == '.') {
                if (i < 10 && board[i] != 'Z' && board[i] != 'W') {
                    boardString += "0" + i + " ";
                }
                if (i >= 10 && board[i] != 'Z' && board[i] != 'W') {
                    boardString += i + " ";
                }
            }
            else {
                boardString += board[i] + "  ";
            }
            if (i % 8 == 7) {
                boardString += "\n";
            }
        }
        System.out.println(boardString);
    }

    @Override
    public void placeMove(int move, char c) {
        saveBoard();
        board[move] = c;
        turnStones(move, c);
    }

    @Override
    public int[] findValidMoves(char characterToMove) {
        boolean[] moves = new boolean[64];
        int validMovesCount = 0;
        for(int position = 0; position < board.length; position++){
            if(isMoveValid(position, characterToMove)){
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

    @Override
    public boolean isGameOver(){
        for(char position : board){
            if(position == '.'){
                return false;
            }
        }
        return true;
    }

    public boolean isMoveValid(int position, char characterToMove){
        if(!isMoveValid(position)){
            return false;
        }
        return isMoveValidLeft(characterToMove,position) || isMoveValidRight(characterToMove, position) || isMoveValidUp(characterToMove, position) || isMoveValidDown(characterToMove, position) || isMoveValidLeftUp(characterToMove, position) || isMoveValidRightUp(characterToMove, position) || isMoveValidLeftDown(characterToMove, position) || isMoveValidRightDown(characterToMove, position);
    }

    private boolean isMoveValidLeft(char characterToMove, int position){
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

    private boolean isMoveValidRight(char characterToMove, int position){
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

    private boolean isMoveValidUp(char characterToMove, int position){
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

    private boolean isMoveValidDown(char characterToMove, int position){
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

    private boolean isMoveValidLeftUp(char characterToMove, int position){
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

    private boolean isMoveValidRightUp(char characterToMove, int position){
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

    private boolean isMoveValidLeftDown(char characterToMove, int position){
        if(position % 8 > 1 && position < 48){
            if(board[position+7] != characterToMove && board[position+7] != '.'){
                for(int i = position + 14; i < 64; i+=7){
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

    private boolean isMoveValidRightDown(char characterToMove, int position){
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

    public void turnStones(int position, char charToPlace){
        if(isMoveValidLeft(charToPlace, position)){
            turnStonesLeft(position, charToPlace);
        }
        if(isMoveValidRight(charToPlace, position)){
            turnStonesRight(position, charToPlace);
        }
        if(isMoveValidUp(charToPlace, position)){
            turnStonesUp(position, charToPlace);
        }
        if(isMoveValidDown(charToPlace, position)){
            turnStonesDown(position, charToPlace);
        }
        if(isMoveValidLeftUp(charToPlace, position)){
            turnStonesLeftUp(position, charToPlace);
        }
        if(isMoveValidRightUp(charToPlace, position)){
            turnStonesRightUp(position, charToPlace);
        }
        if(isMoveValidLeftDown(charToPlace, position)){
            turnStonesLeftDown(position, charToPlace);
        }
        if(isMoveValidRightDown(charToPlace, position)){
            turnStonesRightDown(position, charToPlace);
        }
    }

    private void turnStonesLeft(int position, char charToPlace){
        for(int i = position - 1; board[i] != charToPlace; i--){
            board[i] = charToPlace;
        }
    }

    private void turnStonesRight(int position, char charToPlace){
        for(int i = position + 1; board[i] != charToPlace; i++){
            board[i] = charToPlace;
        }
    }

    private void turnStonesUp(int position, char charToPlace){
        for(int i = position - 8; board[i] != charToPlace; i-=8){
            board[i] = charToPlace;
        }
    }

    private void turnStonesDown(int position, char charToPlace){
        for(int i = position + 8; board[i] != charToPlace; i+=8){
            board[i] = charToPlace;
        }
    }

    private void turnStonesLeftUp(int position, char charToPlace){
        for(int i = position - 9; board[i] != charToPlace; i-=9){
            board[i] = charToPlace;
        }
    }

    private void turnStonesRightUp(int position, char charToPlace){
        for(int i = position - 7; board[i] != charToPlace; i-=7){
            board[i] = charToPlace;
        }
    }

    private void turnStonesLeftDown(int position, char charToPlace){
        for(int i = position + 7; board[i] != charToPlace; i+=7){
            board[i] = charToPlace;
        }
    }

    private void turnStonesRightDown(int position, char charToPlace){
        for(int i = position + 9; board[i] != charToPlace; i+=9){
            board[i] = charToPlace;
        }
    }
}
