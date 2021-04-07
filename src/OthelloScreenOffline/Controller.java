package OthelloScreenOffline;

import Game.NetwerkConnection;
import Game.PlayerData;
import Othello.OthelloBoard;
import Othello.OthelloComputer1;
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
    void Exit() {
        start();
        System.out.println("test");
        updateBoard();

    }

    public void setDifficulty(){

    }

    public void start(){
        player1 = new OthelloPlayer();
        player2 = new OthelloComputer1(8, 9500);
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

        boolean gameOver = false;
        boolean player1Passed = false;
        boolean player2Passed = false;



    }

    public void updateBoard(){

        for (int i = 0; i < board.length(); i++){
            System.out.println(i + " is: " + board.getBoard()[i]);
        }
    }

    public void placeOnScreen(int Place, char C){
        int row = 0;
        //for (int i = 0;)
    }
}

