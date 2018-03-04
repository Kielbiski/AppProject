package quest;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.fxml.FXML;
import javafx.scene.text.TextAlignment;
import sun.awt.image.BufImgVolatileSurfaceManager;

import javax.swing.*;
import java.awt.font.ImageGraphicAttribute;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;


import static quest.Rank.CHAMPION_KNIGHT;
import static quest.Rank.KNIGHT_OF_THE_ROUND_TABLE;

//must check tomake sure a player can actually sponsor a quest
//must add button to advance turn after player draws card



enum CardBehaviour {SPONSOR, QUEST_MEMBER, BID}


public class Controller {

    private Model game = new Model();
    private String resourceFolderPath = "src/main/resources/Cards/";
    private Player activePlayer;
    private Player currentTurnPlayer;
    private int NUM_PLAYERS = 4;
    private int currentPlayerIndex = 0;
    private AdventureCard selectedAdventureCard;
    private CardBehaviour currentBehaviour;

    ///FXML ELEMENTS
    @FXML
    private Button actionButton;
    @FXML
    private ImageView storyDeckImg;
    @FXML
    private ImageView activeStoryImg;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private Label currentTurnLabel;
    @FXML
    private HBox cardsHbox;
    @FXML
    private VBox playerStatsVbox;
    @FXML
    private ImageView currentCardImage;
    @FXML
    private GridPane stagesGridPane;
    @FXML

    private ArrayList<FlowPane> flowPaneArray = new ArrayList<>();






    private ImageView createAdventureCardImageView(AdventureCard card){
        ImageView imgView = new ImageView();
        imgView.setPreserveRatio(true);
        imgView.setFitHeight(75);
        // ScaleTransition st = new ScaleTransition(Duration.millis(2000), imgView);
        //selectedAdventureCard = card;
        imgView.getStyleClass().add("image-view-hand");
        ImageView defaultImage = new ImageView();
        imgView.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            currentCardImage.setImage(imgView.getImage());
            currentCardImage.setPreserveRatio(true);
            currentCardImage.setFitHeight(190);
            currentCardImage.toBack();

        });
        imgView.addEventHandler(MouseEvent.MOUSE_EXITED, event -> currentCardImage.setImage(getCardImage("FacedownAdventure.png")));
        if(currentBehaviour == CardBehaviour.SPONSOR) {
            imgView.setOnDragDetected((MouseEvent event) -> {
                selectedAdventureCard = card;
                Dragboard db = imgView.startDragAndDrop(TransferMode.MOVE);
                db.setDragView(imgView.getImage());
                ClipboardContent content = new ClipboardContent();
                // Store node ID in order to know what is dragged.
                content.putString(imgView.getParent().getId());
                db.setContent(content);
                event.consume();
            });
        }
