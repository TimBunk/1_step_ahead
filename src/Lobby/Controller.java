package Lobby;

import Game.NetwerkConnection;
import Game.PlayerData;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Controller extends Thread implements Initializable {
    private PlayerData playerData;
    private NetwerkConnection netwerkConnection;
    private Thread thread;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private String DifficultyAI;

    @FXML
    private ListView<String> playersListView;

    @FXML
    private AnchorPane LobbyScreen;

    @FXML
    private Label usernameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        start();
        playerData = PlayerData.getInstance();
        netwerkConnection = NetwerkConnection.getInstance();
        usernameLabel.setText(playerData.getUsername());
        try {
            netwerkConnection.sendMessage("get playerlist");
        } catch (IOException e) {
            e.printStackTrace();
        }
        thread = new Thread(this);
        thread.start();
    }

    @FXML
    void automatic(ActionEvent event) throws IOException {
        netwerkConnection.sendMessage("subscribe Reversi");
    }

    @FXML
    void challange() throws IOException {
        String opponent = playersListView.getSelectionModel().getSelectedItem();
        if (opponent != null) {
            netwerkConnection.sendMessage("challenge \"" + opponent + "\" \"Reversi\"");
        }
    }

    @FXML
    void exit(ActionEvent event) throws IOException {
        stopThread();
        netwerkConnection.sendMessage("logout");
        netwerkConnection.stopConnection();

        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("MainScreen/View.fxml"));
            root = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();

            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void refresh(ActionEvent event) throws IOException {
        netwerkConnection.sendMessage("get playerlist");
    }

    public void setPlayersListView(String playerlist) {
        playersListView.getItems().clear();
        String usersString = playerlist.substring(15).replaceAll("\\[|\\]", "");

        List<String> users = new ArrayList<String>(Arrays.asList(usersString.split(",")));
        for (String username : users) {
            String usernameGood = username.replaceAll("\"", "").replaceAll(" ", "");
            if (!usernameGood.equals(playerData.getUsername())) {
                playersListView.getItems().add(usernameGood);
            }
        }
    }

    public void setDifficultyAI(String difficultyAI) { this.DifficultyAI = difficultyAI;}

    public void getChallenge(String challenge) {
        List<String> inputlist = Arrays.asList(challenge.substring(19).replaceAll("\\{|\\}| ", "").split(","));
        Map map = new HashMap();
        for (String text : inputlist) {
            map.put(text.replaceAll(" ", "").split(":")[0], text.replaceAll("\"", "").split(":")[1]);
        }
        Alert a = new Alert(Alert.AlertType.NONE, "Je bent uitgenodigd door: " + map.get("CHALLENGER") + ". Om een potje " + map.get("GAMETYPE") + " te spelen.", ButtonType.OK, ButtonType.NO);
        a.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                stopThread();
                try {
                    netwerkConnection.sendMessage("challenge accept " + map.get("CHALLENGENUMBER"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void stopThread() {
        running.set(false);
    }

    @Override
    public void run() {
        running.set(true);
        synchronized (netwerkConnection) {
            while (running.get()) {
                try {
                    String reveived = netwerkConnection.getMessage();
                    if (!reveived.equals("OK")) {
                        System.out.println("data binnen: " + reveived);
                        String firstWord = reveived.split(" ")[1];
                        switch (firstWord) {
                            case "PLAYERLIST":
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        setPlayersListView(reveived);
                                    }
                                });
                                break;
                            case "GAME":
                                switch (reveived.split(" ")[2]) {
                                    case "CHALLENGE":
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                getChallenge(reveived);
                                            }
                                        });
                                        break;
                                    case "MATCH":
                                        stopThread();
                                        String startPlayer = reveived.split(" ")[4].replaceAll("\"|\\,", "");
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                Parent root;
                                                try {
                                                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("OthelloScreenOnline/View.fxml"));
                                                    root = (Parent) loader.load();
                                                    OthelloScreenOnline.Controller othelloScreenOnline = loader.getController();
                                                    othelloScreenOnline.setStartPlayer(startPlayer);
                                                    othelloScreenOnline.setDifficultyAI(DifficultyAI);

                                                    Stage stage = new Stage();
                                                    stage.setScene(new Scene(root));
                                                    stage.setResizable(false);
                                                    stage.show();
                                                    LobbyScreen.getScene().getWindow().hide();
                                                } catch (IOException e) {
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
    }
}
