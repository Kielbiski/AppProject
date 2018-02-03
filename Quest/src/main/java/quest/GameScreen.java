package quest;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;


public class GameScreen extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane canvas = new Pane();
        canvas.setStyle("-fx-background-color: #6F737E");

        Scene scene = new Scene(canvas);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Quest");
        primaryStage.show();

    }
}
