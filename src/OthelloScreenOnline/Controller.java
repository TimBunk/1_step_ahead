package OthelloScreenOnline;

import Game.NetwerkConnection;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class Controller extends Thread implements Initializable {

    NetwerkConnection netwerkConnection;
    PlayerData playerData;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private Thread t;
    private OthelloBoard board;
    private AbstractPlayer player1;
    private AbstractPlayer player2;
    private String startPlayer;
    private OthelloScreenOnline.Model model = new OthelloScreenOnline.Model();

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Othelle screen geladen");
        netwerkConnection = NetwerkConnection.getInstance();
        playerData = PlayerData.getInstance();
        usernameLabel.setText(playerData.getUsername());
    }

    @FXML
    void exit(ActionEvent event) throws IOException {
        netwerkConnection.sendMessage("forfeit");
    }

    public void setStartPlayer(String startPlayer){
        this.startPlayer = startPlayer;
        start();
        t = new Thread(this);
        t.start();
    }

    public void start(){
        player1 = new OthelloComputer2(8, playerData.getTimeOutTime(),1, 5, 3);

        //dit is de online tegenstander.
        player2 = new OthelloPlayer();
        board = new OthelloBoard();
        board.initializeBoard(64);

        if (startPlayer.equals(playerData.getUsername())){
            System.out.println("jij mag beginnen");
            player1.setCharacter('Z');
            player2.setCharacter('W');
        }
        else{
            System.out.println("de ander mag beginnen");
            player1.setCharacter('W');
            player2.setCharacter('Z');
        }
        updateBoard();
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

    public void updateBoard(){
        for (int i = 0; i < board.length(); i++){
            if (board.getBoard()[i] != '.'){
                placeOnScreen(i, board.getBoard()[i]);
            }
        }
        puntenOpponent.setText("" +board.count(player2.getCharacter()));
        puntenPlayer.setText("" + board.count(player1.getCharacter()));
    }

    public void zet() throws IOException {
        System.out.println("De zet gaat nu");
        int move = player1.doMove(board);
        System.out.println("De zet is: " +move);
        netwerkConnection.sendMessage("move " + move);
        board.placeMove(move, player1.getCharacter());
        turnLabel.setText("De ander is aan zet");
        updateBoard();
    }

    public void zetOpenent(int move){
        board.placeMove(move, player2.getCharacter());
        turnLabel.setText("Jij bent aan zet");
        updateBoard();
    }

    public void showEndScreen(String text){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert a1 = new Alert(Alert.AlertType.NONE,
                        text, ButtonType.APPLY);
                a1.showAndWait().ifPresent(buttonType -> {
                    TicTacToeGridPane.getScene().getWindow().hide();
                    Parent root;
                    try {
                        FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("Lobby/View.fxml"));
                        root = (Parent) loader.load();
                        Stage stage=new Stage();
                        stage.setScene(new Scene(root));
                        stage.setResizable(false);
                        stage.show();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    public void stopThread() {
        running.set(false);
    }

    @Override
    public void run() {
        running.set(true);
        synchronized (netwerkConnection){
        while (running.get()) {
            try {
                String reveived = netwerkConnection.getMessage();
                if (!reveived.equals("OK")) {
                    System.out.println("data binnen: " + reveived);
                    String firstWord = reveived.split(" ")[1];
                    switch (firstWord) {
                        case "GAME":
                            switch (reveived.split(" ")[2]) {
                                case "YOURTURN":
                                    System.out.println("jij bent aan de beurt");
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                zet();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                    break;
                                case "WIN":
                                    stopThread();
                                    System.out.println("Je hebt gewonnen!!");
                                    showEndScreen("Je hebt gewonnen!");
                                    break;
                                case "LOSS":
                                    stopThread();
                                    System.out.println("Je hebt verloren!!");
                                    showEndScreen("Je hebt verloren!");
                                    break;
                                case "DRAWW":
                                    stopThread();
                                    System.out.println("je hebt gelijk gespeeld.");
                                    showEndScreen("Gelijkspel");
                                    break;
                                case "MOVE":
                                    String movePlayer = reveived.split(" ")[4].replaceAll("\"|\\,", "");
                                    int playermove = Integer.parseInt(reveived.split(" ")[6].replaceAll("\"|\\,", ""));
                                    System.out.println("player: " + movePlayer + " move: " + playermove);
                                    if (!movePlayer.equals(playerData.getUsername())){
                                        System.out.println("de tegenstander heeft gezet");
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                zetOpenent(playermove);
                                            }
                                        });
                                    }
                                    break;
                            }
                    }
                }

                    } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

