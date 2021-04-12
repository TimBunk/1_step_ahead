package OthelloScreenOffline;

import Game.PlayerData;
import Othello.OthelloBoard;
import Othello.OthelloComputer1;
import Othello.OthelloComputer2;
import Othello.OthelloPlayer;
import Shared.AbstractPlayer;
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

public class Controller implements Initializable {
    PlayerData playerData;
    private OthelloBoard board;
    private AbstractPlayer player1;
    private AbstractPlayer player2;
    private OthelloScreenOffline.Model model = new OthelloScreenOffline.Model();
    private int difficulty;
    boolean gameOver;
    boolean player1Passed;
    boolean player2Passed;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerData = PlayerData.getInstance();
        usernameLabel.setText(playerData.getUsername());
    }

    @FXML
    private AnchorPane TicTacToeScreen;

    @FXML
    private Label turnLabel;

    @FXML
    private GridPane TicTacToeGridPane;

    @FXML
    private Label difficultyLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label puntenOpponent;

    @FXML
    private Label puntenPlayer;

    @FXML
    void exit(ActionEvent event) {
        System.out.println("Terug naar main screen");

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

    public void setDifficulty(String difficulty){
        this.difficulty = model.CalculateDifficulty(difficulty);
        difficultyLabel.setText("Computer " + difficulty);
        start();
    }

    public void start(){
        player1 = new OthelloPlayer();
        if (difficulty == 1){
            //moeilijk
            player2 = new OthelloComputer2(8, 9500,1, 5, 3);

        }else {
            //gemiddeld
            player2 = new OthelloComputer1(8, 9500);
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
        boolean player1Turn = (player1.getCharacter()=='Z');
        updateBoard();
        gameOver = false;
        player1Passed = false;
        player2Passed = false;

        if (!player1Turn){
            System.out.println("De computer mag!");
            int move = player2.doMove(board);
            board.placeMove(move, player2.getCharacter());
            updateBoard();
        }



    }

    public void updateBoard(){
        for (int i = 0; i < board.length(); i++){
            if (board.getBoard()[i] != '.'){
                placeOnScreen(i, board.getBoard()[i]);
            }
        }
        puntenOpponent.setText("" +board.count(player2.getCharacter()));
        puntenPlayer.setText("" + board.count(player1.getCharacter()));
    }

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

        TicTacToeGridPane.add(image, column, row);
    }

    @FXML
    void pressedMouseOnBord(MouseEvent event) {
        Node node = (Node) event.getTarget();
        int zet = model.NumberOnBoard(GridPane.getRowIndex(node), GridPane.getColumnIndex(node));
        System.out.println(zet);
        zet(zet);



    }

    public void zet(int zet){
        if (gamePlay()){
            if(board.findValidMoves(player1.getCharacter()).length != 0){
                if (board.isMoveValid(zet, player1.getCharacter())){
                    System.out.println("is valide");
                    board.placeMove(zet, player1.getCharacter());
                    int[] validMoves = board.findValidMoves(player1.getCharacter());
                    for (int moves: validMoves){
                        System.out.println(moves);
                    }
                    updateBoard();
                    zetComputer();
                }
            }
            else {
                System.out.println("player 1 passed");
                player1Passed = true;
                board.increaseTurnCount();
                zetComputer();
            }

            gameOver = board.isGameOver();
        }
        else {
            System.out.println("game afgelopen");
        }

    }

    public void zetComputer(){
        //computer nu een zet doen
        if (gamePlay()){
            if(board.findValidMoves(player2.getCharacter()).length != 0){
                int move = player2.doMove(board);
                board.placeMove(move, player2.getCharacter());
                updateBoard();
            }
            else {
                player2Passed = true;
                board.increaseTurnCount();
            }
            gameOver = board.isGameOver();
        }
        else {
            System.out.println("game is afgelopen");
        }
    }

    public Boolean gamePlay(){
        if (gameOver| (player1Passed && player2Passed)){
            return false;
        }
        else {
            return true;
        }

    }
}

