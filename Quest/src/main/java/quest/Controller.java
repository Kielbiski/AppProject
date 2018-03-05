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


import static java.lang.System.exit;
import static quest.Rank.CHAMPION_KNIGHT;
import static quest.Rank.KNIGHT_OF_THE_ROUND_TABLE;
//TO DO IN ORDER OF IMPORTANCE:
//clear warnings from git

//clear board of quest after completion
//check if allies are working in quest
//display quest winner[DONE]
//implement discard pile for adventure and story cards
//events?
//tournaments?
//ask jermey if he implemented funciton to chekc sponsor eligibility


//clear weapons AFTER{seems to work}
//setflowpane to invisible


enum CardBehaviour {SPONSOR, QUEST_MEMBER, BID, DEFAULT}


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
    private Button continueButton;
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
    private Button nextTurnButton;
    @FXML
    private HBox tableHbox;

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

        imgView.setOnDragDetected((MouseEvent event) -> {
            selectedAdventureCard = card;
            Dragboard db = imgView.startDragAndDrop(TransferMode.MOVE);
            //db.setDragView(imgView.getImage());
            ClipboardContent content = new ClipboardContent();
            // Store node ID in order to know what is dragged.
            content.putString(imgView.getParent().getId());
            db.setContent(content);
            event.consume();
        });



