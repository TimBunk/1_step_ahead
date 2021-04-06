package BKE;

import Shared.AbstractPlayer;

import java.util.concurrent.ThreadLocalRandom;

public class BoterKaasEieren {

    public static void main(String[] args) {
        // Start boter kaas en eieren
        BoterKaasEieren BKE = new BoterKaasEieren(new BKEplayer(), new BKEcomputer());
        BKE.start();
    }

    private BKEboard board;
    private AbstractPlayer player1;
    private AbstractPlayer player2;

    BoterKaasEieren(AbstractPlayer player1, AbstractPlayer player2) {
        // Initialiseer alle waardes
        board = new BKEboard();
        board.initializeBoard(9);
        this.player1 = player1;
        this.player1.setCharacter('X');
        this.player2 = player2;
        this.player2.setCharacter('O');
    }

    /**
     * The game loop. This will continue until the board is full or a player wins.
     */
    public void start() {
        // Kies wie er eerst gaat
        boolean player1Turn = true;
        // Genereer een random nummer vanaf 0 tot en met 1
        // Als het nummer 0 is dan mag de speler2 beginnen anders speler1
        if (ThreadLocalRandom.current().nextInt(0, 2) == 0) {
            player1Turn = false;
            // Ruil X en O om want X mag altijd beginnen
            player1.setCharacter('O');
            player2.setCharacter('X');
        }
        // De gameState geeft of iemand heeft gewonnen
        // speler heeft gewonnen -1, computer heeft gewonnen 1
        // Als niemand nog heeft gewonnen krijg je 0
        int gameState = 0;
        do {
            // Laat het board zien
            board.printBoard();
            // Print uit de ronde nummer
            System.out.println("Ronde: " + board.getTurnCount());
            // Laat de speler of de computer zijn beurt spelen
            if (player1Turn) {
                int move = player1.doMove(board);
                board.placeMove(move, player1.getCharacter());
            }
            else {
                int move = player2.doMove(board);
                board.placeMove(move, player2.getCharacter());
            }
            // Verander wie er aan de beurt is
            player1Turn = !player1Turn;
            // Check of er iemand heeft gewonnen of dat het maximaal aantal turns zijn bereikt
        } while ((gameState = getWinner()) == 0 && board.getTurnCount() <= board.length());
        // Print nogmaals het bord om het eind resultaat te laten zien
        board.printBoard();
        // Print de status van de game
        switch (gameState) {
            case 1: // speler1 heeft gewonnen
                System.out.println("Speler1 wint!!");
                break;
            case -1: // speler2 heeft gewonnen
                System.out.println("Speler2 wint!!");
                break;
            case 0: // gelijkspel
                System.out.println("Gelijkspel!!");
                break;
        }
    }

    /**
     * @return Returns 1 if player1 won, -1 if player2 won, 0 if there is no winner yet
     */
    private int getWinner() {
        // Check of die character kan heeft gewonnen
        if (board.doesCharacterWin(player1.getCharacter())) {
            return 1;
        }
        else if (board.doesCharacterWin(player2.getCharacter())) {
            return -1;
        }
        // Return 0 als het spel nog niet klaar is
        return 0;
    }

}
