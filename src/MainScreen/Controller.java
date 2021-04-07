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
    @FXML
    private Label usernameLabel;

    @FXML
    private ChoiceBox<String> OthelloDifficulty;

    @FXML
    private ChoiceBox<String> OthelloOpponent;

    @FXML
    private ChoiceBox<String> TicTacToeDifficulty;

    private PlayerData player;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Main scherm geladen");
        OthelloDifficulty.getItems().addAll("Gemiddeld", "Moeilijk");
        OthelloDifficulty.getSelectionModel().select("Gemiddeld");
        TicTacToeDifficulty.getItems().addAll("Gemiddeld", "Moeilijk");
        TicTacToeDifficulty.getSelectionModel().select("Gemiddeld");
        OthelloOpponent.getItems().addAll("Tegen de computer", "Tegen iemand anders (Online)");
        OthelloOpponent.getSelectionModel().select("Tegen de computer");
    }

    public void setPlayer(PlayerData player){
        this.player = player;
        usernameLabel.setText(player.getUsername());
    }

    @FXML
    void TicTacToeStart(ActionEvent event) {
        System.out.println("TicTacToe gestart");
        Parent root;
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("TicTacToeScreen/View.fxml"));
            root = (Parent) loader.load();

            TicTacToeScreen.Controller ticTacToeScreen=loader.getController();
            ticTacToeScreen.setPlayer(player);
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
                stage.show();
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            Game.NetwerkConnection netwerkConnection = NetwerkConnection.getInstance();
            netwerkConnection.startConnection(player.getIpadres(), player.getPortnumber());
            netwerkConnection.sendMessage("Login " + player.getUsername());
            Parent root;
            try {
                FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("Lobby/View.fxml"));
                root = (Parent) loader.load();
                Stage stage=new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



    @FXML
    public void naarNetwerk(ActionEvent event) {
        System.out.println("Naar netwerkinstellingen...");
        Parent root;
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("Netwerkinstellingen/View.fxml"));
            root = (Parent) loader.load();
            Netwerkinstellingen.Controller netwerkinstellingen=loader.getController();
            netwerkinstellingen.setPlayerData(player);

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
