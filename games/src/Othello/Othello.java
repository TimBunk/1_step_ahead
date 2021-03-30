import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Othello {

    public static void main(String[] args) {
        Othello othello = new Othello();
        othello.start();
    }

    private char[] board = new char[64];
    private char playerCharacter;
    private char computerCharacter;
    private final int PLAYER_NUMBER = 1;
    private final int COMPUTER_NUMBER = 2;
    Random rand = new Random();

    public Othello(){
        //Bepaal wie Witte en Zwarte stenen krijgt
        if(rand.nextInt(2) == 0){
            playerCharacter = 'W';
            computerCharacter = 'Z';
        }
        else{
            playerCharacter = 'Z';
            computerCharacter = 'W';
        }
        //Zet het bord vol met '.'
        Arrays.fill(board, '.');
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

    private boolean isGameOver(){
        for(char position : board){
            if(position == '.'){
                return false;
            }
        }
        return true;
    }

    private int eval(char[] board){
        //evaluate to what degree the board is in favor of the computer
        int playerStones = 0;
        int computerStones = 0;
        for(char position : board){
            if(position == computerCharacter){
                computerStones++;
            }
            else if(position == playerCharacter){
                playerStones++;
            }
        }
        return computerStones - playerStones;
    }

    private void placeMove(int position, int player){
        char charToPlace;
        if(player == PLAYER_NUMBER){
            charToPlace = playerCharacter;
        }
        else if(player == COMPUTER_NUMBER){
            charToPlace = computerCharacter;
        }
        else{
            return;
        }
        board[position] = charToPlace;
    }

    private void printBoard(char[] board) {
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

    private int playersTurn() {
        Scanner sc= new Scanner(System.in);
        int move;
        do {
            // Lees wat de player intypt
            System.out.print("Voer een nummer van 0 tot en met 63 in: ");
            move = sc.nextInt();
            System.out.println(move);
            // En check of de player een valid move kiest om te spelen anders vraag de speler nogmaals een nummer in te vullen
        } while (!isMoveValid(board, move));
        return move;
    }

    private boolean isMoveValid(char[] board, int move) {
        // De move is valid wanneer die 0 is of lager dan de lengte van de board
        // Ook moet er nog niks geplaatst zijn op die tile van het board. '#' geeft een lege tile aan
        if (move >= 0 && move < board.length && board[move] == '.') {
            return true;
        }
        return false;
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
