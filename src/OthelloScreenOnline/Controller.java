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
    private Thread thread;
    private OthelloBoard board;
    private AbstractPlayer player1;
    private AbstractPlayer player2;
    private String startPlayer;
    private OthelloScreenOnline.Model model = new OthelloScreenOnline.Model();
    private int DifficultyAI;

    @FXML
    private Label turnLabel;

    @FXML
    private GridPane OthelloGridPane;

    @FXML
    private Label difficultyLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label puntenOpponent;

    @FXML
    private Label puntenPlayer;

    /**
     * Initializes Othello screen...
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        netwerkConnection = NetwerkConnection.getInstance();
        playerData = PlayerData.getInstance();
        usernameLabel.setText(playerData.getUsername());
    }

    /**
     * Exit button is pressed, user gets redirected to main screen...
     */
    @FXML
    void exit(ActionEvent event) throws IOException {
        netwerkConnection.sendMessage("forfeit");
    }

    public void setStartPlayer(String startPlayer){
        this.startPlayer = startPlayer;
    }

    public void setDifficultyAI(String difficultyAI){
        difficultyLabel.setText("Online - AI: " + difficultyAI);
        this.DifficultyAI = model.CalculateDifficulty(difficultyAI);
        start();
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Othello game start against opponent...
     */
    public void start(){
        if (DifficultyAI == 0){
            //Gemiddeld
            System.out.println("De AI staat op gemiddeld");
            player1 = new OthelloComputer2(playerData.getDepthAI(), playerData.getTimeOutTime(),1, 10, 35);

        }else {
            //Moeilijk
            System.out.println("AI staat op moeilijk");
            player1 = new OthelloComputer2(playerData.getDepthAI(), playerData.getTimeOutTime(),1, 10, 35);
        }
        System.out.println(playerData.getDepthAI());

        //dit is de online tegenstander.
        player2 = new OthelloPlayer();
        board = new OthelloBoard();
        board.initializeBoard(64);

        if (startPlayer.equals(playerData.getUsername())){
            System.out.println("Jij mag beginnen");
            player1.setCharacter('Z');
            player2.setCharacter('W');
        }
        else{
            System.out.println("de tegenstander mag beginnen");
            player1.setCharacter('W');
            player2.setCharacter('Z');
        }
        updateBoard();
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
     * if place on the board is viable, do move.
     */
    public void zet() throws IOException {
        int move = player1.doMove(board);
        System.out.println("De zet is: " +move);
        netwerkConnection.sendMessage("move " + move);
        board.placeMove(move, player1.getCharacter());
        turnLabel.setText("De ander is aan zet");
        updateBoard();
    }

    /**
     * @param move place on the board.
     * Opponent makes move, board gets updated.
     */
    public void zetOpenent(int move){
        board.placeMove(move, player2.getCharacter());
        turnLabel.setText("Jij bent aan zet");
        updateBoard();
    }

    /**
     * @param text player.
     * Endscreen.
     */
    public void showEndScreen(String text){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert a1 = new Alert(Alert.AlertType.NONE,
                        text, ButtonType.APPLY);
                a1.showAndWait().ifPresent(buttonType -> {
                    OthelloGridPane.getScene().getWindow().hide();
                    Parent root;
                    try {
                        FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("Lobby/View.fxml"));
                        root = (Parent) loader.load();
                        Lobby.Controller lobby=loader.getController();
                        lobby.setDifficultyAI(model.CalculateStringDifficulty(DifficultyAI));
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

    /**
     * Runs game and checks if game is still running, decides a winner...
     */
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
                                    showEndScreen("Je hebt gewonnen!");
                                    break;
                                case "LOSS":
                                    stopThread();
                                    showEndScreen("Je hebt verloren!");
                                    break;
                                case "DRAW":
                                    stopThread();
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

