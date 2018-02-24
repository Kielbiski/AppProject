package quest;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.TextAlignment;
import sun.awt.image.BufImgVolatileSurfaceManager;

import java.awt.font.ImageGraphicAttribute;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;


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
    private int NUM_PLAYERS = 4;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private HBox cardsHbox;
    @FXML
    private VBox playerStatsVbox;

    private AdventureCard selectedAdventureCard;

    private void update() {
        //Vbox display player data
        ArrayList<Player> currentPlayers = game.getPlayers();
        playerStatsVbox.getChildren().clear();
        for (Player player : currentPlayers) {
            Label playerLabel = new Label();
            String labelCSS;
            if (player == activePlayer) {
                labelCSS = "-fx-border-color: #EEE8AA;\n";
            } else {
                labelCSS = "-fx-border-color: #d6d6d6;\n";
            }
            labelCSS += "-fx-border-insets: 5;\n" +
                    "-fx-border-width: 4;\n" +
                    "-fx-border-style: solid;\n" +
                    "-fx-border-radius: 10;\n" +
                    "-fx-padding: 10";

            playerLabel.setStyle(labelCSS);
            playerLabel.setTextAlignment(TextAlignment.RIGHT);
            playerLabel.setMinWidth(Region.USE_PREF_SIZE);
            playerLabel.setText(player.getPlayerName() + "\n" +
                    "" + player.getPlayerRank() + "\n" +
                    "" + player.getNumCardsInHand() + " cards");
            playerStatsVbox.getChildren().add(playerLabel);
        }
        //Hbox display card images
        ArrayList<ImageView> imgViews = new ArrayList<>();
        Stack<AdventureCard> playerHand = activePlayer.getCardsInHand();
        playerHand.sort(Comparator.comparing(object2 -> object2.getClass().getName()));
        playerHand.sort(Comparator.comparing(object -> object.getClass().getSuperclass().getName()));
        HashMap<ImageView, AdventureCard> imageToObjectMap = new HashMap<>();
        for (AdventureCard card : playerHand) {
            ImageView imgView = new ImageView();
            imgView.setPreserveRatio(true);
            imgView.setFitHeight(100);
            // ScaleTransition st = new ScaleTransition(Duration.millis(2000), imgView);
            imageToObjectMap.put(imgView, card);
            imgView.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> imgView.setFitHeight(300));
//                    st.setByX(1.5f);
//                    st.setByY(1.5f);
//                    st.setCycleCount(4);
//                    st.setAutoReverse(false);
//                    st.play();
            imgView.addEventHandler(MouseEvent.MOUSE_EXITED, event -> imgView.setFitHeight(100));
            imgView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                imgView.setStyle(
                        "-fx-border-color: #ff0000;\n" +
                                " -fx-border-width: 10;" +
                                "-fx-border-style: solid;\n");
                selectedAdventureCard = imageToObjectMap.get(imgView);
                System.out.println(selectedAdventureCard);
            });
            imgView.setImage(getCardImage(card.getImageFilename()));
            imgViews.add(imgView);
        }

        cardsHbox.getChildren().addAll(imgViews);
    }

    private boolean isGameOver(){
        ArrayList<Player> winningPlayers = getWinningPlayers(game);
        if(winningPlayers.size() > 0){
            for(Player winningPlayer : winningPlayers) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, winningPlayer.getPlayerName() + " won the game!", ButtonType.OK);
                alert.showAndWait();
            }
            return true;
        }
        return false;

    }

    private ArrayList<Player> finalTournament(ArrayList<Player> tournamentParticipants) {
        Tournament knightsOfTheRoundTableTournament = new Tournament("Knights of the Round Table Tournament", "", tournamentParticipants);
        return knightsOfTheRoundTableTournament.getTournamentWinner();
    }

    private ArrayList<Player> getWinningPlayers(Model model) {
        ArrayList<Player> winningPlayers;
        ArrayList<Player> knightsOfTheRoundTable;
        knightsOfTheRoundTable = new ArrayList<>();
        for (Player player : model.getPlayers()) {
            if (player.getPlayerRank() == KNIGHT_OF_THE_ROUND_TABLE) {
                knightsOfTheRoundTable.add(player);
            }
        }
        if (knightsOfTheRoundTable.isEmpty()){
            winningPlayers = new ArrayList<>();
        } else if (knightsOfTheRoundTable.size() == 1) {
            winningPlayers = knightsOfTheRoundTable;
        } else {
            winningPlayers = finalTournament(knightsOfTheRoundTable);
        }
        return winningPlayers;
    }

    private void setupQuest(Player sponsor, Quest quest) {
        quest.setSponsor(sponsor);
        for (int i = 0; i < quest.getNumStage(); i++) {
            if (selectedAdventureCard instanceof Foe) {
                System.out.println("");
            }
        }
    }

    private void addQuestPlayers(Quest currentQuest){
        ArrayList<Player> questPlayers = new ArrayList<>();
        for(int i = 0; i < NUM_PLAYERS; i++){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, game.getPlayers().get(i).getPlayerName() + " would you like to join " + currentQuest.getName() + " ?", ButtonType.YES, ButtonType.NO);
            //alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                questPlayers.add(game.getPlayers().get(i));
            }
        }
        currentQuest.setPlayerList(questPlayers);
        System.out.println(currentQuest.getPlayerList());
    }
//
//    for stage in:
//        alert with foe/weapon/test per stage
//    check if bp is greater with successive stages



    private void setPlayerNames(){
        //cardsHbox.prefWidthProperty().bind(Stage.widthProperty().multiply(0.80));
        for(int i =0; i < NUM_PLAYERS; i++){
            TextInputDialog dialog = new TextInputDialog("Enter Name");
            dialog.setTitle("Set Player name");
            dialog.setHeaderText("Player " + (i+1) + " enter your name");
            // dialog.setContentText("Please enter your name:");

            Optional<String> result = dialog.showAndWait();

            // The Java 8 way to get the response value (with lambda expression).
            result.ifPresent(name -> game.addPlayer(name));
        }
    }

    private void gameLoop() {
        int counter = 0;
        while (true) {
            update();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "It is " + game.getPlayers().get(counter).getPlayerName() + "'s turn.", ButtonType.OK);
            //alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                System.out.println("It is " + game.getPlayers().get(counter).getPlayerName() + "'s turn.");
            }
            activePlayer = game.getPlayers().get(counter);
            game.drawStoryCard();
            if (game.getCurrentStory() instanceof Quest) {
                addQuestPlayers((Quest) game.getCurrentStory());
                setupQuest(game.getPlayers().get(counter), (Quest) game.getCurrentStory());
            } else if (game.getCurrentStory() instanceof Event) {
                System.out.println("Event");
            } else if (game.getCurrentStory() instanceof Tournament) {
                System.out.println("Tournament");
            }
            counter++;
            if (counter > 3){
                counter = 0;
            }
            if(isGameOver()){
                System.exit(0);
            }
        }
    }

    public void initialize() {
        setPlayerNames();
        activePlayer = game.getPlayers().get(0);
        playerStatsVbox.setSpacing(5);
        playerStatsVbox.setAlignment(Pos.TOP_RIGHT);
        cardsHbox.setAlignment(Pos.BASELINE_CENTER);

        game.shuffleAndDeal();
        //testing
        game.getPlayers().get(0).setPlayerRank(CHAMPION_KNIGHT);
        game.getPlayers().get(1).setPlayerRank(KNIGHT_OF_THE_ROUND_TABLE);
        update();
        //gameLoop();
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