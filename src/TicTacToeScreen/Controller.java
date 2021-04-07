package TicTacToeScreen;

import BKE.BKEboard;
import BKE.BKEcomputer;
import BKE.BKEplayer;
import Game.PlayerData;
import Shared.AbstractPlayer;
import Shared.RandomComputer;
import javafx.event.ActionEvent;
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

public class Controller extends Thread{
    private PlayerData player;
    private TicTacToeScreen.Model model = new Model();
    private int Difficulty;

    //van AI team
    private BKEboard board;
    private AbstractPlayer player1;
    private AbstractPlayer player2;



    private BKEplayer bkEplayer;
    private AbstractPlayer computer;
    int gameState;
    boolean player1Turn;
    int turnCount;
    @FXML
    private AnchorPane TicTacToeScreen;


    @FXML
    public void start() {
        System.out.println("start...");
        board = new BKEboard();

        board.initializeBoard(9);


        if (Difficulty == 1){
            computer = new BKEcomputer();
        }
        else{
            computer = new RandomComputer();
        }
        bkEplayer = new BKEplayer();
        player1 = bkEplayer;
        player1.setCharacter('X');
        player2 = computer;
        player2.setCharacter('O');

        // Kies wie er eerst gaat
        player1Turn = true;
        // Genereer een random nummer vanaf 0 tot en met 1
        // Als het nummer 0 is dan mag de speler beginnen anders de computer
        if (ThreadLocalRandom.current().nextInt(0, 2) == 0) {
            player1Turn = false;
            // Ruil X en O om want X mag altijd beginnen
            player1.setCharacter('O');
            player2.setCharacter('X');
            turnLabel.setText("Aan zet: Computer " + player2.getCharacter());
            zetComputer();
            turnLabel.setText("Aan zet: jij " + player1.getCharacter());
        }else {
            turnLabel.setText("Aan zet: jij " + player1.getCharacter());
        }
        // De gameState geeft of iemand heeft gewonnen
        // speler heeft gewonnen -1, computer heeft gewonnen 1
        // Als niemand nog heeft gewonnen krijg je 0
        gameState = 0;
        turnCount = 0;
    }

    private int evaluation(boolean isComputer) {
        // Pak de juiste character om te evaluaren op het board
        char c = isComputer ? player2.getCharacter() : player1.getCharacter();
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
                placeCharacter(zet, player1.getCharacter());
                board.placeMove(zet, player1.getCharacter());

                turnLabel.setText("Aan zet: computer " + player2.getCharacter());
                turnCount++;
                board.printBoard();
                player1Turn = false;
            }
        gameState = evaluation(player1Turn);
        if (gameState != 0 || board.getTurnCount() > board.length()){
            endScreen();
        }
        else {
            zetComputer();
        }

    }

    public void zetComputer() {
        int move = computer.doMove(board);
        board.placeMove(move, player2.getCharacter());
        placeCharacter(move, player2.getCharacter());
        board.printBoard();
        turnCount++;
        turnLabel.setText("Aan zet: jij " + player1.getCharacter());
        player1Turn = true;
        gameState = evaluation(player1Turn);
        if (gameState != 0 || board.getTurnCount() > board.length()){
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
            image = new ImageView(new Image("File:resources/kruisje.png"));
            System.out.println(image.getX());
        }
        else {
            image = new ImageView(new Image("File:resources/rondje.png"));
        }
        TicTacToeGridPane.add(image, column, row);
    }



    public void endScreen(){
        String tittle = switch (gameState) {
            // speler heeft gewonnen
            case -1 -> "Gefeliciteerd " + player.getUsername() + ", je hebt gewonnen!";
            // computer heeft gewonnen
            case 1 -> player.getUsername() + ", de computer heeft gewonnen.";
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
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }



    }

    @FXML
    void exit(ActionEvent event) {
        System.out.println("Terug naar main screen");
        Parent root;
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("MainScreen/View.fxml"));
            root = (Parent) loader.load();
            MainScreen.Controller mainScreen=loader.getController();
            mainScreen.setPlayer(player);


            Stage stage=new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();

            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
