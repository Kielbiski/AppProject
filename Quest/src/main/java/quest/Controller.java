package quest;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.fxml.FXML;
import javafx.scene.text.TextAlignment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;


import static java.lang.System.exit;

enum Behaviour {SPONSOR, QUEST_MEMBER, BID, DISCARD, CALL_TO_ARMS, TOURNAMENT, DEFAULT}

public class Controller implements PropertyChangeListener {

    private static final Logger logger = LogManager.getLogger(App.class);
    private Model game = new Model();
    private String resourceFolderPath = "src/main/resources/Cards/";
    private Player activePlayer;
    private Player currentTurnPlayer;
    private int NUM_PLAYERS = 0;
    private int currentPlayerIndex = 0;
    private AdventureCard selectedAdventureCard;
    private  Behaviour previousBehaviour;
    private Behaviour currentBehaviour;
    private int callToArmsFoes = 0;

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
    @FXML
    private Pane discardPane;

    private ArrayList<FlowPane> flowPaneArray = new ArrayList<>();

    private void print(String stringToPrint){
        System.out.println(stringToPrint);
    }

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
            db.setDragView(getCardImage("SmallAdventureCard.png"));
            ClipboardContent content = new ClipboardContent();
            // Store node ID in order to know what is dragged.
            content.putString(imgView.getParent().getId());
            db.setContent(content);
            event.consume();
        });

        return imgView;
    }

    public void onTableDragOver(DragEvent event){
        Dragboard db = event.getDragboard();
        if (db.hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

    public void onMouseEnterStory(MouseEvent event){
        if(game.getCurrentStory() !=null) {
            currentCardImage.setImage(getCardImage(game.getCurrentStory().getImageFilename()));
            currentCardImage.setPreserveRatio(true);
            currentCardImage.setFitHeight(190);
            currentCardImage.toBack();
        }
    }

    public void onMouseExitStory(MouseEvent event){
        currentCardImage.setImage(getCardImage("FacedownAdventure.png"));
        currentCardImage.setPreserveRatio(true);
        currentCardImage.setFitHeight(190);
        currentCardImage.toBack();
    }

    public void onTableDragDropped(DragEvent event){
        Dragboard db = event.getDragboard();
        // Get item id here, which was stored when the drag started.
        boolean success = false;
        // If this is a meaningful drop...
        if (db.hasString()) {
            if(db.getString().equals(cardsHbox.getId())) {
                if (activePlayer.isValidDrop(selectedAdventureCard)){
                    if (currentBehaviour == Behaviour.QUEST_MEMBER) {
                        if (!(selectedAdventureCard instanceof Foe)) {
                            activePlayer.addCardToTable(selectedAdventureCard);
                            activePlayer.removeCardFromHand(selectedAdventureCard);
                            success = true;
                        }
                    } else if(currentBehaviour == Behaviour.DISCARD){
                        if (!(selectedAdventureCard instanceof Foe) && !(selectedAdventureCard instanceof Weapon)) {
                            activePlayer.addCardToTable(selectedAdventureCard);
                            activePlayer.removeCardFromHand(selectedAdventureCard);
                            if(activePlayer.isHandFull()){
                                handFull(activePlayer);
                            }
                            else{
                                currentBehaviour = previousBehaviour;
                                previousBehaviour = null;
                                if(currentBehaviour == Behaviour.DEFAULT) {
                                    discardPane.setVisible(false);
                                    nextTurnButton.setVisible(true);
                                    nextTurnButton.setDisable(false);
                                    update();
                                }
                            }
                            success = true;
                        }
                    }
                    else if (currentBehaviour == Behaviour.BID) {
                        System.out.println("Bid.");
                    }
                    else if(currentBehaviour == Behaviour.TOURNAMENT){
                        if (!(selectedAdventureCard instanceof Foe)) {
                            activePlayer.addCardToTournamnet(selectedAdventureCard);
                            activePlayer.removeCardFromHand(selectedAdventureCard);
                            success = true;
                        }
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

    public void onDiscardDragOver(DragEvent event){
        Dragboard db = event.getDragboard();
        if (db.hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

    public void onDiscardDragDropped(DragEvent event){
        Dragboard db = event.getDragboard();
        // Get item id here, which was stored when the drag started.
        boolean success = false;
        // If this is a meaningful drop...
        if (db.hasString()) {
            if(db.getString().equals(cardsHbox.getId())) {
                    if (currentBehaviour == Behaviour.DISCARD) {
                            activePlayer.removeCardFromHand(selectedAdventureCard);
                            if(activePlayer.isHandFull()){
                                handFull(activePlayer);
                            }
                            else{
                                currentBehaviour = previousBehaviour;
                                previousBehaviour = null;
                                discardPane.setVisible(false);
                                if(currentBehaviour==Behaviour.DEFAULT) {
                                    nextTurnButton.setVisible(true);
                                    nextTurnButton.setDisable(false);
                                }
                                update();
                            }
                            success = true;
                    }
                    else if(currentBehaviour == Behaviour.CALL_TO_ARMS){
                        if (selectedAdventureCard instanceof Weapon){
                            activePlayer.removeCardFromHand((selectedAdventureCard));
                            currentBehaviour = previousBehaviour;
                            previousBehaviour = null;
                            discardPane.setVisible(false);
                            nextTurnButton.setDisable(false);
                            success=true;
                            update();
                        }
                        else {
                            boolean hasWeapon = false;
                            int foeCount =0;
                            for (AdventureCard card : activePlayer.getCardsInHand()) {
                                if (card instanceof Weapon) {
                                    hasWeapon = true;
                                }
                                if (card instanceof Foe) {
                                    foeCount++;
                                }
                            }
                            if (hasWeapon) {
                                success = false;
                            }
                            else if (callToArmsFoes < 2 && selectedAdventureCard instanceof Foe){
                                activePlayer.removeCardFromHand((selectedAdventureCard));
                                callToArmsFoes++;
                                if(callToArmsFoes==2||(foeCount<2 && callToArmsFoes==foeCount)){
                                    currentBehaviour = previousBehaviour;
                                    previousBehaviour = null;
                                    discardPane.setVisible(false);
                                    callToArmsFoes=0;
                                    nextTurnButton.setDisable(false);
                                    success=true;
                                    update();
                                }
                            }
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
        stagePane.setId(Integer.toString(stageIndex));
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
                }
                else
                    {
                        for(int i=0;i<game.getCurrentQuest().getNumStage();i++){
                            if(db.getString().equals(Integer.toString(i))){
                                if (game.isValidDrop(selectedAdventureCard, stageIndex)) {
                                    game.addToPotentialStage(selectedAdventureCard, stageIndex);
                                    game.removeFromPotentialStage(selectedAdventureCard,i);
                                    success = true;
                                }
                            }
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
        currentTurnLabel.setTextAlignment(TextAlignment.CENTER);
        currentTurnLabel.setMinWidth(Region.USE_PREF_SIZE);
        currentTurnLabel.setStyle("-fx-border-color: #dd3b3b;\n" +
                "-fx-background-color: rgba(0,0,0,0.8);\n"+
            "-fx-border-insets: 5;\n" +
            "-fx-border-width: 4;\n" +
            "-fx-border-style: solid;\n" +
            "-fx-padding: 10;\n" +
            "-fx-translate-x: -80;");
        currentTurnLabel.setText("It is " + currentTurnPlayer.getPlayerName() + "'s turn.");

        for (Player player : currentPlayers) {
            Label playerLabel = new Label();
            String labelCSS;
            if (player == activePlayer) {
                labelCSS = "-fx-border-color: #f44242;\n";
            } else {
                labelCSS = "-fx-border-color: #aaaaaa;\n";
            }
            labelCSS += "-fx-background-color: rgba(0,0,0,0.8);\n"+
                    "-fx-border-insets: 5;\n" +
                    "-fx-border-width: 4;\n" +
                    "-fx-border-style: solid;\n" +
                    "-fx-padding: 10";

            playerLabel.setStyle(labelCSS);
            playerLabel.setTextAlignment(TextAlignment.RIGHT);
            playerLabel.setMinWidth(Region.USE_PREF_SIZE);
            playerLabel.setText(player.getPlayerName() + "\n" +
                    "" + player.stringifyRank() + "\n" +
                    "" + player.getShields() + " Shields\n" +
                    "" + player.getNumCardsInHand() + " Cards");
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
        }

        ArrayList<AdventureCard> playerTournamentCards = activePlayer.getTournamentCards();
        if(playerTournamentCards != null) {
            playerTournamentCards.sort(Comparator.comparing(object2 -> object2.getClass().getName()));
            playerTournamentCards.sort(Comparator.comparing(object -> object.getClass().getSuperclass().getName()));
            for (AdventureCard card : playerTournamentCards) {
                ImageView imgView = createAdventureCardImageView(card);
                imgView.setImage(getCardImage(card.getImageFilename()));
                tableImgViews.add(imgView);
            }
        }
        if(playerTournamentCards != null||playerTableCards != null) {
            tableHbox.getChildren().addAll(tableImgViews);
        }


        if(currentBehaviour == Behaviour.SPONSOR) {
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
        else if(currentBehaviour == Behaviour.QUEST_MEMBER){
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
                    boolean hasMerlin =false;
                    for(Card card : activePlayer.getCardsInHand()){
                        if (card instanceof Merlin) hasMerlin = true;
                    }
//                    if(hasMerlin) {
//                        for (AdventureCard card : game.getPreQuestStageSetup().get(i+1)) {
//                            ImageView imgView = createAdventureCardImageView(card);
//                            imgView.setImage(getCardImage(card.getImageFilename()));
//                            imgView.toFront();
//                            flowPaneArray.get(i+1).getChildren().add(imgView);
//                        }
//                    }
                }
            }
        }
    }

    //ALERTS
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean yesNoAlert(String text, String headerText){
        Alert questAlert = new Alert(Alert.AlertType.CONFIRMATION, text, ButtonType.YES, ButtonType.NO);
        questAlert.setHeaderText(headerText);
        DialogPane dialog = questAlert.getDialogPane();
        dialog.getStylesheets().add(getClass().getResource("../CSS/Alerts.css").toExternalForm());
        dialog.getStyleClass().add("alertDialogs");
        questAlert.showAndWait();
        return (questAlert.getResult() == ButtonType.YES);
    }

    private void okAlert(String contentText, String headerText){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, contentText, ButtonType.OK);
        DialogPane dialog = alert.getDialogPane();
        alert.setHeaderText(headerText);
        dialog.getStylesheets().add(getClass().getResource("../CSS/Alerts.css").toExternalForm());
        dialog.getStyleClass().add("alertDialogs");
        alert.showAndWait();
    }

    private boolean isGameOver(){
        ArrayList<Player> winningPlayers = getWinningPlayers(game);
        if(winningPlayers.size() > 0){
            for(Player winningPlayer : winningPlayers) {
                okAlert(winningPlayer.getPlayerName() + " won the game!", "Winner.");
            }
            return true;
        }
        return false;

    }

//    private ArrayList<Player> finalTournament(ArrayList<Player> tournamentParticipants) {
//        Tournament knightsOfTheRoundTableTournament = new Tournament("Knights of the Round Table Tournament", "", tournamentParticipants);
//        return knightsOfTheRoundTableTournament.getTournamentWinner();
//    }

    private ArrayList<Player> getWinningPlayers(Model model) {
        ArrayList<Player> winningPlayers = new ArrayList<>();
//        ArrayList<Player> knightsOfTheRoundTable;
//        knightsOfTheRoundTable = new ArrayList<>();
//        for (Player player : model.getPlayers()) {
//            if (player.getPlayerRank() == KNIGHT_OF_THE_ROUND_TABLE) {
//                knightsOfTheRoundTable.add(player);
//            }
//        }
//        if (knightsOfTheRoundTable.isEmpty()){
//        } else (knightsOfTheRoundTable.size() == 1) {
//            winningPlayers = knightsOfTheRoundTable;
//        }
////        else {
////            //winningPlayers = finalTournament(knightsOfTheRoundTable);
////        }
        return winningPlayers;
    }

    //TURNS
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private int nextPlayerIndex(int index){
        int nextIndex = index;
        if(nextIndex >= NUM_PLAYERS-1){
            nextIndex = 0;
        } else{
            nextIndex++;
        }
        return nextIndex;
    }

    public void continueAction(){
        if(currentBehaviour == Behaviour.SPONSOR) {
            if (game.validateQuestStages()) {

                for(int i = 0; i<game.getCurrentQuest().getNumStage();i++){
                    game.getCurrentQuest().addStage(game.createStage(game.getPreQuestStageSetup().get(i)));
                }
                game.getCurrentQuest().startQuest();
                if(!game.getCurrentQuest().isInTest()){
                    currentBehaviour = Behaviour.QUEST_MEMBER;
                    setActivePlayer(game.getCurrentQuest().getCurrentPlayer());
                    update();
                }
            } else {
                okAlert("Please set up a valid quest ","Error in quest stages.");
                for (int i = 0; i < game.getCurrentQuest().getNumStage(); i++) {
                    for (AdventureCard card : game.getPreQuestStageSetup().get(i)) {
                        game.getSponsor().addCardToHand(card);
                    }
                }
                game.resetPotentialStages();
                update();
            }
        }
        else if(currentBehaviour == Behaviour.QUEST_MEMBER){
            game.getCurrentQuest().nextTurn();
            if(game.getCurrentQuest().isFinished()){
                if(game.isWinner()){
                    System.out.println("Game over," + game.getWinningPlayers().get(0) + " wins");
                    System.exit(0);
                }
                else{
                    questOver();
                }
            }
            else{
               setActivePlayer(game.getCurrentQuest().getCurrentPlayer());
            }
            update();
        }
        else if(currentBehaviour == Behaviour.TOURNAMENT){
            game.getCurrentTournament().nextTurn();
            if(game.getCurrentTournament().isTournamentOver()){
                if(game.isWinner()){
                    System.out.println("Game over," + game.getWinningPlayers().get(0) + " wins");
                    System.exit(0);
                }
                else{
                    tournamentOver();
                }
            }
            else{
                setActivePlayer(game.getCurrentTournament().getCurrentPlayer());
            }
            update();
        }
        else if(currentBehaviour == Behaviour.BID){
        }
    }

    public void nextTurnAction(){
        currentTurnPlayer = game.getPlayers().get(currentPlayerIndex);
        setActivePlayer(game.getPlayers().get(currentPlayerIndex));
        storyDeckImg.setDisable(false);
        nextTurnButton.setDisable(true);
        update();
    }

    //HAND AND DECK
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private void handFull(Player player){
        if(player == game.getCurrentPlayer()) {
            previousBehaviour = currentBehaviour;
            currentBehaviour = Behaviour.DISCARD;
            nextTurnButton.setDisable(true);
            okAlert(player.getPlayerName() + ", you must play or discard a card.", "Hand Full!");
            discardPane.setVisible(true);
        }
    }

    public void storyDeckDraw(){

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
            game.setCurrentQuest((Quest) game.getCurrentStory());
            questDraw(currentPlayerOrder);
        } else if (game.getCurrentStory() instanceof Event) {
            nextTurnButton.setVisible(true);
            System.out.println(game.getCurrentStory().getName());
            Event gameEvent = (Event) game.getCurrentStory();
            callEventEffect(gameEvent);
        } else if (game.getCurrentStory() instanceof Tournament) {
            nextTurnButton.setVisible(true);
            game.setCurrentTournament((Tournament)game.getCurrentStory());
            performTournament(currentPlayerOrder, game.getCurrentTournament());
            nextTurnButton.setDisable(false);
        }
        currentPlayerIndex = nextPlayerIndex(currentPlayerIndex);
        //activePlayer = game.getPlayers().get(currentPlayerIndex);
        storyDeckImg.setDisable(true);
        update();
    }

    //QUESTS
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private void questDraw(ArrayList<Player> currentPlayerOrder) {
        Player sponsor;
        for (Player player : currentPlayerOrder) {//////////////////////////////////////
            setActivePlayer(player);
            update();
            int validCardCount = 0;
            for(AdventureCard adventureCard : player.getCardsInHand()){
                if((adventureCard instanceof Foe) || (adventureCard instanceof Test)) {
                    validCardCount++;
                }
            }
            if(validCardCount < game.getCurrentQuest().getNumStage()){
                okAlert(player.getPlayerName() + ", you cannot sponsor " + game.getCurrentStory().getName() + "!", "Sponsorship failed.");
            } else {
                boolean alertResult = yesNoAlert(player.getPlayerName() + ", would you like to sponsor " + game.getCurrentStory().getName() + "?", "Sponsor " + game.getCurrentStory().getName() + "?");
                if (alertResult) {
                    sponsor = activePlayer;
                    game.setSponsor(sponsor);
                    performQuest(sponsor, (Quest) game.getCurrentStory());
                    nextTurnButton.setVisible(false);
                    continueButton.setVisible(true);
                    break;
                }
            }
        }
        if(game.getSponsor() == null){
            setActivePlayer(currentTurnPlayer);
            nextTurnButton.setVisible(true);
            continueButton.setVisible(false);
        }
    }

    private void questOver(){
        if(game.getCurrentQuest().isWinner()) {
            for (Player player : game.getCurrentQuest().getPlayerList()) {
                if(game.isKingsRecognition()){
                    player.setShields(player.getShields() + 3);
                    game.setKingsRecognition(false);
                }
                okAlert(player.getPlayerName() + " won the the quest, and received " + game.getCurrentQuest().getShields() + " shields!", "Quest won!");
            }
        }
        else{
            okAlert("Quest has no winner.", "Quest failed!");
        }
        int sponsorCardsToDraw=game.getCurrentQuest().getNumStage();
        for(int i =0; i<game.getPreQuestStageSetup().size();i++){
            for(AdventureCard card: game.getPreQuestStageSetup().get(i)){
                sponsorCardsToDraw++;
            }
        }
        for(int i=0;i<sponsorCardsToDraw;i++) {
            game.drawAdventureCard(game.getSponsor());
        }
        stagesGridPane.getChildren().clear();
        flowPaneArray.clear();
        game.getPreQuestStageSetup().clear();
        game.clearQuest();
        setActivePlayer(game.getSponsor());
        game.setSponsor(null);
        if(!game.getCurrentPlayer().handFull) {
            currentBehaviour = Behaviour.DEFAULT;
            nextTurnButton.setVisible(true);
            nextTurnButton.setDisable(false);
        }
        continueButton.setVisible(false);
        update();
    }

    private void performQuest(Player sponsor, Quest quest) {
        quest.addChangeListener(this);
        game.setSponsor(sponsor);
        quest.setSponsor(sponsor);
        setActivePlayer(sponsor);
        currentBehaviour = Behaviour.SPONSOR;
        continueButton.setVisible(true);

        for(int i = 0;i<quest.getNumStage();i++){
            createStagePane(i);
        }
        addQuestPlayers(quest);
        setActivePlayer(sponsor);
    }

    private void performTest(){

        ArrayList<Player> testPlayers = game.getCurrentQuest().getCurrentStage().getParticipatingPlayers();
        ArrayList<Player> testPlayersToRemove = new ArrayList<>();

        int currentHighestBid = 0;
        int minBids = 3;
        if(game.getCurrentStory().equals("Search For The Questing Beast") && ((TestStage)game.getCurrentQuest().getCurrentStage()).getSponsorTestCard().getName().equals("Test Of The Questing Beast")){
            minBids = 4;
        }
        int currentBid=minBids;
        int currentTestPlayerIndex=0;
        int currentNumInTest = testPlayers.size();
        Player currentTestPlayer;

        while(game.getCurrentQuest().isInTest()) {
            currentTestPlayer=testPlayers.get(currentTestPlayerIndex);
            setActivePlayer(currentTestPlayer);
            update();

            if(testPlayers.size()!=1) {
                List<String> choices = new ArrayList<>();
                for (int i = currentBid; i < activePlayer.getNumCardsInHand(); i++) {
                    choices.add(Integer.toString(i + 1));
                }
                choices.add("Drop Out");
                ChoiceDialog<String> dialog = new ChoiceDialog<>(Integer.toString(currentBid), choices);
                dialog.setTitle("Bid.");
                dialog.setHeaderText("Number of cards to bid?");
                dialog.setContentText("Please select the number of cards to bid or 'Drop Out':");
                Optional<String> result = dialog.showAndWait();
                // The Java 8 way to get the response value (with lambda expression).
                String cardsToBid = "Drop Out";
                if (result.isPresent()) {
                    cardsToBid = result.get();
                }
                if (cardsToBid.equals("Drop Out")) {
                    testPlayersToRemove.add(currentTestPlayer);
                } else {
                    currentBid = Integer.parseInt(cardsToBid);
                }
                if (currentBid > currentHighestBid) {
                    currentHighestBid = currentBid;
                }
                currentTestPlayerIndex++;
                if (currentTestPlayerIndex >= currentNumInTest) {
                    currentTestPlayerIndex = 0;
                    for (Player player : testPlayersToRemove) {
                        testPlayers.remove(player);
                    }
                    currentNumInTest = testPlayers.size();
                    testPlayersToRemove.clear();
                }
            }
            else{
                game.getCurrentQuest().setInTest(false);
                testPlayers.get(0).setCurrentBid(currentHighestBid);
                game.getCurrentQuest().setPlayerList(testPlayers);
                okAlert(testPlayers.get(0).getPlayerName() + " won the test, discard your bids", "Test Over");
                currentBehaviour = Behaviour.BID;
                logger.info("Current player with highest bid" + testPlayers.get(0) +" for this testStage." );
            }
        }

    }

    private void addQuestPlayers(Quest currentQuest){
        ArrayList<Player> questPlayers = new ArrayList<>();
        for(int i = 0; i < NUM_PLAYERS; i++){
            if(game.getPlayers().get(i) != game.getSponsor()) {
                activePlayer = game.getPlayers().get(i);
                update();
                if (yesNoAlert("Join " + game.getCurrentStory().getName() +" " + activePlayer.getPlayerName() + "?", "Join quest?")) {
                    questPlayers.add(game.getPlayers().get(i));
                }
            }
        }
        if(questPlayers.size() == 0){
            questOver();
        }
        else {
            currentQuest.setPlayerList(questPlayers);
        }
    }

    //TOURNAMENT
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private void performTournament(ArrayList<Player> currentPlayerOrder, Tournament tournament) {
        currentBehaviour = Behaviour.TOURNAMENT;
        addTournamentPlayers(tournament);
        if(!tournament.isTournamentOver()){
            tournament.setCurrentPlayer(tournament.getPlayerList().get(0));
            activePlayer=tournament.getCurrentPlayer();
            if(tournament.getPlayerList().size()==1){
                tournament.setTournamentOver(true);
                tournament.setWinners(tournament.getPlayerList());
                tournament.rewardWinner(tournament.getPlayerList().get(0));
                tournamentOver();
            }else {
                update();
                nextTurnButton.setVisible(false);
                continueButton.setVisible(true);
            }
        }

    }

    private void addTournamentPlayers(Tournament currentTournament){
        ArrayList<Player> tournamentPlayers = new ArrayList<>();
        for(int i = 0; i < NUM_PLAYERS; i++){
            activePlayer = game.getPlayers().get(i);
            update();
            if(!(activePlayer instanceof AbstractAI)) {
                if (yesNoAlert("Join " + game.getCurrentStory().getName() + " " + activePlayer.getPlayerName() + "?", "Join Tournament?")) {
                    tournamentPlayers.add(game.getPlayers().get(i));
                }
            } else {
                if(((AbstractAI) activePlayer).doIParticipateInTournament(tournamentPlayers, currentTournament.getShields())){
                    tournamentPlayers.add(activePlayer);
                }
            }
        }
        if(tournamentPlayers.size() == 0){
            tournamentOver();
        }
        else {
            currentTournament.setPlayerList(tournamentPlayers);
        }
    }

    private void tournamentOver(){

        for (Player player : game.getCurrentTournament().getWinners()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, player.getPlayerName() + " won the the Tournament, and received " + game.getCurrentTournament().getShields() + " shields!", ButtonType.OK);
            DialogPane dialog = alert.getDialogPane();
            dialog.getStylesheets().add(getClass().getResource("../CSS/Alerts.css").toExternalForm());
            dialog.getStyleClass().add("alertDialogs");
            alert.showAndWait();
        }
        game.getCurrentTournament().setTournamentOver(true);
        game.setCurrentTournament(null);
        currentBehaviour = Behaviour.DEFAULT;
        nextTurnButton.setVisible(true);
        nextTurnButton.setDisable(false);
        continueButton.setVisible(false);
        setActivePlayer(game.getPlayers().get(game.getCurrentTurnIndex()));
        update();
    }


    //EVENTS
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////

    private void callEventEffect(Event event){
        switch (event.getName()) {
            case "Chivalrous Deed":
                event.applyEvent(game.getPlayers(), null);
                nextTurnButton.setDisable(false);
                break;
            case "Court Called To Camelot":
                event.applyEvent(game.getPlayers(), null);
                nextTurnButton.setDisable(false);
                break;
            case "King's Call To Arms":
                event.applyEvent(game.getPlayers(),activePlayer);
                break;
            case "King's Recognition":
                event.applyEvent(null, null);
                nextTurnButton.setDisable(false);
                break;
            case "Plague":
                event.applyEvent(null, activePlayer);
                nextTurnButton.setDisable(false);
                break;
            case "Pox":
                event.applyEvent(game.getPlayers(), activePlayer);
                nextTurnButton.setDisable(false);
                break;

            case "Prosperity Throughout The Realm":
                event.applyEvent(game.getPlayers(), null);
                nextTurnButton.setDisable(false);
                break;
            case "Queen's Favor": {
                event.applyEvent(game.getPlayers(), null);
                nextTurnButton.setDisable(false);
                break;
            }
        }
        System.out.println(event.getName() + " was activated.");
        for(Player player : game.getPlayers()){
            System.out.println(player.getShields());
        }
        logger.info("Successfully called : Event constructor");
    }

    private void callToArms(Player player){
        previousBehaviour =currentBehaviour;
        currentBehaviour = Behaviour.CALL_TO_ARMS;
        update();
        int foeCount =0;
        int weaponCount =0;
        for(AdventureCard card: player.getCardsInHand()){
            if(card instanceof Foe){ foeCount++;}
            if(card instanceof Weapon){ weaponCount++;}
        }
        okAlert(player.getPlayerName() + ", you must discard 1 Weapon. If you have no weapons, you must discard 2 Foes ","Call to Arms");
        if(foeCount==0 && weaponCount==0){
            okAlert("No weapons or foes to discard","Notice:");
            currentBehaviour = previousBehaviour;
        }
        else{
            nextTurnButton.setDisable(true);
            discardPane.setVisible(true);
            update();
        }

    }

    //Player related
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private void runAITurn(AbstractAI ai){
        if(currentBehaviour == Behaviour.DEFAULT){
            game.drawStoryCard();
            activeStoryImg.setImage(getCardImage(game.getCurrentStory().getImageFilename()));
            update();
        }
        ArrayList<Player> currentPlayerOrder = new ArrayList<>();
        int currentTurn = game.getPlayers().indexOf(activePlayer);
        for(int i = 0; i < NUM_PLAYERS; i++){
            currentPlayerOrder.add(game.getPlayers().get(currentTurn));
            currentTurn = nextPlayerIndex(currentTurn);
        }
        ArrayList<Player> noAIPlayers = new ArrayList<>();
        for(Player player : game.getPlayers()){
            if(!(player instanceof AbstractAI)){
                noAIPlayers.add(player);
            }
        }
        if (game.getCurrentStory() instanceof Quest) {
            game.setCurrentQuest((Quest) game.getCurrentStory());
            //BEHAVIOUR HERE
            //questDraw(currentPlayerOrder);
        } else if (game.getCurrentStory() instanceof Event) {
            Event gameEvent = (Event) game.getCurrentStory();
            callEventEffect(gameEvent);
        } else if (game.getCurrentStory() instanceof Tournament) {
            game.setCurrentTournament((Tournament)game.getCurrentStory());
            currentBehaviour = Behaviour.TOURNAMENT;
        }
        if(currentBehaviour == Behaviour.SPONSOR) {
        }
        else if(currentBehaviour == Behaviour.QUEST_MEMBER){
        }
        else if(currentBehaviour == Behaviour.TOURNAMENT){
            print("Before playing");
            print("Cards in hand");
            for(AdventureCard adventureCard : ai.getCardsInHand()){
               print(adventureCard.getName());
            }
            print("Cards on table");
            for(AdventureCard adventureCard : ai.getCardsOnTable()){
                print(adventureCard.getName());
            }
            if(ai.doIParticipateInTournament(game.getPlayers(), game.getCurrentTournament().getShields())){
                ai.playCardsAI(ai.whatIPlay(ai.getCardsInHand(), game.getPlayers(), game.getCurrentTournament().getShields()));
            }
            print("After playing");
            print("Cards in hand");
            for(AdventureCard adventureCard : ai.getCardsInHand()){
                print(adventureCard.getName());
            }
            print("Cards on table");
            for(AdventureCard adventureCard : ai.getCardsOnTable()){
                print(adventureCard.getName());
            }
        }
        currentPlayerIndex = nextPlayerIndex(currentPlayerIndex);
        currentTurnPlayer = game.getPlayers().get(currentPlayerIndex);
        setActivePlayer(game.getPlayers().get(currentPlayerIndex));
        update();
    }

    private void setActivePlayer(Player player){
        activePlayer = player;
        if(activePlayer instanceof AbstractAI){
            runAITurn((AbstractAI) activePlayer);
        }
        game.setCurrentPlayer(player);
        if(player.isHandFull()){
            handFull(player);
        }
    }

    private int getNumberOfPlayers(){
        List<String> choices = new ArrayList<>();
        choices.add("2 Players");
        choices.add("3 Players");
        choices.add("4 Players");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("4 Players", choices);
        dialog.setTitle("Number of Players?");
        dialog.setHeaderText("How many players would you like?");
        dialog.setContentText("Please select number of players:");
        Optional<String> result = dialog.showAndWait();
        // The Java 8 way to get the response value (with lambda expression).
        if (result.isPresent()) {
            String number = result.get();
            switch (number) {
                case "2 Players":
                    return 2;
                case "3 Players":
                    return 3;
                case "4 Players":
                    return 4;
                default:
                    return 0;
            }
        }
        return 0;
    }

    public void initialize() {
        List<String> choices = new ArrayList<>();
        choices.add("Regular");
        choices.add("Boar Hunt");
        choices.add("Modified Boar Hunt");
        choices.add("Strategy 1");
        choices.add("Strategy 2");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Regular", choices);
        dialog.setTitle("Scenario selection.");
        dialog.setHeaderText("Select Scenario");
        dialog.setContentText("Please select the scenario you would like:");
        Optional<String> result = dialog.showAndWait();
        // The Java 8 way to get the response value (with lambda expression).
        String scenario = "Regular";
        if (result.isPresent()) {
            scenario = result.get();
        }

        Stack<AdventureCard> deckOfAdventureCards = game.getDeckOfAdventureCards();
        Collections.shuffle(deckOfAdventureCards);

        switch (scenario){
            case "Regular":
                break;
            case "Boar Hunt":
                for(StoryCard storyCard : game.getDeckOfStoryCards()){
                    if(storyCard instanceof BoarHunt){ //to preserve deck card ratios
                        game.removeFromStoryDeck(storyCard);
                        break;
                    }
                }
                game.addToStoryDeck(new BoarHunt());
                break;
            case "Modified Boar Hunt":
                for(StoryCard storyCard : game.getDeckOfStoryCards()){
                    if(storyCard instanceof BoarHunt){ //to preserve deck card ratios
                        game.removeFromStoryDeck(storyCard);
                        break;
                    }
                }
                game.addToStoryDeck(new BoarHunt());
                break;
            case "Strategy 1":
                for(StoryCard storyCard : game.getDeckOfStoryCards()){
                    if(storyCard instanceof TournamentAtOrkney){ //to preserve deck card ratios
                        game.removeFromStoryDeck(storyCard);
                        break;
                    }
                }
                game.addToStoryDeck(new TournamentAtOrkney());
                break;
            case "Strategy 2":
                for(StoryCard storyCard : game.getDeckOfStoryCards()){
                    if(storyCard instanceof SlayTheDragon){ //to preserve deck card ratios
                        game.removeFromStoryDeck(storyCard);
                        break;
                    }
                }
                game.addToStoryDeck(new SlayTheDragon());
                break;
        }
        int numberOfPlayersResult = getNumberOfPlayers();
        if(numberOfPlayersResult == 0){
            okAlert("Error starting game, not enough players!", "Error.");
            exit(0);
        }
        setPlayerNames(numberOfPlayersResult);
        NUM_PLAYERS = numberOfPlayersResult;
        currentBehaviour = Behaviour.DEFAULT;
        currentTurnPlayer = game.getPlayers().get(0);
        setActivePlayer(game.getPlayers().get(0));
        playerStatsVbox.setSpacing(5);
        playerStatsVbox.setAlignment(Pos.TOP_RIGHT);
        cardsHbox.setAlignment(Pos.BASELINE_CENTER);
        game.dealCards(deckOfAdventureCards);
        game.addChangeListener(this);
        update();

    }


    private void setPlayerNames(int numberOfPlayers){
        //cardsHbox.prefWidthProperty().bind(Stage.widthProperty().multiply(0.80));
        for(int i =0; i < numberOfPlayers; i++){
            List<String> choices = new ArrayList<>();
            choices.add("Human");
            choices.add("CPU");
            ChoiceDialog<String> playerTypeDialog = new ChoiceDialog<>("Human", choices);
            playerTypeDialog.setTitle("Human or CPU?");
            playerTypeDialog.setHeaderText("Player/AI");
            playerTypeDialog.setContentText("Please select if you would like Player " + (i+1) + " to be a human or CPU.");
            Optional<String> playerTypeResult = playerTypeDialog.showAndWait();
            // The Java 8 way to get the response value (with lambda expression).
            String playerType = "Human";
            if (playerTypeResult.isPresent()) {
                playerType = playerTypeResult.get();
            }
            final String playerTypeFinal = playerType;
            String playerString;
            if(playerType.equals("Human")){
                playerString = "Player";
            } else {
                playerString = playerType;
            }
            TextInputDialog dialog = new TextInputDialog(playerString + (i+1));
            if(playerType.equals("CPU")){
                dialog.setTitle("Set CPU name");
                dialog.setHeaderText("Please enter the name for CPU" + (i+1) + ".");
            } else {
                dialog.setTitle("Set Player name");
                dialog.setHeaderText("Player " + (i + 1) + ", please enter your name.");
            }
//            DialogPane dialogPane = dialog.getDialogPane();
//            dialogPane.getStylesheets().add(getClass().getResource("../CSS/Alerts.css").toExternalForm());
//            dialogPane.getStyleClass().add("alertDialogs");
            // dialog.setContentText("Please enter your name:");

            Optional<String> result = dialog.showAndWait();

            // The Java 8 way to get the response value (with lambda expression).
            result.ifPresent(name -> game.addPlayer(playerTypeFinal, name));
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

    @Override
    public void propertyChange(PropertyChangeEvent change) {

        if (change.getPropertyName().equals("handFull")){
            if ((boolean) change.getNewValue() ){
                handFull((Player)change.getSource());
            }
        }
        else if(change.getPropertyName().equals("callToArms")){
            Player drawPlayer = (Player)change.getSource();
            callToArms(drawPlayer);
        }
        else if(change.getPropertyName().equals("test")){
            if((Boolean)change.getOldValue() == false && (Boolean)change.getNewValue()==true){
                performTest();
            }
        }


    }

}