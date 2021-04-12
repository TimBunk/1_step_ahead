package BeginScreen;

import Game.PlayerData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    @FXML
    private TextField usernameField;


    /**
     *The user clicks on the next button, username will be saved and the user goes to the main screen...
     */
    @FXML
    void next(ActionEvent event) throws IOException {
        PlayerData player = PlayerData.getInstance();
        player.setUsername(usernameField.getText());
        Parent root;
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("MainScreen/View.fxml"));
            root = (Parent) loader.load();
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
}
