package quest.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage)throws Exception{
        Pane canvas = new Pane();
        String fxmlPath = "/fxml/ServerSettings.fxml";
        canvas.setStyle("-fx-background-color: #6F737E");
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Controller controller = loader.getController();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Server Settings");
        primaryStage.show();
        //  controller.gameLoop();
    }
        public static void main(String[] args) {
            launch(args);
        }
}
