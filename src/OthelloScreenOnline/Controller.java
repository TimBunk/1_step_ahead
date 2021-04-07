package OthelloScreenOnline;

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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class Controller extends Thread implements Initializable {

    NetwerkConnection netwerkConnection;
    PlayerData playerData;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private Thread t;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        netwerkConnection = NetwerkConnection.getInstance();
        playerData = PlayerData.getInstance();
        t = new Thread(this);
        t.start();
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
                        case "GAME" :
                            switch (reveived.split(" ")[2]){
                                case "CHALLENGE" :
                                    System.out.println("je bent uitgenodigd");
                                    System.out.println(reveived);
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            //getChallenge(reveived);
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
}

