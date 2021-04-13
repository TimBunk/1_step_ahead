package OthelloScreenOffline;

import Game.PlayerData;
import Othello.OthelloBoard;
import Othello.OthelloComputer1;
import Othello.OthelloComputer2;
import Othello.OthelloPlayer;
import Shared.AbstractPlayer;
import javafx.application.Platform;
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
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class Controller extends Thread implements Initializable {
    PlayerData playerData;
    private OthelloBoard board;
    private Thread thread;
    private AbstractPlayer player1;
    private AbstractPlayer player2;
    private OthelloScreenOffline.Model model = new OthelloScreenOffline.Model();
    private final AtomicBoolean running = new AtomicBoolean(false);
    private int difficulty;
    private boolean gameOver;
    private boolean player1Passed;
    private boolean player2Passed;
    private boolean player1Turn;

    /**
     * Initializes Othello screen...
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerData = PlayerData.getInstance();
        usernameLabel.setText(playerData.getUsername());
    }

    @FXML
    private Label turnLabel;

    @FXML
    private GridPane OthelloGridPane;

    @FXML
    private AnchorPane OthelloScreen;

    @FXML
    private Label difficultyLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label puntenOpponent;

    @FXML
    private Label puntenPlayer;

    /**
     * Exit button is pressed, user gets redirected to main screen...
     */
    @FXML
    void exit(ActionEvent event) {
        stopThread();
        Parent root;
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("MainScreen/View.fxml"));
            root = (Parent) loader.load();
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

    /**
     * @param event mouse clicked.
     * Makes a move if the mouseplacement is correct on the othello board.
     */
    @FXML
    void pressedMouseOnBord(MouseEvent event) {
        Node node = (Node) event.getTarget();
        int zet = model.NumberOnBoard(GridPane.getRowIndex(node), GridPane.getColumnIndex(node));
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                zet(zet);
            }
        });
    }

    /**
     * @param difficulty diffuculty of the computer
     * Sets difficulty of the computer and starts game.
     */
    public void setDifficulty(String difficulty){
        this.difficulty = model.CalculateDifficulty(difficulty);
        difficultyLabel.setText("Computer " + difficulty);
        start();
    }


    /**
     * Othello game start against computer...
     */
    public void start(){
        player1 = new OthelloPlayer();
        if (difficulty == 1){
            //moeilijk
            player2 = new OthelloComputer2(8, playerData.getTimeOutTime(),1, 5, 3);

        }else {
            //gemiddeld
            player2 = new OthelloComputer1(8, playerData.getTimeOutTime());
        }

        board = new OthelloBoard();
        board.initializeBoard(64);

        if (new Random().nextInt(2) == 0) {
            player1.setCharacter('W');
            player2.setCharacter('Z');
        }
        else {
            player1.setCharacter('Z');
            player2.setCharacter('W');
        }
        player1Turn = (player1.getCharacter() == 'Z');
        gameOver = false;
        player1Passed = false;
        player2Passed = false;

        if (!player1Turn) {
            turnLabel.setText("De computer is aan zet");
        } else {
            turnLabel.setText("Jij bent aan zet");
        }
        updateBoard();
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Keeps updating the Othello board.
     */
    public void updateBoard(){
        for (int i = 0; i < board.length(); i++){
            if (board.getBoard()[i] != '.'){
                placeOnScreen(i, board.getBoard()[i]);
            }
        }
        puntenOpponent.setText("" +board.count(player2.getCharacter()));
        puntenPlayer.setText("" + board.count(player1.getCharacter()));
    }

    /**
     * @param Place which place on the board.
     * @param C white/black circle.
     * Image of black/white circle gets added on the board
     */
    public void placeOnScreen(int Place, char C){
        int row = 0;
        ImageView image;
        for (int i = 0; i < Place; i++){
            if (i % 8 ==7){
                row++;
            }
        }
        int column = Place - (row*8);
        if (C == 'Z'){
            image = new ImageView(new Image("File:resources/black.png"));
        }
        else{
            image = new ImageView(new Image("File:resources/white.png"));
        }
        OthelloGridPane.add(image, column, row);
    }

    /**
     * @param zet place on the board.
     * if place on the board is viable, do move.
     */
    public void zet(int zet){
        if (player1Turn){
            if(board.findValidMoves(player1.getCharacter()).length != 0){
                if (board.isMoveValid(zet, player1.getCharacter())){
                    board.placeMove(zet, player1.getCharacter());
                    turnLabel.setText("De computer is aan zet");
                    updateBoard();
                    player1Turn = false;
                }
            }
            else {
                player1Passed = true;
                board.increaseTurnCount();
                player1Turn = false;
            }
            gameOver = board.isGameOver();
            if (board.findValidMoves(player2.getCharacter()).length == 0){
                player1Passed = true;
                player1Turn = true;
            }
        }
    }

    /**
     * Computer makes move, board gets updated.
     */
    public void zetComputer() {
        if (!player1Turn){
            System.out.println("De computer mag");
            if (board.findValidMoves(player2.getCharacter()).length != 0) {
                int move = player2.doMove(board);
                board.placeMove(move, player2.getCharacter());
                player1Turn = true;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        turnLabel.setText("Jij bent aan de beurt");
                        updateBoard();
                    }
                });
            } else {
                player2Passed = true;
                board.increaseTurnCount();
                player1Turn = true;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        turnLabel.setText("Jij bent aan de beurt");
                    }
                });
            }
            gameOver = board.isGameOver();
            if (board.findValidMoves(player1.getCharacter()).length == 0){
                player2Passed = true;
                player1Turn = false;
            }
        }
    }

    public Boolean gamePlay() {
        if (gameOver | (board.findValidMoves(player1.getCharacter()).length == 0 && board.findValidMoves(player2.getCharacter()).length == 0)) {
            return false;
        } else {
            return true;
        }
    }

    public void stopThread() {
        running.set(false);
    }

    /**
     * Compares points and decides a winner...
     */
    public void winnaar(){
        int player1Points = board.count(player1.getCharacter());
        int player2Points = board.count(player2.getCharacter());
        String tittle;

        if(player1Points > player2Points){
            tittle = "Je wint met " + player1Points + " tegen " + player2Points;
        }
        else if(player2Points > player1Points){
            tittle = "Computer wint met " + player2Points + " tegen " + player1Points;
        }
        else {
            tittle = "Gelijkspel met een score van " + player1Points;
        }
        Parent root;
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("EndScreen/View.fxml"));
            root = (Parent) loader.load();

            EndScreen.Controller endScreen=loader.getController();
            endScreen.setBeforeScreen(OthelloScreen);
            endScreen.setText(tittle);
            endScreen.setGame("Othello");

            Stage stage=new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Runs game and checks if game is still running...
     */
    @Override
    public void run() {
        running.set(true);
        synchronized (playerData) {
            while (running.get()) {
                if (gamePlay()) {
                    if (!player1Turn) {
                        zetComputer();

                    } else {

                    }
                } else {
                    //spel is afgelopen
                    stopThread();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            winnaar();
                        }
                    });
                }
            }
        }
    }
}

