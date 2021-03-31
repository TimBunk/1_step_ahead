package BeginScreen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Game.Player;
import java.io.IOException;

public class Controller {
    private Model model = new Model();
    private String username;

    @FXML
    private TextField usernameField;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @FXML
    void next(ActionEvent event) throws IOException {
        System.out.println("gelikt op doorgaan...");
        Player player = new Player(usernameField.getText());
        Parent root;
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("MainScreen/View.fxml"));
            root = (Parent) loader.load();

            MainScreen.Controller mainScreenController=loader.getController();
            mainScreenController.setPlayer(player);

            Stage stage=new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }

}
