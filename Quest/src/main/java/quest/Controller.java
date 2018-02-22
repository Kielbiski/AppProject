package quest;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.TextInputDialog;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.awt.*;


import static quest.Rank.KNIGHT_OF_THE_ROUND_TABLE;

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

    private void update(){
        ArrayList<Player> currentPlayers = game.getPlayers();

        if(currentPlayers.size() !=0) {
            player1Label.setText(currentPlayers.get(0).getPlayerName() + "\n" +
                    "Rank: " + currentPlayers.get(0).getPlayerRank() + "\n" +
                    "# of Cards: " + currentPlayers.get(0).getNumCardsInHand());

            player2Label.setText(currentPlayers.get(1).getPlayerName() + "\n" +
                    "Rank: " + currentPlayers.get(1).getPlayerRank() + "\n" +
                    "# of Cards: " + currentPlayers.get(1).getNumCardsInHand());

            player3Label.setText(currentPlayers.get(2).getPlayerName() + "\n" +
                    "Rank: " + currentPlayers.get(2).getPlayerRank() + "\n" +
                    "# of Cards: " + currentPlayers.get(2).getNumCardsInHand());

            player4Label.setText(currentPlayers.get(3).getPlayerName() + "\n" +
                    "Rank: " + currentPlayers.get(3).getPlayerRank() + "\n" +
                    "# of Cards: " + currentPlayers.get(3).getNumCardsInHand());
        }
    }

    private ArrayList<Player> finalTournament(ArrayList<Player> tournamentParticipants){
        Tournament knightsOfTheRoundTableTournament = new Tournament("Knights of the Round Table Tournament", "", tournamentParticipants);
        return knightsOfTheRoundTableTournament.getTournamentWinner();
    }

    private ArrayList<Player> getWinningPlayers(Model model) {
        ArrayList<Player> winningPlayers;
        ArrayList<Player> knightsOfTheRoundTable = new ArrayList<>();
        for(Player player : model.getPlayers()){
            if (player.getPlayerRank() == KNIGHT_OF_THE_ROUND_TABLE) {
                knightsOfTheRoundTable.add(player);
            }
        }
        if (knightsOfTheRoundTable.size() == 1){
            winningPlayers = knightsOfTheRoundTable;
        } else {
            winningPlayers = finalTournament(knightsOfTheRoundTable);
        }
        return winningPlayers;
    }

    public void initialize() {
        update();

        for(int i =0; i < 4; i++){
            TextInputDialog dialog = new TextInputDialog("Enter Name");
            dialog.setTitle("Set Player name");
            dialog.setHeaderText("Player " + (i+1) + " enter your name");
           // dialog.setContentText("Please enter your name:");

            Optional<String> result = dialog.showAndWait();

            // The Java 8 way to get the response value (with lambda expression).
            result.ifPresent(name -> game.addPlayer(name));
        }

        update();
    }

    private javafx.scene.image.Image getCardImage(String cardFileName){
        javafx.scene.image.Image img;
        File cardsDir = new File("src/main/resources/Cards/");

        try {
            img = new Image(new FileInputStream(cardsDir + cardFileName));//can be url
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return img;
    }


    private void setupCardsAnimation(Pane canvas) {

        File cardsDir = new File("src/main/resources/Cards/");






//        ImageView imgView = new ImageView();
//        imgView.setFitHeight(100);
//        imgView.setFitWidth(100);
//        imgView.setPreserveRatio(true);
//        imgView.setImage(1);
//        imgView.relocate(20, 180);

      //  canvas.getChildren().add(imgView);
    }

}