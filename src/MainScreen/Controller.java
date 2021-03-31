package MainScreen;

import Game.PlayerData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

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
}
