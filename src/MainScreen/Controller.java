package MainScreen;

import Game.PlayerData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Label usernameLabel;
    private PlayerData player;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Main scherm geladen");
    }

    public void setPlayer(PlayerData player){
        this.player = player;
        usernameLabel.setText(player.getUsername());
    }

    @FXML
    public void naarNetwerk(ActionEvent event) {
        System.out.println("Naar netwerkinstellingen...");
        Parent root;
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("Netwerkinstellingen/View.fxml"));
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
