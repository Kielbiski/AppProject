package quest;

import com.sun.xml.internal.bind.v2.runtime.output.ForkXmlOutput;
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
        initUI(primaryStage);
    }
    private void initUI(Stage primaryStage) throws Exception{
        Pane canvas = new Pane();
        String fxmlPath = "/fxml/PlayerView.fxml";
        canvas.setStyle("-fx-background-color: #6F737E");
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));

        Parent root = loader.load();
        Scene scene = new Scene(root);


        primaryStage.setFullScreen(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Quest");
        primaryStage.show();



    }

    public static void main(String[] args) {
        launch(args);

    }
}

