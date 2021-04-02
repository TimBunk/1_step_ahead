package EndScreen;

import Game.PlayerData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller{
    @FXML
    private Label statusLabel;

    @FXML
    private ChoiceBox<String> gameDifficulty;

    private PlayerData playerData;
    private AnchorPane beforeScreen;
    private String game;

    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
    }

    public void setBeforeScreen(AnchorPane beforeScreen){
        this.beforeScreen = beforeScreen;
    }

    public void setText(String text){
        statusLabel.setText(text);
    }

    public void setGame(String game){
        this.game = game;
        setGameDifficultyChoiceBox();
    }

    @FXML
    void again(ActionEvent event) {
        Parent root;
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("TicTacToeScreen/View.fxml"));
            root = (Parent) loader.load();
            TicTacToeScreen.Controller ticTacToeScreen=loader.getController();
            ticTacToeScreen.setPlayer(playerData);
            ticTacToeScreen.setdifficulty(gameDifficulty.getValue());

            Stage stage=new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();

            beforeScreen.getScene().getWindow().hide();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void stop(ActionEvent event) {

        Parent root;
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("MainScreen/View.fxml"));
            root = (Parent) loader.load();
            MainScreen.Controller mainScreen=loader.getController();
            mainScreen.setPlayer(playerData);


            Stage stage=new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();

            beforeScreen.getScene().getWindow().hide();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setGameDifficultyChoiceBox(){
        gameDifficulty.getItems().addAll("Gemiddeld", "Moeilijk");
        gameDifficulty.getSelectionModel().select("Gemiddeld");
    }
}
