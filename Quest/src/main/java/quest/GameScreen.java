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
       initUI(primaryStage);

    }

    public void initUI(Stage primaryStage){
        Pane canvas = new Pane();
        canvas.setStyle("-fx-background-color: #6F737E");
        addControlsToCanvas(canvas);
        setupCardsAnimation(canvas);


        Scene scene = new Scene(canvas, 1366, 768 );

        primaryStage.setScene(scene);
        primaryStage.setTitle("Quest");
        primaryStage.show();
    }
    public void addControlsToCanvas(Pane canvas){

    }
    public void setupCardsAnimation(Pane canvas){

    }

}
