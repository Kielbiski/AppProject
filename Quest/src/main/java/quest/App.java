package quest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;



public class App extends Application {
//    //Initialize Group root for Main Node
//    Group root = new Group();
//
//    //Initialize Scene on group root with specific sizes
//    Scene scene = new Scene(root, 450, 250);
//
//    //Initialize BorderPane and Bind the layout with the scene size.
//    BorderPane borderPane = new BorderPane();
//    borderPane.prefHeightProperty().bind(scene.heightProperty());
//    borderPane.prefWidthProperty().bind(scene.widthProperty());
//
//    //Set the TabPane to be centered
//    borderPane.setCenter(tabPane);
//
//    //Adds Layout to Main Node
//    root.getChildren().add(borderPane);
    private static final Logger logger = LogManager.getLogger(App.class);

    @Override
    public void start(Stage primaryStage)throws Exception{
        initUI(primaryStage);
    }
    private void initUI(Stage primaryStage) throws Exception{
        Pane canvas = new Pane();
        canvas.setStyle("-fx-background-color: #6F737E");

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/PlayerView.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Quest");
        primaryStage.show();


//        readyButton.setOnMouseClicked(event ->
//        {
//            if(event.getButton() != MouseButton.PRIMARY)
//            {
//                return;
//            }
//            model.nextTurn();
//            readyText.setText("It's your turn, " + model.getState().getCurrentTurnPlayer().getPlayerName() + "!");
//        });
    }

    private void setupCardsAnimation(Pane canvas) {

        Image img = null;
        File cardsDir = new File("src/main/resources/Cards/");
        FilenameFilter imgFilter = (dir, name) -> name.toLowerCase().endsWith("jpg");

        File[] cardsFile = cardsDir.listFiles(imgFilter);
        Image[] cardsImg = new Image[cardsFile.length];

        int index = 0;
        for (File cardFile : cardsFile) {
            try {
                cardsImg[index++] = new Image(new FileInputStream(cardFile.getPath()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        ImageView imgView = new ImageView();
        imgView.setFitHeight(100);
        imgView.setFitWidth(100);
        imgView.setPreserveRatio(true);
        imgView.setImage(cardsImg[4]);
        imgView.relocate(20, 180);

        canvas.getChildren().add(imgView);
    }

    public static void main(String[] args) {
        launch(args);

    }
}

