package Lobby;

import Game.NetwerkConnection;
import Game.PlayerData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Controller {
    private PlayerData playerData;
    private NetwerkConnection netwerkConnection;

    @FXML
    private AnchorPane LobbyScreen;

    @FXML
    private Label usernameLabel;

    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
        usernameLabel.setText(playerData.getUsername());
    }

    public void setNetwerkConnection(NetwerkConnection netwerkConnection) throws IOException {
        this.netwerkConnection = netwerkConnection;
        //while (netwerkConnection.getMessage()!= null){
          //  System.out.println(netwerkConnection.getMessage());
        //}
        System.out.println(netwerkConnection.getMessage());
        System.out.println(netwerkConnection.getMessage());
        System.out.println(netwerkConnection.getMessage());
        netwerkConnection.sendMessage("get playerlist");
        System.out.println(netwerkConnection.getMessage());
        System.out.println(netwerkConnection.getMessage());
    }

    @FXML
    void automatic(ActionEvent event) {

    }

    @FXML
    void challange(ActionEvent event) {

    }



    @FXML
    void naarNetwerk(ActionEvent event) {

    }

    @FXML
    void refresh(ActionEvent event) {

    }


}
