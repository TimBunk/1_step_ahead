package MainScreen;

import Game.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Label usernameLabel;
    private Player player;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Main scherm geladen");
    }

    public void setPlayer(Player player){
        this.player = player;
        usernameLabel.setText(player.getUsername());
    }
}
