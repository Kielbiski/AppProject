package quest;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.lang.reflect.Array;
import java.util.*;
import java.awt.*;


import static quest.Rank.CHAMPION_KNIGHT;
import static quest.Rank.KNIGHT_OF_THE_ROUND_TABLE;

//GAMEPLAN FOR TOMORROW
//ALL GAME APP HAS TO DO IS LAUNCH THE APP< NOTHING ELSE THE REST IS IN HERE
//1: make game model class track its gurrent state
//2: controller init method that creates a new game with players
//3: START MAKING METHODS FOR EACH GAME SCENARIO IE SHOW QUEST CARDS ETC LETS GO


public class Controller {

    private Model game = new Model();
    private String resourceFolderPath = "src/main/resources/Cards/";
    private Player activePlayer;
    @FXML
    private BorderPane mainBorderPane ;
    @FXML
    private HBox cardsHbox;
    @FXML
    private VBox playerStatsVbox;

    private void update(){
        //Vbox display player data
        ArrayList<Player> currentPlayers = game.getPlayers();
        playerStatsVbox.getChildren().clear();
        cardsHbox.getChildren().clear();
        for(Player player : currentPlayers) {
            Label playerLabel = new Label();
            String labelCSS;
            if(player == activePlayer) {
                labelCSS = "-fx-border-color: #EEE8AA;\n";
            } else {
                labelCSS = "-fx-border-color: #d6d6d6;\n";
            }
            labelCSS += "-fx-border-insets: 5;\n" +
                        "-fx-border-width: 4;\n" +
                        "-fx-border-style: solid;\n" +
                        "-fx-border-radius: 10;" +
                        "-fx-padding: 10";

            playerLabel.setStyle(labelCSS);
            playerLabel.setTextAlignment(TextAlignment.RIGHT);
            playerLabel.setMinWidth(Region.USE_PREF_SIZE);
            playerLabel.setText(player.getPlayerName() + "\n" +
                    "" + player.getPlayerRank() + "\n" +
                    "" + player.getNumCardsInHand()+ " cards");
            playerStatsVbox.getChildren().add(playerLabel);
        }
        //Hbox display card images
        ArrayList<ImageView> imgViews = new ArrayList<>();
        Stack<AdventureCard> playerHand = activePlayer.getCardsInHand();
        playerHand.sort(Comparator.comparing(object2 -> object2.getClass().getName()));
        playerHand.sort(Comparator.comparing(object -> object.getClass().getSuperclass().getName()));
        HashMap<ImageView, AdventureCard> imageToObjectMap = new HashMap<>();
        for(AdventureCard card : playerHand) {
            ImageView imgView = new ImageView();
            imgView.setPreserveRatio(true);
            imgView.setFitHeight(150);
            //imgView.fitHeightProperty().bind(cardsHbox.heightProperty());
            imgView.setImage(getCardImage(card.getImageFilename()));
            //HBox.setHgrow(imgView, Priority.ALWAYS);
            imgViews.add(imgView);
            imageToObjectMap.put(imgView, card);
        }
//        cardsHbox.prefWidthProperty().bind(mainBorderPane.widthProperty());
//        cardsHbox.widthProperty().addListener(e -> {
//            double fitWidth = mainBorderPane.widthProperty().get() / imgViews.size();
//            for (ImageView imageView : imgViews) {
//                imageView.setFitWidth(fitWidth);
//            }
//        });
        cardsHbox.getChildren().addAll(imgViews);
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
    private void setPlayerNames(){
        //cardsHbox.prefWidthProperty().bind(Stage.widthProperty().multiply(0.80));
        for(int i =0; i < 4; i++){
            TextInputDialog dialog = new TextInputDialog("Enter Name");
            dialog.setTitle("Set Player name");
            dialog.setHeaderText("Player " + (i+1) + " enter your name");
            // dialog.setContentText("Please enter your name:");

            Optional<String> result = dialog.showAndWait();

            // The Java 8 way to get the response value (with lambda expression).
            result.ifPresent(name -> game.addPlayer(name));
        }
    }
    public void initialize() {
        setPlayerNames();

        activePlayer = game.getPlayers().get(0);
        playerStatsVbox.setSpacing(5);
        playerStatsVbox.setAlignment(Pos.TOP_RIGHT);
        cardsHbox.setSpacing(5);
        game.shuffleAndDeal();
        //testing
        game.getPlayers().get(0).setPlayerRank(CHAMPION_KNIGHT);
        game.getPlayers().get(1).setPlayerRank(KNIGHT_OF_THE_ROUND_TABLE);
        //

        update();
    }

    private javafx.scene.image.Image getCardImage(String cardFileName){
        javafx.scene.image.Image img;

        try {
            img = new Image(new FileInputStream(new File(resourceFolderPath + cardFileName)));//can be url
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return img;
    }


}