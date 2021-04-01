package BKE;

import java.util.concurrent.ThreadLocalRandom;

public class BoterKaasEieren {

    public static void main(String[] args) {
        // Start boter kaas en eieren
        BoterKaasEieren BKE = new BoterKaasEieren();
        BKE.start();
    }

    private BKEboard board;
    private BKEplayer player;
    private BKEcomputer computer;

    public static char PLAYERS_CHAR = 'O';
    public static char COMPUTERS_CHAR = 'X';

    BoterKaasEieren() {
        // Initialiseer alle waardes
        board = new BKEboard();
        board.initializeBoard(9);
        player = new BKEplayer();
        computer = new BKEcomputer();
        computer.setDifficulty(1); // Difficulty 0 = random moves, difficulty 1 = minimax
    }

    /**
     * The game loop. This will continue until the board is full or a player wins.
     */
    public void start() {
        // Kies wie er eerst gaat
        boolean playersTurn = false;
        // Genereer een random nummer vanaf 0 tot en met 1
        // Als het nummer 0 is dan mag de speler beginnen anders de computer
        if (ThreadLocalRandom.current().nextInt(0, 2) == 0) {
            playersTurn = true;
            // Ruil X en O om want X mag altijd beginnen
            PLAYERS_CHAR = 'X';
            COMPUTERS_CHAR = 'O';
        }
        // De gameState geeft of iemand heeft gewonnen
        // speler heeft gewonnen -1, computer heeft gewonnen 1
        // Als niemand nog heeft gewonnen krijg je 0
        int gameState = 0;
        int turnCount = 0;
        do {
            // Laat het board zien
            board.printBoard();
            // Print uit de ronde nummer
            System.out.println("Ronde: " + (turnCount+1));
            // Laat de speler of de computer zijn beurt spelen
            if (playersTurn) {
                int move = player.doMove(board);
                board.placeMove(move, PLAYERS_CHAR);
            }
            else {
                int move = computer.doMove(board);
                board.placeMove(move, COMPUTERS_CHAR);
            }
            // Verander wie er aan de beurt is
            playersTurn = !playersTurn;
            // Increment de turnCount
            turnCount++;
            // Check of er iemand heeft gewonnen of dat het maximaal aantal turns zijn bereikt
        } while ((gameState = evaluation(playersTurn)) == 0 && turnCount < board.length());
        // Print nogmaals het bord om het eind resultaat te laten zien
        board.printBoard();
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

    /**
     * @param isComputer Whether the evaluating player is the computer
     * @return How good the board is, evaluated as 1 for victory, -1 for defeat, 0 otherwise.
     */
    private int evaluation(boolean isComputer) {
        // Pak het juiste character om te evalueren op het board
        char c = isComputer ? COMPUTERS_CHAR : PLAYERS_CHAR;
        // Check of die character kan heeft gewonnen
        if (board.doesCharacterWin(c)) {
            return isComputer ? 1 : -1; // Return -1 als de speler wint en 1 als de computer wint
        }
        // Return 0 als het spel nog niet klaar is
        return 0;
    }

}
