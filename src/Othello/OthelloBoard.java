package Othello;

import Shared.AbstractBoard;

import java.util.Arrays;
import java.util.HashSet;

public class OthelloBoard extends AbstractBoard {

    /**
     * @param size The desired size of the board. Since this board is for Othello, this should be 64
     */
    public void initializeBoard(int size) {
        super.initializeBoard(size);
        Arrays.fill(board, '.');
        board[27] = 'W';
        board[28] = 'Z';
        board[35] = 'Z';
        board[36] = 'W';
    }

    /**
     * @return Another instance of the board
     */
    @Override
    public AbstractBoard clone() {
        OthelloBoard b = new OthelloBoard();
        b.board = new char[board.length];
        System.arraycopy(this.board, 0, b.board, 0, board.length);
        return b;
    }

    /**
     * @param move  The move whose validity should be checked
     * @return      Whether the move is valid
     */
    @Override
    public boolean isMoveValid(int move) {
        // De move is valid wanneer die op een lege plaats wordt gedaan
        // Daarnaast moet er door de move minstens 1 steen worden omgedraaid
        if (board[move] == '.') {
            return true;
        }
        return false;
    }

    /**
     * Unsupported for Othello, do not use
     */
    @Override
    public boolean doesCharacterWin(char c) {
        return false;
    }

    /**
     * Prints the state of the board to the screen
     */
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

    /**
     * This function allow you to place a stone on the board and increases the turn count by 1
     * @param move  Where we want to place the move
     * @param c     The character we want to place
     */
    @Override
    public void placeMove(int move, char c) {
        increaseTurnCount();
        board[move] = c;
        turnStones(move, c);
    }

    /**
     * @param characterToMove   The character for which we want to find valid moves
     * @return                  Array of the positions of valid moves
     */
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

    /**
     * @param c The character for which valid moves should be counted
     * @return The amount of valid moves available right now
     */
    @Override
    public int validMoveCount(char c) {
        int validMovesCount = 0;
        for(int i = 0; i < board.length; i++){
            if(isMoveValid(i, c)){
                validMovesCount++;
            }
        }
        return validMovesCount;
    }

    /**
     * @return If there are no free places on the board, the game is over
     */
    @Override
    public boolean isGameOver(){
        for(char position : board){
            if(position == '.'){
                return false;
            }
        }
        return true;
    }

    /**
     * @param move  The position of the move whose validity should be checked
     * @param c     The character for which we want to check the move's validity
     * @return      Whether the move is allowed by the rules of Othello
     */
    public boolean isMoveValid(int move, char c){
        if(!isMoveValid(move)){
            return false;
        }
        return isMoveValidLeft(move, c) || isMoveValidRight(move, c) || isMoveValidUp(move, c) || isMoveValidDown(move, c) || isMoveValidLeftUp(move, c) || isMoveValidRightUp(move, c) || isMoveValidLeftDown(move, c) || isMoveValidRightDown(move, c);
    }

