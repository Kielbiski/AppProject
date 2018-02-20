package quest;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.ResourceBundle;
import java.awt.*;

//GAMEPLAN FOR TOMORROW
//ALL GAME APP HAS TO DO IS LAUNCH THE APP< NOTHING ELSE THE REST IS IN HERE
//1: make game model class track its gurrent state
//2: controller init method that creates a new game with players
//3: START MAKING METHODS FOR EACH GAME SCENARIO IE SHOW QUEST CARDS ETC LETS GO


public class Controller {

    private Model game = new Model();

    @FXML
    private BorderPane mainBorderPane ;
    @FXML
    private GridPane handGridPane ;
    @FXML
    private HBox alliesHbox ;
    @FXML
    private HBox weaponHbox;
    @FXML
    private HBox foesHbox;
    @FXML
    private VBox playerStatsVbox;

    //
    @FXML
    private Label player1Label;
    @FXML
    private Label player2Label;
    @FXML
    private Label player3Label;
    @FXML
    private Label player4Label;

    public void update(){
        ArrayList<Player> currentPlayers = game.getPlayers();

        player1Label.setText("Name: " + currentPlayers.get(0).getPlayerName() + "\n" +
                            "Rank: " + currentPlayers.get(0).getPlayerRank() + "\n" +
                            "# of Cards: " +currentPlayers.get(0).getNumCardsInHand());

        player2Label.setText("Name: " + currentPlayers.get(1).getPlayerName() + "\n" +
                              "Rank: " + currentPlayers.get(1).getPlayerRank() + "\n" +
                             "# of Cards: " +currentPlayers.get(1).getNumCardsInHand());

        player3Label.setText("Name: " + currentPlayers.get(2).getPlayerName() + "\n" +
                            "Rank: " + currentPlayers.get(2).getPlayerRank() + "\n" +
                            "# of Cards: " +currentPlayers.get(2).getNumCardsInHand());

        player4Label.setText("Name: " + currentPlayers.get(3).getPlayerName() + "\n" +
                            "Rank: " + currentPlayers.get(3).getPlayerRank() + "\n" +
                            "# of Cards: " +currentPlayers.get(3).getNumCardsInHand());


    }


    public void initialize() {
       update();
    }


}