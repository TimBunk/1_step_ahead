import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class BoterKaasEieren {

    public static void main(String[] args) {
        // Start boter kaas en eieren
        BoterKaasEieren BKE = new BoterKaasEieren();
        BKE.start();
    }

    private char board[];
    private char playersCharacter;
    private char computersCharacter;

    BoterKaasEieren() {
        // Initialiseer alle waardes
        playersCharacter = 'O';
        computersCharacter = 'X';
        // '#' geeft aan dat het om een lege tile gaat
        board = new char[9];
        for (int i = 0; i < board.length; i++) {
            board[i] = '#';
        }
    }

    public void start() {
        // Kies wie er eerst gaat
        boolean playersTurn = false;
        // Genereer een random nummer vanaf 0 tot en met 1
        // Als het nummer 0 is dan mag de speler beginnen anders de computer
        if (ThreadLocalRandom.current().nextInt(0, 2) == 0) {
            playersTurn = true;
            playersCharacter = 'X';
            computersCharacter = 'O';
        }
        // De gameState geeft of iemand heeft gewonnen
        // speler heeft gewonnen -1, computer heeft gewonnen 1
        // Als niemand nog heeft gewonnen krijg je 0
        int gameState = 0;
        int turnCount = 0;
        do {
            // Laat het board zien
            printBoard(board);
            // Print uit de ronde nummer
            System.out.println("Ronde: " + (turnCount+1));
            // Laat de speler of de computer zijn beurt spelen
            if (playersTurn) {
                board[playersTurn()] = playersCharacter;
            }
            else {
                board[computersTurn()] = computersCharacter;
            }
            // Verander wie er aan de beurt is
            playersTurn = !playersTurn;
            // Increment de turnCount
            turnCount++;
        } while ((gameState = evaluation(board, playersTurn)) == 0 && turnCount < board.length);
        // Print nogmaals het bord om het eind resultaat te laten zien
        printBoard(board);
        // Print de status van de game
        switch (gameState) {
            case -1: // speler heeft gewonnen
                System.out.println("De speler wint!!");
                break;
            case 1: // computer heeft gewonnen
                System.out.println("De computer wint!!");
                break;
            case 0: // gelijkspel
                System.out.println("Gelijkspel!!");
                break;
        }
    }

    private int playersTurn() {
        Scanner sc= new Scanner(System.in);
        int move = 0;
        do {
            // Lees wat de player intypt
            System.out.print("Voer een nummer van 0 tot en met 8 in: ");
            move = sc.nextInt();
            System.out.println(move);
            // En check of de player een valid move kiest om te spelen anders vraag de speler nogmaals een nummer in te vullen
        } while (isMoveValid(board, move) == false);
        return move;
    }

    private boolean isMoveValid(char[] board, int move) {
        // De move is valid wanneer die 0 is of lager dan de lengte van de board
        // Ook moet er nog niks geplaatst zijn op die tile van het board. '#' geeft een lege tile aan
        if (move >= 0 && move < board.length && board[move] == '#') {
            return true;
        }
        return false;
    }

    private int computersTurn() {
        // De computer maximaliseert
        int bestEvaluation = Integer.MIN_VALUE;
        int bestMove = 0;
        // Loop door alle tiles van het bord
        for (int i = 0; i < board.length; i++) {
            // Kijk of de tile beschikbaar is
            if (isMoveValid(board, i)) {
                // Maak een clone van het board en plaats de move erin
                char[] cloneOfBoard = board.clone();
                cloneOfBoard[i] = computersCharacter;
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

    private int minimax(char[] board_state, int depth, boolean maximizing) {
        int evaluation = maximizing ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        evaluation = 0;
        // Als de evaluation zegt dat er een winnaar is dan geven we de evaluation terug
        // of als de diepte 0 is bereikt of als er geen tiles meer vrij zijn
        if ((evaluation = evaluation(board_state, !maximizing)) != 0 || depth == 0 || anyTilesAvailable(board_state) == false) {
            return evaluation;
        }

        if (maximizing) {
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < board_state.length; i++) {
                // Check of de move valid is
                if (isMoveValid(board_state, i)) {
                    // Maak een clone van het board
                    char[] cloneOfBoard = board_state.clone();
                    // Plaats de move op het board
                    cloneOfBoard[i] = computersCharacter;
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
            for (int i = 0; i < board_state.length; i++) {
                // Check of de move valid is
                if (isMoveValid(board_state, i)) {
                    // Maak een clone van het board
                    char[] cloneOfBoard = board_state.clone();
                    // Plaats de move op het board
                    cloneOfBoard[i] = playersCharacter;
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

    private int evaluation(char[] board, boolean isComputer) {
        // Pak de juiste character om te evaluaren op het board
        char c = isComputer ? computersCharacter : playersCharacter;
        // Check of die character kan heeft gewonnen
        if (doesCharacterWin(board, c)) {
            return isComputer ? 1 : -1; // Return -1 als de speler wint en 1 als de computer wint
        }
        // Return 0 als het spel nog niet klaar is
        return 0;
    }

    private boolean doesCharacterWin(char[] board, char c) {
        // Check alle mogelijke win scenarios
        if (doesCharacterWinHorizontally(board, c) || doesCharacterWinVertically(board, c) || doesCharacterWinDiagonally(board, c)) {
            return true;
        }
        return false;
    }

    private boolean doesCharacterWinHorizontally(char[] board, char c) {
        for (int i = 0; i < 3; i++) {
            boolean win = true;
            for (int j = 0; j < 3; j++) {
                if (board[j * 3 + i] != c) {
                    win = false;
                    break;
                }
            }
            if (win) { return true; }
        }
        return false;
    }

    private boolean doesCharacterWinVertically(char[] board, char c) {
        for (int i = 0; i < 3; i++) {
            boolean win = true;
            for (int j = 0; j < 3; j++) {
                if (board[i * 3 + j] != c) {
                    win = false;
                    break;
                }
            }
            if (win) { return true; }
        }
        return false;
    }

    private boolean doesCharacterWinDiagonally(char[] board, char c) {
        boolean win = true;
        // Check voor een diagonale overwinnen van linksboven naar rechtsonder
        for (int i = 0; i < 3; i++) {
            if (board[i*4] != c) {
                win = false;
                break;
            }
        }
        if (win) { return true; }
        win = true;
        // Check voor een diagonale overwinnen van rechtsboven naar linksonder
        for (int i = 0; i < 3; i++) {
            if (board[2 + i*2] != c) {
                win = false;
                break;
            }
        }
        if (win) { return true; }
        return false;
    }

    private boolean anyTilesAvailable(char[] board) {
        // Zolang er nog '#' op het board staat is het board nog niet leeg
        for (char c : board) {
            if (c == '#') {
                return true;
            }
        }
        return false;
    }

    private void printBoard(char[] board_state) {
        String boardString = "";
        for (int i = 0; i < board_state.length; i++) {
            boardString += board_state[i];
            if ((i+1) % 3 == 0) {
                boardString += "\n";
            }
            else {
                boardString += "-";
            }
        }
        System.out.println(boardString);
    }

}