//        imgView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//            if(currentBehaviour == CardBehaviour.SPONSOR){
//                game.getSponsor().removeCardFromHand(selectedAdventureCard);
//                game.getSponsor().addCardToTable(selectedAdventureCard);
//                System.out.println(game.getSponsor().getCardsOnTable());
//            }
//            else if(currentBehaviour == CardBehaviour.QUEST_MEMBER){
//
//            }
//            else if(currentBehaviour ==CardBehaviour.BID){
//
//            }
//            imgView.setStyle(
//                    "-fx-border-color: #ff0000;\n" +
//                            " -fx-border-width: 10;" +
//                            "-fx-border-style: solid;\n");
//        });
        return imgView;
    }

    private ImageView createStoryCardImageView(){
        ImageView imgView = new ImageView();
        imgView.setPreserveRatio(true);
        imgView.setFitHeight(100);
        imgView.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> imgView.setFitHeight(300));
        imgView.addEventHandler(MouseEvent.MOUSE_EXITED, event -> imgView.setFitHeight(100));
        return imgView;
    }

    private void createStagePane(int stageIndex){
        FlowPane stagePane = new FlowPane();
        stagePane.setStyle("-fx-border-color: white");
        stagePane.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            // Get item id here, which was stored when the drag started.
            boolean success = false;
            // If this is a meaningful drop...
            if (db.hasString()) {
                if(db.getString().equals(cardsHbox.getId())) {
                    game.addToPotentialStage(selectedAdventureCard, stageIndex);
                    game.getSponsor().removeCardFromHand(selectedAdventureCard);
                    success = true;
                }
//                else{
//                   // game.removeFromPotentialStage(selectedAdventureCard,);
//                    game.addToPotentialStage(selectedAdventureCard, stageIndex);
//                    success = true;
//                }
            }
            event.setDropCompleted(success);
            update();
            event.consume();
        });
        stagePane.setOnDragOver(event ->{
            Dragboard db = event.getDragboard();
            if (db.hasString()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });
        flowPaneArray.add(stagePane);
        stagesGridPane.add(stagePane,stageIndex,0);
    }

    private void update() {
        //Vbox display player data
        ArrayList<Player> currentPlayers = game.getPlayers();
        playerStatsVbox.getChildren().clear();
        cardsHbox.getChildren().clear();
        String currentTurnLabelCSS;

        currentTurnLabelCSS = "-fx-border-color: #d6d6d6;\n" +
                "-fx-border-insets: 5;\n" +
                "-fx-border-width: 4;\n" +
                "-fx-border-style: solid;\n" +
                "-fx-border-radius: 10;\n" +
                "-fx-padding: 10";
        currentTurnLabel.setStyle(currentTurnLabelCSS);
        currentTurnLabel.setTextAlignment(TextAlignment.CENTER);
        currentTurnLabel.setMinWidth(Region.USE_PREF_SIZE);
        currentTurnLabel.setText("It is " + currentTurnPlayer.getPlayerName() + "'s turn.");

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
        ArrayList<AdventureCard> playerHand = activePlayer.getCardsInHand();
        playerHand.sort(Comparator.comparing(object2 -> object2.getClass().getName()));
        playerHand.sort(Comparator.comparing(object -> object.getClass().getSuperclass().getName()));
        for (AdventureCard card : playerHand) {
            ImageView imgView = createAdventureCardImageView(card);
            imgView.setImage(getCardImage(card.getImageFilename()));
            imgViews.add(imgView);
        }
        cardsHbox.getChildren().addAll(imgViews);

        if(currentBehaviour == CardBehaviour.SPONSOR) {
            for(int i =0; i < game.getCurrentQuest().getNumStage(); i++){
                flowPaneArray.get(i).getChildren().clear();
                for (AdventureCard card : game.getPreQuestStageSetup().get(i)) {
                    ImageView imgView = createAdventureCardImageView(card);
                    imgView.setImage(getCardImage(card.getImageFilename()));
                    imgView.toFront();
                    flowPaneArray.get(i).getChildren().add(imgView);
                }
            }
        }

    }

    private boolean isGameOver(){
        ArrayList<Player> winningPlayers = getWinningPlayers(game);
        if(winningPlayers.size() > 0){
            for(Player winningPlayer : winningPlayers) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, winningPlayer.getPlayerName() + " won the game!", ButtonType.OK);
                DialogPane dialog = alert.getDialogPane();
                dialog.getStylesheets().add(getClass().getResource("../CSS/Alerts.css").toExternalForm());
                dialog.getStyleClass().add("alertDialogs");
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

    private void performQuest(Player sponsor, Quest quest) {
        game.setSponsor(sponsor);
        game.setCurrentQuest(quest);
        quest.setSponsor(sponsor);
        addQuestPlayers(quest);
        activePlayer = sponsor;
        currentBehaviour = CardBehaviour.SPONSOR;
        actionButton.setVisible(true);

        for(int i = 0;i<quest.getNumStage();i++){
            createStagePane(i);
        }
        //ArrayList<AdventureCard> sponsorCards = new ArrayList<>();
//            if(validCardOrdering()){//insert condition here to check that stages are in ascending order, no duplicate weapons, etc.)
//                sponsorCards = sponsor.getCardsOnTable();
//                break;
//            } else {
//                for(AdventureCard card : sponsor.getCardsOnTable()) {
//                    sponsor.addCardToHand(card);
//                    sponsor.removeCardFromTable(card);
//                }
        }
    private void addQuestPlayers(Quest currentQuest){
        ArrayList<Player> questPlayers = new ArrayList<>();
        for(int i = 0; i < NUM_PLAYERS; i++){
            if(game.getPlayers().get(i) != game.getSponsor()) {
                activePlayer = game.getPlayers().get(i);
                update();
                Alert questAlert = new Alert(Alert.AlertType.CONFIRMATION, game.getPlayers().get(i).getPlayerName() + " would you like to join " + currentQuest.getName() + " ?", ButtonType.YES, ButtonType.NO);
                questAlert.setHeaderText("Join " + game.getCurrentStory().getName() + "?");
                DialogPane dialog = questAlert.getDialogPane();
                dialog.getStylesheets().add(getClass().getResource("../CSS/Alerts.css").toExternalForm());
                dialog.getStyleClass().add("alertDialogs");
                questAlert.showAndWait();
                if (questAlert.getResult() == ButtonType.YES) {
                    questPlayers.add(game.getPlayers().get(i));
                }
            }
        }
        currentQuest.setPlayerList(questPlayers);
    }
//
//    for stage in:
//        alert with foe/weapon/test per stage
//    check if bp is greater with successive stages

    private int nextPlayerIndex(int index){
        int nextIndex = index;
        if(nextIndex >= 3){
            nextIndex = 0;
        } else{
            nextIndex++;
        }
        return nextIndex;
    }
    public void continueAction(ActionEvent event){
        if(game.validateQuestStages()){
            System.out.println("true");
        }
        else{
            Alert invalidQuest = new Alert(Alert.AlertType.CONFIRMATION,  "Please set up a valid quest " ,ButtonType.YES);
            DialogPane dialog = invalidQuest.getDialogPane();
            dialog.getStylesheets().add(getClass().getResource("../CSS/Alerts.css").toExternalForm());
            dialog.getStyleClass().add("alertDialogs");
            invalidQuest.setHeaderText("Sponsor " + game.getCurrentStory().getName() + "?");
            invalidQuest.showAndWait();
            for(int i =0; i < game.getCurrentQuest().getNumStage(); i++){
                for (AdventureCard card : game.getPreQuestStageSetup().get(i)) {
                   game.getSponsor().addCardToHand(card);
                }
            }
            game.resetPotentialStages();
            update();
        }

    }

    public void storyDeckDraw(MouseEvent event){
        currentTurnPlayer = game.getPlayers().get(currentPlayerIndex);
        activePlayer = game.getPlayers().get(currentPlayerIndex);

        game.drawStoryCard();
        System.out.println("storyDeckDraw(): " + game.getCurrentStory().getName());
        //activeStoryImg = createStoryCardImageView();
        activeStoryImg.setImage(getCardImage(game.getCurrentStory().getImageFilename()));
        update();

        ArrayList<Player> currentPlayerOrder = new ArrayList<>();
        int currentTurn = game.getPlayers().indexOf(activePlayer);

        for(int i = 0; i < NUM_PLAYERS; i++){
            currentPlayerOrder.add(game.getPlayers().get(currentTurn));
            currentTurn = nextPlayerIndex(currentTurn);
        }

        if (game.getCurrentStory() instanceof Quest) {
            Player sponsor;
            for (Player player : currentPlayerOrder) {//////////////////////////////////////
                activePlayer = player;
                update();
                Alert sponsorQuest = new Alert(Alert.AlertType.CONFIRMATION, player.getPlayerName() + ", would you like to sponsor " + game.getCurrentStory().getName() + "?", ButtonType.YES, ButtonType.NO);
                DialogPane dialog = sponsorQuest.getDialogPane();
                dialog.getStylesheets().add(getClass().getResource("../CSS/Alerts.css").toExternalForm());
                dialog.getStyleClass().add("alertDialogs");
                sponsorQuest.setHeaderText("Sponsor " + game.getCurrentStory().getName() + "?");
                sponsorQuest.showAndWait();
                if (sponsorQuest.getResult() == ButtonType.YES) {
                    sponsor = game.getPlayers().get(currentPlayerIndex);
                    game.setSponsor(sponsor);
                    performQuest(sponsor, (Quest) game.getCurrentStory());
                    break;
                }
                storyDeckImg.setDisable(true);
            }
        } else if (game.getCurrentStory() instanceof Event) {
            System.out.println("Event");
        } else if (game.getCurrentStory() instanceof Tournament) {
            System.out.println("Tournament");
        }
        currentPlayerIndex = nextPlayerIndex(currentPlayerIndex);
        //activePlayer = game.getPlayers().get(currentPlayerIndex);
        update();

    }

    public void initialize() {
        setPlayerNames();
        currentTurnPlayer = game.getPlayers().get(0);
        activePlayer = game.getPlayers().get(0);
        playerStatsVbox.setSpacing(5);
        playerStatsVbox.setAlignment(Pos.TOP_RIGHT);
        cardsHbox.setAlignment(Pos.BASELINE_CENTER);


        game.shuffleAndDeal();
        //testing
        game.getPlayers().get(0).setPlayerRank(CHAMPION_KNIGHT);
        game.getPlayers().get(1).setPlayerRank(KNIGHT_OF_THE_ROUND_TABLE);

        //storyDeckImg.setOnMouseClicked(this::storyDeckDraw);
        update();

    }


    private void setPlayerNames(){
        //cardsHbox.prefWidthProperty().bind(Stage.widthProperty().multiply(0.80));
        for(int i =0; i < NUM_PLAYERS; i++){
            TextInputDialog dialog = new TextInputDialog("Enter Name");
            dialog.setTitle("Set Player name");
            dialog.setHeaderText("Player " + (i+1) + " enter your name");
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("../CSS/Alerts.css").toExternalForm());
            dialogPane.getStyleClass().add("alertDialogs");
            // dialog.setContentText("Please enter your name:");

            Optional<String> result = dialog.showAndWait();

            // The Java 8 way to get the response value (with lambda expression).
            result.ifPresent(name -> game.addPlayer(name));
        }
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