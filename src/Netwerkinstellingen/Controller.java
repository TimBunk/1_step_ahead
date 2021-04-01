package Netwerkinstellingen;

import Game.PlayerData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private Model model = new Model();
    private PlayerData playerData;

    @FXML
    private TextField IPinvoer;

    @FXML
    private TextField Portinvoer;

    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
        IPinvoer.setText(playerData.getIpadres());
        Portinvoer.setText(String.valueOf(playerData.getPortnumber()));
    }

    @FXML
    public void naarMain(ActionEvent event) {
        playerData.setIpadres(IPinvoer.getText());
        playerData.setPortnumber(Integer.parseInt(Portinvoer.getText()));
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
