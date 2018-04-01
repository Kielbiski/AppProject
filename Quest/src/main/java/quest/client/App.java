package quest.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class App extends Application {

    private static final Logger logger = LogManager.getLogger(App.class);

    @Override
    public void start(Stage primaryStage)throws Exception{
        Pane canvas = new Pane();
        String fxmlPath = "/fxml/PlayerView.fxml";
        canvas.setStyle("-fx-background-color: #6F737E");
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
//        Controller controller = loader.getController();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Quests Of The Round Table");
        primaryStage.show();
      //  controller.gameLoop();
    }

    public static void main(String[] args) {
        logger.info("App has been Launched ");
        launch(args);
    }
}