//        imgView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//            if(currentBehaviour == CardBehaviour.QUEST_MEMBER){
//                activePlayer.addCardToTable(card);
//                activePlayer.removeCardFromHand(card);
//                update();
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
    public void onTableDragOver(DragEvent event){
        Dragboard db = event.getDragboard();
        if (db.hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }
    public void onTableDragDropped(DragEvent event){
        Dragboard db = event.getDragboard();
        // Get item id here, which was stored when the drag started.
        boolean success = false;
        // If this is a meaningful drop...
        if (db.hasString()) {
            if(db.getString().equals(cardsHbox.getId())) {
                if (activePlayer.isValidDrop(selectedAdventureCard)){
                    if (currentBehaviour == CardBehaviour.QUEST_MEMBER) {
                        if (!(selectedAdventureCard instanceof Foe)) {
                            activePlayer.addCardToTable(selectedAdventureCard);
                            activePlayer.removeCardFromHand(selectedAdventureCard);
                            success = true;
                        }
                    }
                    else if (currentBehaviour == CardBehaviour.BID) {

                    }
                }
                else{
                    success = false;
                }

            }
        }
        event.setDropCompleted(success);
        update();
        event.consume();

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
        stagePane.getStyleClass().add("eventStage");
        stagePane.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            // Get item id here, which was stored when the drag started.
            boolean success = false;
            // If this is a meaningful drop...
            if (db.hasString()) {
                if(db.getString().equals(cardsHbox.getId())) {
                    if (game.isValidDrop(selectedAdventureCard, stageIndex)) {
                        game.addToPotentialStage(selectedAdventureCard, stageIndex);
                        game.getSponsor().removeCardFromHand(selectedAdventureCard);
                        success = true;
                    }
                    else{
                        success = false;
                    }
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
                event.acceptTransferModes(TransferMode.MOVE);
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
        tableHbox.getChildren().clear();
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
        //Hbox hand display card images
        ArrayList<ImageView> handImgViews = new ArrayList<>();
        ArrayList<AdventureCard> playerHand = activePlayer.getCardsInHand();
        playerHand.sort(Comparator.comparing(object2 -> object2.getClass().getName()));
        playerHand.sort(Comparator.comparing(object -> object.getClass().getSuperclass().getName()));
        for (AdventureCard card : playerHand) {
            ImageView imgView = createAdventureCardImageView(card);
            imgView.setImage(getCardImage(card.getImageFilename()));
            handImgViews.add(imgView);
        }
        cardsHbox.getChildren().addAll(handImgViews);

        ArrayList<ImageView> tableImgViews = new ArrayList<>();
        ArrayList<AdventureCard> playerTableCards = activePlayer.getCardsOnTable();
        if(playerTableCards != null) {
            playerTableCards.sort(Comparator.comparing(object2 -> object2.getClass().getName()));
            playerTableCards.sort(Comparator.comparing(object -> object.getClass().getSuperclass().getName()));
            for (AdventureCard card : playerTableCards) {
                ImageView imgView = createAdventureCardImageView(card);
                imgView.setImage(getCardImage(card.getImageFilename()));
                tableImgViews.add(imgView);
            }
            tableHbox.getChildren().addAll(tableImgViews);
        }

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
        else if(currentBehaviour == CardBehaviour.QUEST_MEMBER){
            for(int i =0; i < game.getCurrentQuest().getNumStage(); i++){
                flowPaneArray.get(i).getChildren().clear();
                if(game.getCurrentQuest().getCurrentStage() == game.getCurrentQuest().getStages().get(i)) {
                    for (AdventureCard card : game.getPreQuestStageSetup().get(i)) {
                        ImageView imgView = createAdventureCardImageView(card);
                        imgView.setImage(getCardImage(card.getImageFilename()));
                        imgView.toFront();
                        flowPaneArray.get(i).getChildren().add(imgView);
                    }
                }
                else{
//                    for (AdventureCard card : game.getPreQuestStageSetup().get(i)) {
//                        ImageView imgView = createAdventureCardImageView(card);
//                        //imgView.setImage(getCardImage("FacedownAdventureCard.png"));
//                        flowPaneArray.get(i).getChildren().add(imgView);
//                    }
                }
            }
        }
        else if(currentBehaviour == CardBehaviour.DEFAULT){
            for(int i =0; i < flowPaneArray.size(); i++){
                flowPaneArray.get(i).getStyleClass().clear();
                flowPaneArray.get(i).setStyle(null);
                flowPaneArray.get(i).getChildren().clear();
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
        continueButton.setVisible(true);

        for(int i = 0;i<quest.getNumStage();i++){
            createStagePane(i);
        }
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
        if(currentBehaviour == CardBehaviour.SPONSOR) {
            if (game.validateQuestStages()) {

                for(int i = 0; i<game.getCurrentQuest().getNumStage();i++){
                    game.getCurrentQuest().addStage(game.createStage(game.getPreQuestStageSetup().get(i)));
                }
                game.getCurrentQuest().startQuest();
                currentBehaviour = CardBehaviour.QUEST_MEMBER;
                activePlayer = game.getCurrentQuest().getCurrentPlayer();
                update();
            } else {
                Alert invalidQuest = new Alert(Alert.AlertType.CONFIRMATION, "Please set up a valid quest ", ButtonType.YES);
                DialogPane dialog = invalidQuest.getDialogPane();
                dialog.getStylesheets().add(getClass().getResource("../CSS/Alerts.css").toExternalForm());
                dialog.getStyleClass().add("alertDialogs");
                invalidQuest.setHeaderText("Sponsor " + game.getCurrentStory().getName() + "?");
                invalidQuest.showAndWait();
                for (int i = 0; i < game.getCurrentQuest().getNumStage(); i++) {
                    for (AdventureCard card : game.getPreQuestStageSetup().get(i)) {
                        game.getSponsor().addCardToHand(card);
                    }
                }
                game.resetPotentialStages();
                update();
            }
        }
        else if(currentBehaviour == CardBehaviour.QUEST_MEMBER){
            game.getCurrentQuest().nextTurn();
            if(game.getCurrentQuest().isFinished()){
                if(game.isWinner()){
                    System.out.println("gameover," + game.getWinningPlayers().get(0) + " wins");
                    System.exit(0);
                }
                else{
                    questOver();
                }
            }
            else{
                activePlayer = game.getCurrentQuest().getCurrentPlayer();
            }
            update();
        }


    }
    private void questOver(){
        for(Player player: game.getCurrentQuest().getPlayerList()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, player.getPlayerName() + " won the the Quest!, +" + game.getCurrentQuest().getShields() + " shields", ButtonType.OK);
            DialogPane dialog = alert.getDialogPane();
            dialog.getStylesheets().add(getClass().getResource("../CSS/Alerts.css").toExternalForm());
            dialog.getStyleClass().add("alertDialogs");
            alert.showAndWait();
        }
        game.getPreQuestStageSetup().clear();
        game.clearQuest();
        game.setSponsor(null);
        currentBehaviour = CardBehaviour.DEFAULT;
        nextTurnButton.setVisible(true);
        continueButton.setVisible(false);
        update();
    }

    public void nextTurnAction(ActionEvent event){
        currentTurnPlayer = game.getPlayers().get(currentPlayerIndex);
        activePlayer = game.getPlayers().get(currentPlayerIndex);
        storyDeckImg.setDisable(false);
        nextTurnButton.setDisable(true);
        update();
    }

    public void storyDeckDraw(MouseEvent event){

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
                    nextTurnButton.setVisible(false);
                    continueButton.setVisible(true);
                    break;
                }
            }
            if(game.getSponsor() == null){
                activePlayer = currentTurnPlayer;
                nextTurnButton.setVisible(true);
                continueButton.setVisible(false);
            }
        } else if (game.getCurrentStory() instanceof Event) {
            nextTurnButton.setVisible(true);
            System.out.println("Event");
        } else if (game.getCurrentStory() instanceof Tournament) {
            nextTurnButton.setVisible(true);
            System.out.println("Tournament");
        }
        currentPlayerIndex = nextPlayerIndex(currentPlayerIndex);
        //activePlayer = game.getPlayers().get(currentPlayerIndex);
        nextTurnButton.setDisable(false);
        storyDeckImg.setDisable(true);
        update();

    }

    public void initialize() {
        setPlayerNames();
        currentBehaviour = CardBehaviour.DEFAULT;
        currentTurnPlayer = game.getPlayers().get(0);
        activePlayer = game.getPlayers().get(0);
        playerStatsVbox.setSpacing(5);
        playerStatsVbox.setAlignment(Pos.TOP_RIGHT);
        cardsHbox.setAlignment(Pos.BASELINE_CENTER);
        game.shuffleAndDeal();
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