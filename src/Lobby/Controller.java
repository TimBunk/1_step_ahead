package Lobby;

import Game.NetwerkConnection;
import Game.PlayerData;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.jar.JarOutputStream;

public class Controller extends Thread implements Initializable{
    private PlayerData playerData;
    private NetwerkConnection netwerkConnection;
    private Thread t;
    private final AtomicBoolean running = new AtomicBoolean(false);

    @FXML
    private ListView<String> playersListView;

    @FXML
    private AnchorPane LobbyScreen;

    @FXML
    private Label usernameLabel;

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

    public void getChallenge(String challenge){
        System.out.println(challenge);
        List<String> inputlist = Arrays.asList(challenge.substring(19).replaceAll("\\{|\\}| ","").split(","));
        Map map=new HashMap();
        for (String text: inputlist){
            System.out.println(text);
            map.put(text.replaceAll(" ", "").split(":")[0], text.replaceAll("\"", "").split(":")[1]);
        }
        Alert a = new Alert(Alert.AlertType.NONE, "Je bent uitgenodigd door: " + map.get("CHALLENGER") + ". Om een potje " + map.get("GAMETYPE") + " te spelen.", ButtonType.OK, ButtonType.NO);
        a.showAndWait().ifPresent(buttonType -> {
            if(buttonType == ButtonType.OK){
                System.out.println("Uitdaging aangegaan");
                try {
                    netwerkConnection.sendMessage("challenge accept "+ map.get("CHALLENGENUMBER"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("Uitdaging niet aangegaan");

            }
        });
    }

    @FXML
    void automatic(ActionEvent event) throws IOException {
        netwerkConnection.sendMessage("subscribe Reversi");

    }

    @FXML
    void challange(ActionEvent event) throws IOException {
        System.out.println(playersListView.getSelectionModel().getSelectedItem());
        String opponent = playersListView.getSelectionModel().getSelectedItem();
        if (opponent != null){
            netwerkConnection.sendMessage("challenge \"" + opponent + "\" \"Reversi\"");
        }
    }

    @FXML
    void exit(ActionEvent event) {
        System.out.println("Terug naar main screen");

        Parent root;
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("MainScreen/View.fxml"));
            root = (Parent) loader.load();
            MainScreen.Controller mainScreen=loader.getController();
            mainScreen.setPlayer(playerData);


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
    void refresh(ActionEvent event) throws IOException {
        netwerkConnection.sendMessage("get playerlist");
    }

    public void stopThread() {
        running.set(false);
    }

    @Override
    public void run() {
        running.set(true);
        while (running.get()){
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
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            getChallenge(reveived);
                                        }
                                    });
                                    break;
                                case "MATCH" :
                                    stopThread();
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            Parent root;
                                            try {
                                                FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("OthelloScreenOnline/View.fxml"));
                                                root = (Parent) loader.load();

                                                Stage stage=new Stage();
                                                stage.setScene(new Scene(root));
                                                stage.setResizable(false);
                                                stage.show();

                                                // ((Node)(event.getSource())).getScene().getWindow().hide();
                                            }
                                            catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                    break;















                            }

                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerData = PlayerData.getInstance();
        netwerkConnection = NetwerkConnection.getInstance();
        usernameLabel.setText(playerData.getUsername());
        try {
            netwerkConnection.sendMessage("get playerlist");
        } catch (IOException e) {
            e.printStackTrace();
        }
        t = new Thread(this);
        t.start();
    }
}
