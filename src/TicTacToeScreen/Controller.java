package TicTacToeScreen;

import BKE.BKEboard;
import BKE.BKEcomputer;
import BKE.BKEplayer;
import Game.PlayerData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Controller {
    private PlayerData player;
    private TicTacToeScreen.Model model = new Model();
    private int Difficulty;

    //van AI team
    private BKEboard board;
    private BKEplayer bkEplayer;
    private BKEcomputer computer;
    public static char PLAYERS_CHAR = 'O';
    public static char COMPUTERS_CHAR = 'X';
    int gameState;
    boolean playersTurn;
    int turnCount;
    @FXML
    private AnchorPane TicTacToeScreen;

    @FXML
    public void start() {
        System.out.println("start...");
        board = new BKEboard();
        board.initializeBoard(9);
        bkEplayer = new BKEplayer();
        computer = new BKEcomputer();
        computer.setDifficulty(Difficulty);
        // Kies wie er eerst gaat
        playersTurn = false;
        // Genereer een random nummer vanaf 0 tot en met 1
        // Als het nummer 0 is dan mag de speler beginnen anders de computer
        if (ThreadLocalRandom.current().nextInt(0, 2) == 0) {
            playersTurn = true;
            // Ruil X en O om want X mag altijd beginnen
            PLAYERS_CHAR = 'X';
            COMPUTERS_CHAR = 'O';
            turnLabel.setText("Aan zet: jij " + PLAYERS_CHAR);
        }else {
            turnLabel.setText("Aan zet: Computer " + COMPUTERS_CHAR);
        }
        // De gameState geeft of iemand heeft gewonnen
        // speler heeft gewonnen -1, computer heeft gewonnen 1
        // Als niemand nog heeft gewonnen krijg je 0
        gameState = 0;
        turnCount = 0;
        //Computer begint met eerste zet als hij aan de beurt is
        if (!playersTurn) {
            zetComputer();
        }
        turnLabel.setText("Aan zet: jij " + PLAYERS_CHAR);
    }

    private int evaluation(boolean isComputer) {
        // Pak de juiste character om te evaluaren op het board
        char c = isComputer ? COMPUTERS_CHAR : PLAYERS_CHAR;
        // Check of die character kan heeft gewonnen
        if (board.doesCharacterWin(c)) {
            return isComputer ? 1 : -1; // Return -1 als de speler wint en 1 als de computer wint
        }
        // Return 0 als het spel nog niet klaar is
        return 0;
    }

    public void zet(int zet) {
            if(gameState ==0){
                // Laat het board zien
                board.printBoard();

                // Print uit de ronde nummer
                placeCharacter(zet, PLAYERS_CHAR);
                board.placeMove(zet, PLAYERS_CHAR);

                turnLabel.setText("Aan zet: computer " + COMPUTERS_CHAR);
                turnCount++;
                board.printBoard();
                playersTurn = false;
            }
        gameState = evaluation(playersTurn);
        if (gameState != 0 || turnCount >= board.length()){
            endScreen();
        }
        else {
            zetComputer();
        }

    }

    public void zetComputer() {
        int move = computer.doMove(board);
        board.placeMove(move, COMPUTERS_CHAR);
        placeCharacter(move, COMPUTERS_CHAR);
        board.printBoard();
        turnCount++;
        turnLabel.setText("Aan zet: jij " + PLAYERS_CHAR);
        playersTurn = true;
        gameState = evaluation(playersTurn);
        if (gameState != 0 || turnCount >= board.length()){
            endScreen();
        }
    }

    @FXML
    private Label usernameLabel;
    @FXML
    private Label difficultyLabel;
    @FXML
    private GridPane TicTacToeGridPane;
    @FXML
    private Label turnLabel;


    public void setPlayer(PlayerData player){
        this.player = player;
        usernameLabel.setText(player.getUsername());
    }

    public void setdifficulty(String difficulty){
        difficultyLabel.setText("Computer " + difficulty);
        this.Difficulty = model.CalculateDifficulty(difficulty);
        start();
    }

    @FXML
    void pressedMouseOnBord(MouseEvent event) {
        Node node = (Node) event.getTarget();
        int move = model.TableToInt(GridPane.getRowIndex(node), GridPane.getColumnIndex(node));
        //checken of de zet valide is:
        if (board.isMoveValid(move)){
            zet(move);
        }
    }

    public void placeCharacter(int Cell, char Char){
        int row;
        int column;
        ImageView image;
        if (Cell <= 2){
            row = 0;
            column = Cell;
        }
        else if (Cell <= 5){
            row = 1;
            column = Cell - 3;
        }
        else {
            row = 2;
            column = Cell - 6;
        }

        if (Char == 'X'){
            image = new ImageView(new Image("File:resources/x.png"));
        }
        else {
            image = new ImageView(new Image("File:resources/o.png"));
        }
        TicTacToeGridPane.add(image, column, row);
    }

    public void endScreen(){
        String tittle = switch (gameState) {
            // speler heeft gewonnen
            case -1 -> "Gefeliciteerd " + player.getUsername() + ", je hebt gewonnen!";
            // computer heeft gewonnen
            case 1 -> "Jammer " + player.getUsername() + ", de computer heeft gewonnen.";
            // gelijkspel
            case 0 -> player.getUsername() + ", je hebt gelijk gespeeld.";
            default -> throw new IllegalStateException("Unexpected value: " + gameState);
        };

        Parent root;
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("EndScreen/View.fxml"));
            root = (Parent) loader.load();

            EndScreen.Controller endScreen=loader.getController();
            endScreen.setPlayerData(player);
            endScreen.setBeforeScreen(TicTacToeScreen);
            endScreen.setText(tittle);
            endScreen.setGame("TicTacToe");

            Stage stage=new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
