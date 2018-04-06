//To Reference


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
    @FXML public String sPort;




    public void onClick() throws IOException{
        data.ip = server_ip.getText();
        this.sPort = port.getText();
        data.name = name.getText();
        data.port = Integer.parseInt(sPort);


        Stage stage;
        stage = (Stage) server_ip.getScene().getWindow();
        //Parent parent = FXMLLoader.load(getClass().getResource("room.fxml"));
        Pane canvas = new Pane();
        String fxmlPath = "/fxml/PlayerView.fxml";
        canvas.setStyle("-fx-background-color: #6F737E");
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Controller controller = loader.getController();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Quests Of The Round Table");
        stage.show();
    }


}
