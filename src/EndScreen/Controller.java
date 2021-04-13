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

public class Controller implements Initializable{
    @FXML
    private Label statusLabel;

    @FXML
    private ChoiceBox<String> gameDifficultyChoiceBox;

    private AnchorPane beforeScreen;
    private String game;

    /**
     *The user clicks on the again button, game played at the moment will restart...
     */
    @FXML
    void again(ActionEvent event) {
        Parent root;
        try {
            if (game.equals("TicTacToe")){
                FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("TicTacToeScreen/View.fxml"));
                root = (Parent) loader.load();
                TicTacToeScreen.Controller ticTacToeScreen=loader.getController();
                ticTacToeScreen.setdifficulty(gameDifficultyChoiceBox.getValue());
            }else {
                FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("OthelloScreenOffline/View.fxml"));
                root = (Parent) loader.load();
                OthelloScreenOffline.Controller othelloScreenOffline=loader.getController();
                othelloScreenOffline.setDifficulty(gameDifficultyChoiceBox.getValue());
            }


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

    /**
     *The user clicks on the end/leave button, user will be redirected to mainscreen...
     */
    @FXML
    void stop(ActionEvent event) {
        Parent root;
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("MainScreen/View.fxml"));
            root = (Parent) loader.load();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gameDifficultyChoiceBox.getItems().addAll("Gemiddeld", "Moeilijk");
        gameDifficultyChoiceBox.getSelectionModel().select("Gemiddeld");
    }

    public void setBeforeScreen(AnchorPane beforeScreen){
        this.beforeScreen = beforeScreen;
    }

    public void setText(String text){
        statusLabel.setText(text);
    }

    public void setGame(String game) {
        this.game = game;
    }
}
