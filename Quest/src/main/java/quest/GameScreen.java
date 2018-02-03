package quest;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;



public class GameScreen extends Application {

    private static final Logger logger = LogManager.getLogger(GameScreen.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("hi");
       initUI(primaryStage);

    }

    private void initUI(Stage primaryStage){
        Pane canvas = new Pane();
        canvas.setStyle("-fx-background-color: #6F737E");
        addControlsToCanvas(canvas);
        setupCardsAnimation(canvas);


        Scene scene = new Scene(canvas, 1366, 768 );

        primaryStage.setScene(scene);
        primaryStage.setTitle("Quest");
        primaryStage.show();
    }
    private void addControlsToCanvas(Pane canvas){

    }
    private void setupCardsAnimation(Pane canvas){
        Image img = null;
        File cardsDir = new File("src/main/resources/Cards/");
        FilenameFilter imgFilter = (dir, name) -> name.toLowerCase().endsWith("jpg");

        File[] cardsFile = cardsDir.listFiles(imgFilter);
        Image[] cardsImg = new Image[cardsFile.length];

        int index =0;
        for (File cardFile : cardsFile){
            try {
                cardsImg[index++] = new Image(new FileInputStream(cardFile.getPath()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        ImageView imgView = new ImageView();
        imgView.setFitHeight(250);
        imgView.setFitWidth(250);
        imgView.setPreserveRatio(true);
        imgView.setImage(cardsImg[1]);
        imgView.relocate(20,180);

        canvas.getChildren().add(imgView);
    }

}
