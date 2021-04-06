package Lobby;

import Game.NetwerkConnection;
import Game.PlayerData;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.jar.JarOutputStream;

public class Controller extends Thread{
    private PlayerData playerData;
    private NetwerkConnection netwerkConnection;

    @FXML
    private ListView<String> playersListView;

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
        netwerkConnection.sendMessage("get playerlist");
        Thread t = new Thread(this);
        t.start();
    }

    public void setPlayersListView(String playerlist){
        playersListView.getItems().clear();
        String listString = playerlist.substring(15);

        String test = listString.replaceAll("\\[|\\]","");

        List<String> myList = new ArrayList<String>(Arrays.asList(test.split(",")));
        for (String username: myList){
            String usernameGood = username.replaceAll("\"","").replaceAll(" ", "");
            if (!usernameGood.equals(playerData.getUsername())){
                playersListView.getItems().add(usernameGood);
            }
        }
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
    void refresh(ActionEvent event) throws IOException {
        netwerkConnection.sendMessage("get playerlist");
    }

    @Override
    public void run() {
        System.out.println("okioki");
        while (true){
            try {
                String reveived = netwerkConnection.getMessage();
                if (!reveived.equals("OK")){
                    System.out.println("data binnen: " +reveived);
                    String firstWord = reveived.split(" ")[1];
                    System.out.println("Eerst woord is: " +firstWord);
                    System.out.println("test...");
                    switch (firstWord){
                        case "PLAYERLIST" :
                            System.out.println("jij wilt dit: " + reveived);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    setPlayersListView(reveived);
                                }
                            });

                            break;
                        case "GAME" :
                            switch (reveived.split(" ")[2]){
                                case "CHALLENGE" :
                                    System.out.println("je bent uitgenodigd");
                                    System.out.println(reveived);
                            }

                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}
