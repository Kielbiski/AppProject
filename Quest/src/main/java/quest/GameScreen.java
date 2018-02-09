package quest;

import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
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

    GameModel model;

    Scene scene;

    Button readyButton = new Button();
    Text readyText = new Text();

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        model = new GameModel();
        model.startGame();
       initUI(primaryStage);
    }

    private void initUI(Stage primaryStage){
        Pane canvas = new Pane();
        canvas.setStyle("-fx-background-color: #6F737E");

        scene = new Scene(canvas, 1366, 768 );

        addControlsToCanvas(canvas);
        setupCardsAnimation(canvas);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Quest");
        primaryStage.show();

        positionUIElements();

        readyButton.setOnMouseClicked(event ->
        {
            if(event.getButton() != MouseButton.PRIMARY)
            {
                return;
            }
            model.nextTurn();
            readyText.setText("It's your turn, " + model.getCurrentTurnPlayer().getPlayerName() + "!");
        });
    }

    private void addControlsToCanvas(Pane canvas){
        readyText.setText("It's your turn, " + model.getCurrentTurnPlayer().getPlayerName() + "!");
        readyText.setFont(Font.font(72));
        readyText.setFill(Color.WHITE);

        readyButton.setText("Ready");
        readyButton.setFont(Font.font(48));
        readyButton.setStyle("-fx-background-color: green");
        readyButton.setTextFill(Color.DARKGREEN);

        canvas.getChildren().add(readyButton);
        canvas.getChildren().add(readyText);
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

    private void positionUIElements()
    {
        Bounds bounds = readyText.getBoundsInParent();
        readyText.relocate(scene.getWidth() / 2 - bounds.getWidth() / 2, scene.getHeight() / 2 - bounds.getHeight() / 2);
        readyButton.relocate(scene.getWidth() / 2 - readyButton.getWidth() / 2, readyText.localToScene(readyText.getBoundsInLocal()).getMinY() + 100);
    }
}
