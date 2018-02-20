package quest;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.fxml.FXML;
import java.awt.*;

//GAMEPLAN FOR TOMORROW
//ALL GAME APP HAS TO DO IS LAUNCH THE APP< NOTHING ELSE THE REST IS IN HERE
//1: make game model class track its gurrent state
//2: controller init method that creates a new game with players
//3: START MAKING METHODS FOR EACH GAME SCENARIO IE SHOW QUEST CARDS ETC LETS GO


public class Controller {

    private Model game = new Model();
    //
    @FXML
    private BorderPane mainBorderPane ;
    @FXML
    private GridPane handGridPane ;
    @FXML
    private HBox alliesHbox ;

    //
    @FXML
    private TextArea p1TextArea ;
    @FXML
    private TextArea p2TextArea ;
    @FXML
    private TextArea p3TextArea ;
    @FXML
    private TextArea p4TextArea ;




    public Controller() {
        System.out.println("first");
    }

    @FXML
    public void initialize() {
        System.out.println("second");
    }


}