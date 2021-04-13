package MainScreen;

import Game.NetwerkConnection;
import Game.PlayerData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private PlayerData playerData;

    @FXML
    private Label usernameLabel;

    @FXML
    private ChoiceBox<String> OthelloDifficulty;

    @FXML
    private ChoiceBox<String> OthelloOpponent;

    @FXML
    private ChoiceBox<String> TicTacToeDifficulty;

    /**
     *The main screen gets initialized...
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerData = PlayerData.getInstance();
        usernameLabel.setText(playerData.getUsername());
        OthelloDifficulty.getItems().addAll("Gemiddeld", "Moeilijk");
        OthelloDifficulty.getSelectionModel().select("Moeilijk");
        TicTacToeDifficulty.getItems().addAll("Gemiddeld", "Moeilijk");
        TicTacToeDifficulty.getSelectionModel().select("Gemiddeld");
        OthelloOpponent.getItems().addAll("Tegen de computer", "Tegen iemand anders (Online)");
        OthelloOpponent.getSelectionModel().select("Tegen de computer");
    }

    /**
     *The user clicks on the tictactoe start button, Tictactoe wil start...
     */
    @FXML
    void TicTacToeStart(ActionEvent event) {
        Parent root;
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("TicTacToeScreen/View.fxml"));
            root = (Parent) loader.load();

            TicTacToeScreen.Controller ticTacToeScreen=loader.getController();
            ticTacToeScreen.setdifficulty(TicTacToeDifficulty.getValue());

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
     *The user clicks on the othello start button, othello wil start...
     */
    @FXML
    void OthelloStart(ActionEvent event) throws IOException {
        if(OthelloOpponent.getValue().equals("Tegen de computer")){
            Parent root;
            try {
                FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("OthelloScreenOffline/View.fxml"));
                root = (Parent) loader.load();

                OthelloScreenOffline.Controller othelloScreenOffline=loader.getController();
                othelloScreenOffline.setDifficulty(OthelloDifficulty.getValue());

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
        else{
            Game.NetwerkConnection netwerkConnection = NetwerkConnection.getInstance();
            netwerkConnection.startConnection(playerData.getIpadres(), playerData.getPortnumber());
            netwerkConnection.sendMessage("Login " + playerData.getUsername());
            Parent root;
            try {
                FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("Lobby/View.fxml"));
                root = (Parent) loader.load();

                Lobby.Controller lobby=loader.getController();
                lobby.setDifficultyAI(OthelloDifficulty.getValue());

                Stage stage=new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                stage.setResizable(false);
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *The user clicks on the ? button, the user will be redirected to the network settings...
     */
    @FXML
    public void naarNetwerk(ActionEvent event) {
        Parent root;
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("Netwerkinstellingen/View.fxml"));
            root = (Parent) loader.load();
            Stage stage=new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
