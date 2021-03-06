package Netwerkinstellingen;

import Game.PlayerData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private PlayerData playerData;

    @FXML
    private TextField IPinvoer;

    @FXML
    private TextField Portinvoer;

    @FXML
    private TextField TimeOutinvoer;

    @FXML
    private TextField Depthinvoer;

    /**
     *The network settings screen gets initialized...
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerData = PlayerData.getInstance();
        IPinvoer.setText(playerData.getIpadres());
        TimeOutinvoer.setText(String.valueOf(playerData.getTimeOutTime()));
        Portinvoer.setText(String.valueOf(playerData.getPortnumber()));
        Depthinvoer.setText(String.valueOf(playerData.getDepthAI()));
    }

    /**
     *The user saves the network settings...
     */
    @FXML
    public void naarMain(ActionEvent event) {
        playerData.setIpadres(IPinvoer.getText());
        playerData.setPortnumber(Integer.parseInt(Portinvoer.getText()));
        playerData.setTimeOutTime(Integer.parseInt(TimeOutinvoer.getText()));
        playerData.setDepthAI(Integer.parseInt(Depthinvoer.getText()));
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