    /**
     * @param position          The position of the move whose validity should be checked
     * @param characterToMove   The character for which we want to check the move's validity
     * @return                  Whether the move is valid because it flips stones to the left
     */
    protected boolean isMoveValidLeft(int position, char characterToMove){
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

    /**
     * @param position          The position of the move whose validity should be checked
     * @param characterToMove   The character for which we want to check the move's validity
     * @return                  Whether the move is valid because it flips stones to the right
     */
    protected boolean isMoveValidRight(int position, char characterToMove){
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

    /**
     * @param position          The position of the move whose validity should be checked
     * @param characterToMove   The character for which we want to check the move's validity
     * @return                  Whether the move is valid because it flips stones to the top
     */
    protected boolean isMoveValidUp(int position, char characterToMove){
        if(position > 15){
            if(board[position-8] != characterToMove && board[position-8] != '.'){
                for(int i = position - 16; i >= 0; i-=8){
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

    /**
     * @param position          The position of the move whose validity should be checked
     * @param characterToMove   The character for which we want to check the move's validity
     * @return                  Whether the move is valid because it flips stones to the bottom
     */
    protected boolean isMoveValidDown(int position, char characterToMove){
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

    /**
     * @param position          The position of the move whose validity should be checked
     * @param characterToMove   The character for which we want to check the move's validity
     * @return                  Whether the move is valid because it flips stones to the upper left
     */
    protected boolean isMoveValidLeftUp(int position, char characterToMove){
        if(position > 15 && position % 8 > 1){
            if(board[position-9] != characterToMove && board[position-9] != '.'){
                for(int i = position - 18; i >= 0; i-=9){
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

    /**
     * @param position          The position of the move whose validity should be checked
     * @param characterToMove   The character for which we want to check the move's validity
     * @return                  Whether the move is valid because it flips stones to the upper right
     */
    protected boolean isMoveValidRightUp(int position, char characterToMove){
        if(position % 8 < 6 && position > 15){
            if(board[position-7] != characterToMove && board[position-7] != '.'){
                for(int i = position - 14; i >= 0; i-=7){
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

    /**
     * @param position          The position of the move whose validity should be checked
     * @param characterToMove   The character for which we want to check the move's validity
     * @return                  Whether the move is valid because it flips stones to the bottom left
     */
    protected boolean isMoveValidLeftDown(int position, char characterToMove){
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

    /**
     * @param position          The position of the move whose validity should be checked
     * @param characterToMove   The character for which we want to check the move's validity
     * @return                  Whether the move is valid because it flips stones to the bottom right
     */
    protected boolean isMoveValidRightDown(int position, char characterToMove){
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

    /**
     * @param position      The position of the stone that was placed
     * @param charToPlace   The character that was placed
     */
    public void turnStones(int position, char charToPlace){
        if(isMoveValidLeft(position, charToPlace)){
            turnStonesLeft(position, charToPlace);
        }
        if(isMoveValidRight(position, charToPlace)){
            turnStonesRight(position, charToPlace);
        }
        if(isMoveValidUp(position, charToPlace)){
            turnStonesUp(position, charToPlace);
        }
        if(isMoveValidDown(position, charToPlace)){
            turnStonesDown(position, charToPlace);
        }
        if(isMoveValidLeftUp(position, charToPlace)){
            turnStonesLeftUp(position, charToPlace);
        }
        if(isMoveValidRightUp(position, charToPlace)){
            turnStonesRightUp(position, charToPlace);
        }
        if(isMoveValidLeftDown(position, charToPlace)){
            turnStonesLeftDown(position, charToPlace);
        }
        if(isMoveValidRightDown(position, charToPlace)){
            turnStonesRightDown(position, charToPlace);
        }
    }

    /**
     * @param position      The position of the stone that was placed
     * @param charToPlace   The character that was placed
     */
    private void turnStonesLeft(int position, char charToPlace){
        for(int i = position - 1; board[i] != charToPlace; i--){
            board[i] = charToPlace;
        }
    }

    /**
     * @param position      The position of the stone that was placed
     * @param charToPlace   The character that was placed
     */
    private void turnStonesRight(int position, char charToPlace){
        for(int i = position + 1; board[i] != charToPlace; i++){
            board[i] = charToPlace;
        }
    }

    /**
     * @param position      The position of the stone that was placed
     * @param charToPlace   The character that was placed
     */
    private void turnStonesUp(int position, char charToPlace){
        for(int i = position - 8; board[i] != charToPlace; i-=8){
            board[i] = charToPlace;
        }
    }

    /**
     * @param position      The position of the stone that was placed
     * @param charToPlace   The character that was placed
     */
    private void turnStonesDown(int position, char charToPlace){
        for(int i = position + 8; board[i] != charToPlace; i+=8){
            board[i] = charToPlace;
        }
    }

    /**
     * @param position      The position of the stone that was placed
     * @param charToPlace   The character that was placed
     */
    private void turnStonesLeftUp(int position, char charToPlace){
        for(int i = position - 9; board[i] != charToPlace; i-=9){
            board[i] = charToPlace;
        }
    }

    /**
     * @param position      The position of the stone that was placed
     * @param charToPlace   The character that was placed
     */
    private void turnStonesRightUp(int position, char charToPlace){
        for(int i = position - 7; board[i] != charToPlace; i-=7){
            board[i] = charToPlace;
        }
    }

    /**
     * @param position      The position of the stone that was placed
     * @param charToPlace   The character that was placed
     */
    private void turnStonesLeftDown(int position, char charToPlace){
        for(int i = position + 7; board[i] != charToPlace; i+=7){
            board[i] = charToPlace;
        }
    }

    /**
     * @param position      The position of the stone that was placed
     * @param charToPlace   The character that was placed
     */
    private void turnStonesRightDown(int position, char charToPlace){
        for(int i = position + 9; board[i] != charToPlace; i+=9){
            board[i] = charToPlace;
        }
    }

    /**
     * @param c The character to count the stables stones for
     * @return The amount of stable stones that the character has on the board
     */
    public int getStableStoneCount(char c) {
        HashSet<Integer> uniqueStableStones = new HashSet<>();
        GetStableStonesFromBottomRight(c, uniqueStableStones);
        GetStableStonesFromTopRight(c, uniqueStableStones);
        GetStableStonesFromTopLeft(c, uniqueStableStones);
        GetStableStonesFromBottomLeft(c, uniqueStableStones);
        return uniqueStableStones.size();
    }

    /**
     * Helper function for the getStableStoneCount(char c)
     * @param c The character to count the stables stones for
     * @param stones A Hashset<Integer> to put the stable stone positions in
     */
    private void GetStableStonesFromTopLeft(char c, HashSet<Integer> stones) {
        int maxSteps1 = 8;
        int maxSteps2 = 8;
        for (int i=0;i<8;i++) {
            // Check van links beneden naar rechts boven
            int pos = i*8;
            int steps = 0;
            do {
                if (steps < maxSteps1 && board[pos] == c) {
                    steps++;
                    stones.add(pos);
                }
                else {
                    maxSteps1 = steps;
                    break;
                }
                pos -= 7;
            } while (pos > 0);
            // Check van rechts boven naar links beneden
            pos = i;
            steps = 0;
            do {
                if (steps < maxSteps2 && board[pos] == c) {
                    steps++;
                    stones.add(pos);
                }
                else {
                    maxSteps2 = steps;
                    break;
                }

                pos += 7;
            } while (pos <= i*8);
        }
    }

    /**
     * Helper function for the getStableStoneCount(char c)
     * @param c The character to count the stables stones for
     * @param stones A Hashset<Integer> to put the stable stone positions in
     */
    private void GetStableStonesFromBottomLeft(char c, HashSet<Integer> stones) {
        int maxSteps1 = 8;
        int maxSteps2 = 8;
        for (int i=0;i<8;i++) {
            // Check van links boven naar rechts beneden
            int pos = 56 - (i*8);
            int steps = 0;
            do {
                if (steps < maxSteps1 && board[pos] == c) {
                    steps++;
                    stones.add(pos);
                }
                else {
                    maxSteps1 = steps;
                    break;
                }
                pos += 9;
            } while (pos < 64);
            // Check van rechts benden naar links boven
            pos = 56 + i;
            steps = 0;
            do {
                if (steps < maxSteps2 && board[pos] == c) {
                    steps++;
                    stones.add(pos);
                }
                else {
                    maxSteps2 = steps;
                    break;
                }

                pos -= 9;
            } while (pos >= (56-(i*8)));
        }
    }

    /**
     * Helper function for the getStableStoneCount(char c)
     * @param c The character to count the stables stones for
     * @param stones A Hashset<Integer> to put the stable stone positions in
     */
    private void GetStableStonesFromTopRight(char c, HashSet<Integer> stones) {
        int maxSteps1 = 8;
        int maxSteps2 = 8;
        for (int i=0;i<8;i++) {
            // Check van links boven naar rechts onder
            int pos = 7-i;
            int steps = 0;
            do {
                if (steps < maxSteps1 && board[pos] == c) {
                    steps++;
                    stones.add(pos);
                }
                else {
                    maxSteps1 = steps;
                    break;
                }
                pos += 9;
            } while (pos < 8+(i*8));
            // Check van rechts onder naar links boven
            pos = 7+(i*8);
            steps = 0;
            do {
                if (steps < maxSteps2 && board[pos] == c) {
                    steps++;
                    stones.add(pos);
                }
                else {
                    maxSteps2 = steps;
                    break;
                }

                pos -= 9;
            } while (pos >= 0);
        }
    }

    /**
     * Helper function for the getStableStoneCount(char c)
     * @param c The character to count the stables stones for
     * @param stones A Hashset<Integer> to put the stable stone positions in
     */
    private void GetStableStonesFromBottomRight(char c, HashSet<Integer> stones) {
        int maxSteps1 = 8;
        int maxSteps2 = 8;
        for (int i=0;i<8;i++) {
            // Check van links onder naar rechts boven
            int pos = 63-i;
            int steps = 0;
            do {
                if (steps < maxSteps1 && board[pos] == c) {
                    steps++;
                    stones.add(pos);
                }
                else {
                    maxSteps1 = steps;
                    break;
                }
                pos -= 7;
            } while (pos >= 63-(i*8));
            // Check van rechts boven naar links onder
            pos = 63-(i*8);
            steps = 0;
            do {
                if (steps < maxSteps2 && board[pos] == c) {
                    steps++;
                    stones.add(pos);
                }
                else {
                    maxSteps2 = steps;
                    break;
                }

                pos += 7;
            } while (pos < 64);
        }
    }
}
