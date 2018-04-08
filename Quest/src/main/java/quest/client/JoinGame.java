

package quest.client;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class JoinGame {
    @FXML public TextField server_ip;
    @FXML public TextField port;
    @FXML public TextField name;

    public void onClick() throws IOException{
        PlayerData.ipAddress = server_ip.getText();
        PlayerData.port = Integer.parseInt(port.getText());
        PlayerData.name = name.getText();
        Stage stage;
        stage = (Stage) server_ip.getScene().getWindow();
        Pane canvas = new Pane();
        String fxmlPath = "/fxml/PlayerView.fxml";
        canvas.setStyle("-fx-background-color: #6F737E");
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Quests Of The Round Table");
        stage.show();
    }
}
