package quest.client;

import com.fasterxml.jackson.core.type.TypeReference;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.fxml.FXML;
import javafx.scene.text.TextAlignment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import quest.server.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.TimeUnit;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import static java.lang.System.exit;
import static java.util.Arrays.asList;

enum Behaviour {SPONSOR, QUEST_MEMBER, BID, DISCARD, CALL_TO_ARMS, TOURNAMENT, DEFAULT, DISABLED}

public class Controller implements PropertyChangeListener {

    private static final Logger logger = LogManager.getLogger(App.class);
    private Player activePlayer;
    private Player currentTurnPlayer;
    private Player thisPlayer;
    private int NUM_PLAYERS = 0;
    private int currentPlayerIndex = 0;
    private AdventureCard selectedAdventureCard;
    private  Behaviour previousBehaviour;
    private Behaviour currentBehaviour;
    private int callToArmsFoes = 0;
    private int bidsToDo =0;

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
    @FXML
    private Pane disabledPane;
    @FXML
    private Label disabledLabel;
    private static Thread thread;
    private ArrayList<FlowPane> flowPaneArray = new ArrayList<>();
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private Socket backgroundWorkerSocket;
    private DataOutputStream pdos;
    private DataInputStream pdis;



    public void setCurrentTurnPlayer(Player currentTurnPlayer) {
        this.currentTurnPlayer = currentTurnPlayer;
    }

    public Controller(){
        currentBehaviour = Behaviour.DISABLED;
        final BackgroundWorker backgroundWorker = new BackgroundWorker();
        try {
            socket = new Socket(PlayerData.ipAddress, PlayerData.port);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            backgroundWorkerSocket = new Socket(PlayerData.ipAddress, PlayerData.port+1);
            pdos = new DataOutputStream(backgroundWorkerSocket.getOutputStream());
            pdis = new DataInputStream(backgroundWorkerSocket.getInputStream());
            dos.writeUTF(PlayerData.name);
            dos.flush();

            backgroundWorker.updateState.addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    Platform.runLater(this::update);
                }
            });
            backgroundWorker.start();
        } catch(IOException E) {
            E.printStackTrace();
        }
    }

    private void print(String stringToPrint){
        System.out.println(stringToPrint);
    }

    private ImageView createAdventureCardImageView(AdventureCard card){
        ImageView imgView = new ImageView();
        imgView.setPreserveRatio(true);
        imgView.setFitHeight(75);
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

        imgView.setOnMouseClicked((MouseEvent event) -> {
            if(currentBehaviour==Behaviour.QUEST_MEMBER) {
                if (card.getName().equals("Merlin")) {
                    if (!(serverGetMerlinIsUsed(card))){
                        boolean useMerlin = yesNoAlert("Use Merlin effect to see the next stage?", "Merlin");
                        if(useMerlin){
                            flowPaneArray.get(serverGetCurrentQuest().getCurrentStageIndex()+1).getChildren().clear();
                            for (AdventureCard adCard : serverGetPreQuestStageSetup().get(serverGetCurrentQuest().getCurrentStageIndex()+1)) {
                                ImageView imgView2 = createAdventureCardImageView(adCard);
                                imgView2.setImage(getCardImage(adCard.getImageFilename()));
                                imgView2.toFront();
                                flowPaneArray.get(serverGetCurrentQuest().getCurrentStageIndex()+1).getChildren().add(imgView2);
                            }
                            serverSetMerlinIsUsed(card, true);
                        }
                    }
                }
            }
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
        StoryCard currentStory = serverGetCurrentStory();
        if(currentStory !=null) {
            currentCardImage.setImage(getCardImage(currentStory.getImageFilename()));
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
                else if(currentBehaviour == Behaviour.BID){
                    activePlayer.removeCardFromHand(selectedAdventureCard);
                    bidsToDo--;
                    if(bidsToDo==0){
                        currentBehaviour = Behaviour.QUEST_MEMBER;
                        continueButton.setDisable(false);
                        discardPane.setVisible(false);
                        serverSetInTestCurrentQuest(false);
                    }
                    update();
                    success = true;
                }
                else if(currentBehaviour == Behaviour.CALL_TO_ARMS){
                    if (selectedAdventureCard instanceof Weapon){
                        activePlayer.removeCardFromHand((selectedAdventureCard));
                        currentBehaviour = previousBehaviour;
                        previousBehaviour = null;
                        discardPane.setVisible(false);
                        nextTurnButton.setDisable(false);
                        setActivePlayer(serverGetPlayers().get(serverGetCurrentTurnIndex()));
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
                                setActivePlayer(serverGetPlayers().get(serverGetCurrentTurnIndex()));
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
            if(currentBehaviour==Behaviour.SPONSOR){
                if (db.hasString()) {
                    if (db.getString().equals(cardsHbox.getId())) {
                        if (serverIsValidDrop(selectedAdventureCard, stageIndex)) {
                            serverAddToPotentialStage(selectedAdventureCard, stageIndex);
                            serverGetSponsor().removeCardFromHand(selectedAdventureCard);
                            success = true;
                        }
                    } else {
                        for (int i = 0; i < serverGetCurrentQuest().getNumStage(); i++) {
                            if (db.getString().equals(Integer.toString(i))) {
                                if (serverIsValidDrop(selectedAdventureCard, stageIndex)) {
                                    serverAddToPotentialStage(selectedAdventureCard, stageIndex);
                                    serverRemoveFromPotentialStage(selectedAdventureCard, i);
                                    success = true;
                                }
                            }
                        }
                    }
                }
//                else{
//                   // serverRemoveFromPotentialStage(selectedAdventureCard,);
//                    serverAddToPotentialStage(selectedAdventureCard, stageIndex);
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

    public void setCurrentBehaviour(Behaviour behave){
        currentBehaviour = behave;
    }

    public void update() {
        //Vbox display player data
        activePlayer = serverGetActivePlayer();
        ArrayList<Player> currentPlayers = serverGetPlayers();
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
        currentTurnPlayer = currentPlayers.get(0);
        /////////
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
            Quest current = serverGetCurrentQuest();
            HashMap<Integer, ArrayList<AdventureCard>> pre = serverGetPreQuestStageSetup();
            for(int i =0; i < current.getNumStage(); i++){
                flowPaneArray.get(i).getChildren().clear();
                for (AdventureCard card : pre.get(i)) {
                    ImageView imgView = createAdventureCardImageView(card);
                    imgView.setImage(getCardImage(card.getImageFilename()));
                    imgView.toFront();
                    flowPaneArray.get(i).getChildren().add(imgView);
                }
            }
        }
        else if(currentBehaviour == Behaviour.QUEST_MEMBER||currentBehaviour == Behaviour.BID){
            Quest current = serverGetCurrentQuest();
            if(current!=null) {
                for (int i = 0; i < current.getNumStage(); i++) {
                    flowPaneArray.get(i).getChildren().clear();
                    if (current.getCurrentStage() == current.getStages().get(i)) {
                        HashMap<Integer, ArrayList<AdventureCard>> pre = serverGetPreQuestStageSetup();
                        for (AdventureCard card : pre.get(i)) {
                            ImageView imgView = createAdventureCardImageView(card);
                            imgView.setImage(getCardImage(card.getImageFilename()));
                            imgView.toFront();
                            flowPaneArray.get(i).getChildren().add(imgView);
                        }
                    } else {
                        boolean hasMerlin = false;
                        for (Card card : activePlayer.getCardsInHand()) {
                            if (card.getName().equals("Merlin")) hasMerlin = true;
                        }
////                    if(hasMerlin) {
////                        for (AdventureCard card :serverGetPreQuestStageSetup().get(i+1)) {
////                            ImageView imgView = createAdventureCardImageView(card);
////                            imgView.setImage(getCardImage(card.getImageFilename()));
////                            imgView.toFront();
////                            flowPaneArray.get(i+1).getChildren().add(imgView);
////                        }
////                    }
                    }
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
        dialog.getStylesheets().add(getClass().getResource("/CSS/Alerts.css").toExternalForm());
        dialog.getStyleClass().add("alertDialogs");
        questAlert.showAndWait();
        return (questAlert.getResult() == ButtonType.YES);
    }

    private void okAlert(String contentText, String headerText){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, contentText, ButtonType.OK);
        DialogPane dialog = alert.getDialogPane();
        alert.setHeaderText(headerText);
        dialog.getStylesheets().add(getClass().getResource("/CSS/Alerts.css").toExternalForm());
        dialog.getStyleClass().add("alertDialogs");
        alert.showAndWait();
    }


//    private ArrayList<Player> finalTournament(ArrayList<Player> tournamentParticipants) {
//        Tournament knightsOfTheRoundTableTournament = new Tournament("Knights of the Round Table Tournament", "", tournamentParticipants);
//        return knightsOfTheRoundTableTournament.getTournamentWinner();
//    }

    private void getWinningPlayers() {
        ArrayList<Player> knightsOfTheRoundTable;
        knightsOfTheRoundTable = new ArrayList<>();
        for (Player player : serverGetPlayers()) {
            if (player.stringifyRank().equals("Knight of the Round Table")) {
                knightsOfTheRoundTable.add(player);
            }
        }
        if (knightsOfTheRoundTable.isEmpty()){ }
        else if (knightsOfTheRoundTable.size() == 1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, knightsOfTheRoundTable.get(0).getPlayerName() + " won the the Game!");
            DialogPane dialog = alert.getDialogPane();
            dialog.getStylesheets().add(getClass().getResource("/CSS/Alerts.css").toExternalForm());
            dialog.getStyleClass().add("alertDialogs");
            alert.showAndWait();
            exit(0);
        }
        else {
            //fix this later
           // performTournament(knightsOfTheRoundTable,new TournamentFinal());
        }
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
            if (serverValidateQuestStages()) {

                for(int i = 0; i<serverGetCurrentQuest().getNumStage();i++){
                    serverAddStageToCurrentQuest(i);
                }
                setCurrentBehaviour(Behaviour.QUEST_MEMBER);
                update();
                serverGetCurrentQuest().startQuest();
                if(!serverGetCurrentQuest().isInTest()){
                    setCurrentBehaviour(Behaviour.QUEST_MEMBER);
                    setActivePlayer(serverGetCurrentQuest().getCurrentPlayer());
                    update();
                }
            } else {
                okAlert("Please set up a valid quest ","Error in quest stages.");
                for (int i = 0; i < serverGetCurrentQuest().getNumStage(); i++) {
                    for (AdventureCard card : serverGetPreQuestStageSetup().get(i)) {
                        //ADD METHOD ADD CARD TO SPONSOR HAND
                        serverAddCardToSponsorHand(card);
                    }
                }
                serverResetPotentialStages();
                update();
            }
        }
        else if(currentBehaviour == Behaviour.QUEST_MEMBER){
            serverGetCurrentQuest().nextTurn();
            if(serverGetCurrentQuest().isFinished()){
                getWinningPlayers();
                questOver();

            }
            else{
                if(serverGetCurrentQuest().isInTest()) {
                    setActivePlayer(serverGetCurrentQuest().getCurrentPlayer());
                }
                else{
                    activePlayer = serverGetCurrentQuest().getCurrentPlayer();
                    if(activePlayer instanceof AbstractAI){

                    }
                }
            }
            update();
        }
        else if(currentBehaviour == Behaviour.TOURNAMENT){
            serverGetCurrentTournament().nextTurn();
            if(serverGetCurrentTournament().isTournamentOver()){
                if(serverIsWinner()){
                    System.out.println("Game over," + serverGetWinningPlayers().get(0) + " wins");
                    System.exit(0);
                }
                else{
                    tournamentOver();
                }
            }
            else{
                setActivePlayer(serverGetCurrentTournament().getCurrentPlayer());
            }
            update();
        }
    }

    public void nextTurnAction(){

        currentTurnPlayer = serverGetPlayers().get(currentPlayerIndex);
        setActivePlayer(currentTurnPlayer);
        if(activePlayer instanceof AbstractAI){
            runAITurn();
        }
        else{
            storyDeckImg.setDisable(false);
            nextTurnButton.setDisable(true);
            update();
        }
    }

    //HAND AND DECK
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private void handFull(Player player){
        if(player == serverGetActivePlayer()) {
            if(!(player instanceof AbstractAI)){
                if(currentBehaviour!=Behaviour.DISCARD){
                    previousBehaviour = currentBehaviour;
                }
                setCurrentBehaviour(Behaviour.DISCARD);
                nextTurnButton.setDisable(true);
                okAlert(player.getPlayerName() + ", you must play or discard a card.", "Hand Full!");
                discardPane.setVisible(true);
            }
            else{
                ArrayList<AdventureCard> toRemove = new ArrayList<>();
                for(AdventureCard card: player.getCardsInHand()){
                    if(toRemove.size()<(player.getCardsInHand().size()-12)) {
                        toRemove.add(card);
                    }
                    else {
                        player.removeCardsAI(toRemove);
                        break;
                    }
                }
            }
        }
    }

    public void storyDeckDraw(){
        if(currentBehaviour!=Behaviour.DISABLED) {
            serverDrawStoryCard();
            StoryCard serverResponse = serverGetCurrentStory();
            //activeStoryImg = createStoryCardImageView();
            activeStoryImg.setImage(getCardImage(serverResponse.getImageFilename()));
            update();

            ArrayList<Player> serverPlayers = serverGetPlayers();

            ArrayList<Player> currentPlayerOrder = new ArrayList<>();
            int currentTurn = serverPlayers.indexOf(activePlayer);

            for (int i = 0; i < NUM_PLAYERS; i++) {
                currentPlayerOrder.add(serverPlayers.get(currentTurn));
                currentTurn = nextPlayerIndex(currentTurn);
            }
            if (serverResponse instanceof Quest) {
                serverSetCurrentQuest((Quest) serverResponse);
                questDraw(currentPlayerOrder);
            } else if (serverResponse instanceof Event) {
                nextTurnButton.setVisible(true);
                Event gameEvent = (Event) serverResponse;
                callEventEffect(gameEvent);
            } else if (serverResponse instanceof Tournament) {
                nextTurnButton.setVisible(true);
                serverSetCurrentTournament((Tournament) serverResponse);
                performTournament(currentPlayerOrder, serverGetCurrentTournament());
                nextTurnButton.setDisable(false);
            }
            currentPlayerIndex = nextPlayerIndex(currentPlayerIndex);
            //activePlayer = serverPlayers.get(currentPlayerIndex);
            storyDeckImg.setDisable(true);
            update();
        }
    }

    //QUESTS
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private void questDraw(ArrayList<Player> currentPlayerOrder) {
        StoryCard serverResponse = serverGetCurrentStory();
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
            if(validCardCount < serverGetCurrentQuest().getNumStage()){
                if(!(player instanceof AbstractAI)) {
                    okAlert(player.getPlayerName() + ", you cannot sponsor " + serverResponse.getName() + "!", "Sponsorship failed.");
                }
            } else {
                if (!(player instanceof AbstractAI)){
                    boolean alertResult = yesNoAlert(player.getPlayerName() + ", would you like to sponsor " + serverResponse.getName() + "?", "Sponsor " + serverResponse.getName() + "?");
                    if (alertResult) {
                        sponsor = activePlayer;
                        serverSetSponsor(sponsor);
                        performQuest(sponsor, (Quest) serverResponse);
                        nextTurnButton.setVisible(false);
                        continueButton.setVisible(true);
                        break;
                    }
                }
                else{
                    player.getCardsInHand();
                    ((AbstractAI) player).doISponsor(currentPlayerOrder,player.getCardsInHand(),(Quest) serverResponse);
                    boolean aiResult = ((AbstractAI) player).doISponsor(currentPlayerOrder,player.getCardsInHand(),(Quest)serverResponse);
                    if (aiResult) {
                        sponsor = activePlayer;
                        serverSetSponsor(sponsor);
                        performQuest(sponsor, (Quest) serverResponse);
                        nextTurnButton.setVisible(false);
                        continueButton.setVisible(true);
                        break;
                    }
                }
            }
        }
        if(serverGetSponsor() == null){
            setActivePlayer(currentTurnPlayer);
            nextTurnButton.setVisible(true);
            nextTurnButton.setDisable(false);
            continueButton.setVisible(false);
        }
    }

    private void questOver(){
        if(serverGetCurrentQuest().isWinner()) {
            for (Player player : serverGetCurrentQuest().getPlayerList()) {
                if(serverIsKingsRecognition()){
                    player.setShields(player.getShields() + 3);
                    serverSetKingsRecognition(false);
                }
                okAlert(player.getPlayerName() + " won the the quest, and received " + serverGetCurrentQuest().getShields() + " shields!", "Quest won!");
            }
        }
        else{
            okAlert("Quest has no winner.", "Quest failed!");
        }
        int sponsorCardsToDraw=serverGetCurrentQuest().getNumStage();
        for(int i =0; i<serverGetPreQuestStageSetup().size();i++){
            for(AdventureCard card: serverGetPreQuestStageSetup().get(i)){
                sponsorCardsToDraw++;
            }
        }
        for(int i=0;i<sponsorCardsToDraw;i++) {
            //DRAW ADVENTURE CARD METHOD WITH PLAYER PARAM
            serverDrawAdventureCard(serverGetSponsor());
        }
        stagesGridPane.getChildren().clear();
        flowPaneArray.clear();
        //NEEDS CLEAR METHOD
        serverClearPreQuestStageSetup();
        serverGetCurrentQuest().wipeWeapons();
        serverClearQuest();
        setActivePlayer(serverGetSponsor());
        serverSetSponsor(null);
        previousBehaviour = Behaviour.DEFAULT;
        if(!serverGetActivePlayer().handFull) {
            setCurrentBehaviour(Behaviour.DEFAULT);
            nextTurnButton.setVisible(true);
            nextTurnButton.setDisable(false);
        }
        continueButton.setVisible(false);
        update();
    }

    private void performQuest(Player sponsor, Quest quest) {
        quest.addChangeListener(this);
        serverSetSponsor(sponsor);
        quest.setSponsor(sponsor);
        setActivePlayer(sponsor);
        setCurrentBehaviour(Behaviour.SPONSOR);
        continueButton.setVisible(true);

        for(int i = 0;i<quest.getNumStage();i++){
            createStagePane(i);
        }
        addQuestPlayers(quest);
        setActivePlayer(sponsor);
        if(sponsor instanceof AbstractAI){
            serverSetPotentialStage(((AbstractAI) sponsor).sponsorQuestFirstStage(sponsor.getCardsInHand()),0);
            sponsor.removeCardsAI(((AbstractAI) sponsor).sponsorQuestFirstStage(sponsor.getCardsInHand()));
            for(int i=1; i<quest.getNumStage()-1;i++){
                serverSetPotentialStage(((AbstractAI) sponsor).sponsorQuestMidStage(sponsor.getCardsInHand()),i);
                sponsor.removeCardsAI(((AbstractAI) sponsor).sponsorQuestMidStage(sponsor.getCardsInHand()));
            }
            serverSetPotentialStage(((AbstractAI) sponsor).sponsorQuestLastStage(sponsor.getCardsInHand()),quest.getNumStage()-1);
            sponsor.removeCardsAI(((AbstractAI) sponsor).sponsorQuestLastStage(sponsor.getCardsInHand()));
            for(int i = 0; i<serverGetCurrentQuest().getNumStage();i++){
                serverAddStageToCurrentQuest(i);
            }
            setCurrentBehaviour(Behaviour.QUEST_MEMBER);
            serverGetCurrentQuest().startQuest();
            if(!serverGetCurrentQuest().isInTest()){
                setCurrentBehaviour(Behaviour.QUEST_MEMBER);
                setActivePlayer(serverGetCurrentQuest().getCurrentPlayer());
            }
            update();
        }
    }

    private void performTest(){

        ArrayList<Player> testPlayers = serverGetCurrentQuest().getCurrentStage().getParticipatingPlayers();
        ArrayList<Player> testPlayersToRemove = new ArrayList<>();

        int currentHighestBid = 0;
        int minBids = 2;
        if(serverGetCurrentStory().getName().equals("Search For The Questing Beast") && ((TestStage)serverGetCurrentQuest().getCurrentStage()).getSponsorTestCard().getName().equals("Test Of The Questing Beast")){
            minBids = 3;
        }
        int currentBid=minBids;
        int currentTestPlayerIndex=0;
        int currentNumInTest = testPlayers.size();
        Player currentTestPlayer;

        while(serverGetCurrentQuest().isInTest()) {
            if (currentTestPlayerIndex >= currentNumInTest) {
                currentTestPlayerIndex = 0;
                for (Player player : testPlayersToRemove) {
                    testPlayers.remove(player);
                }
                currentNumInTest = testPlayers.size();
                testPlayersToRemove.clear();
            }

            currentTestPlayer=testPlayers.get(currentTestPlayerIndex);
            activePlayer = currentTestPlayer;
            update();

            if(testPlayers.size()!=1 && (testPlayersToRemove.size()!=testPlayers.size()-1)) {
                if(!(currentTestPlayer instanceof AbstractAI)) {
                    List<String> choices = new ArrayList<>();
                    for (int i = currentBid + 1; i < activePlayer.getNumCardsInHand(); i++) {
                        choices.add(Integer.toString(i + 1));
                    }
                    choices.add("Drop Out");
                    ChoiceDialog<String> dialog = new ChoiceDialog<>(Integer.toString(currentBid + 1), choices);
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
                        logger.info("Player " + currentTestPlayer.getPlayerName() + "left the quest");
                    } else {
                        currentBid = Integer.parseInt(cardsToBid);
                    }
                    if (currentBid > currentHighestBid) {
                        currentHighestBid = currentBid;
                    }
                    currentTestPlayerIndex++;
                }
                else{
                    currentBid = ((AbstractAI) currentTestPlayer).nextBid(currentTestPlayer.getCardsInHand());
                    if (currentBid > currentHighestBid) {
                        currentHighestBid = currentBid;
                    }
                    else{
                        testPlayersToRemove.add(currentTestPlayer);
                    }
                    currentTestPlayerIndex++;
                }
            }
            else{
                for (Player player : testPlayersToRemove) {
                    testPlayers.remove(player);
                }
                testPlayersToRemove.clear();
                testPlayers.get(0).setCurrentBid(currentHighestBid);
                serverGetCurrentQuest().setPlayerList(testPlayers);
                continueButton.setDisable(true);
                if(!(testPlayers.get(0) instanceof AbstractAI)) {
                    okAlert(testPlayers.get(0).getPlayerName() + " won the test, discard your bids", "Test Over");
                    setCurrentBehaviour(Behaviour.BID);
                    activePlayer=testPlayers.get(0);
                    bidsToDo = currentHighestBid - (testPlayers.get(0).getBidDiscount(serverGetCurrentQuest()));
                    discardPane.setVisible(true);
                    discardPane.setDisable(false);
                }else{
                    ((AbstractAI) currentTestPlayer).discardAfterWinningTest(currentTestPlayer.getCardsInHand());
                    currentBehaviour = Behaviour.QUEST_MEMBER;
                    continueButton.setDisable(false);
                    discardPane.setVisible(false);
                    serverGetCurrentQuest().setInTest(false);
                    serverGetCurrentQuest().nextTurn();
                    if(serverGetCurrentQuest().isFinished()){
                        if(serverIsWinner()){
                            System.out.println("Game over," + serverGetWinningPlayers().get(0) + " wins");
                            System.exit(0);
                        }
                        else{
                            questOver();
                        }
                    }
                    else{
                        if(serverGetCurrentQuest().isInTest()) {
                            setActivePlayer(serverGetCurrentQuest().getCurrentPlayer());
                        }
                        else{
                            activePlayer = serverGetCurrentQuest().getCurrentPlayer();

                        }
                    }
                }

                logger.info("Current player with highest bid" + testPlayers.get(0) +" for this testStage." );
                update();
                break;
            }
        }

    }

    private void addQuestPlayers(Quest currentQuest){
        ArrayList<Player> playersInQuest = new ArrayList<>();
        ArrayList<Player> serverPlayers = serverGetPlayers();
        for(int i = 0; i < NUM_PLAYERS; i++){
            if(serverPlayers.get(i) != serverGetSponsor()) {
                activePlayer = serverPlayers.get(i);
                update();
                if(activePlayer instanceof AbstractAI){
                    if(((AbstractAI)activePlayer).doIParticipateInQuest(activePlayer.getCardsInHand(),currentQuest.getNumStage())){
                        playersInQuest.add(activePlayer);
                    }
                } else {
                    if (yesNoAlert("Join " + serverGetCurrentQuest().getName() + " " + activePlayer.getPlayerName() + "?", "Join quest?")) {
                        playersInQuest.add(activePlayer);
                    }
                }
            }
        }
        if(playersInQuest.size() == 0){
            questOver();
        }
        else {
            currentQuest.setPlayerList(playersInQuest);
        }
    }

    //TOURNAMENT
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private void performTournament(ArrayList<Player> currentPlayerOrder, Tournament tournament) {
        setCurrentBehaviour(Behaviour.TOURNAMENT);
        addTournamentPlayers(tournament);
        if(!tournament.isTournamentOver()){
            tournament.setCurrentPlayer(tournament.getPlayerList().get(0));
            activePlayer =tournament.getCurrentPlayer();
            if(tournament.getPlayerList().size()==1){
                tournament.setTournamentOver(true);
                tournament.setWinners(tournament.getPlayerList());
                tournament.rewardWinner(tournament.getPlayerList().get(0));
                tournamentOver();
            }else {
                if(activePlayer instanceof AbstractAI){
                    AbstractAI ai = (AbstractAI)activePlayer;
                    ai.playCardsAI(ai.whatIPlay(ai.getCardsInHand(), serverGetPlayers(), serverGetCurrentTournament().getShields()));
                    serverGetCurrentTournament().nextTurn();
                    if(serverGetCurrentTournament().isTournamentOver()){
                        tournamentOver();
                    }
                    else{
                        setActivePlayer(serverGetCurrentTournament().getCurrentPlayer());
                    }
                }
                update();
                nextTurnButton.setVisible(false);
                continueButton.setVisible(true);
            }
        }

    }

    private void addTournamentPlayers(Tournament currentTournament){
        ArrayList<Player> tournamentPlayers = new ArrayList<>();
        ArrayList<Player> serverPlayers = serverGetPlayers();


        for(int i = 0; i < NUM_PLAYERS; i++){
            activePlayer = serverPlayers.get(i);
            update();
            if(!(activePlayer instanceof AbstractAI)) {
                if (yesNoAlert("Join " + serverGetCurrentStory().getName() + " " + activePlayer.getPlayerName() + "?", "Join Tournament?")) {
                    tournamentPlayers.add(serverPlayers.get(i));
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
        if(serverGetCurrentTournament().getName().equals("Tournament Final")){
            for (Player player : serverGetCurrentTournament().getWinners()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, player.getPlayerName() + " won the the Game!");
                DialogPane dialog = alert.getDialogPane();
                dialog.getStylesheets().add(getClass().getResource("/CSS/Alerts.css").toExternalForm());
                dialog.getStyleClass().add("alertDialogs");
                alert.showAndWait();
            }
            exit(0);
        }

        for (Player player : serverGetCurrentTournament().getWinners()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, player.getPlayerName() + " won the the Tournament, and received " + serverGetCurrentTournament().getShields() + " shields!", ButtonType.OK);
            DialogPane dialog = alert.getDialogPane();
            dialog.getStylesheets().add(getClass().getResource("/CSS/Alerts.css").toExternalForm());
            dialog.getStyleClass().add("alertDialogs");
            alert.showAndWait();
        }
        serverGetCurrentTournament().setTournamentOver(true);
        serverSetCurrentTournament(null);
        setCurrentBehaviour(Behaviour.DEFAULT);
        nextTurnButton.setVisible(true);
        nextTurnButton.setDisable(false);
        continueButton.setVisible(false);
        setActivePlayer(serverGetPlayers().get(serverGetCurrentTurnIndex()));
        getWinningPlayers();
        update();
        if(activePlayer instanceof AbstractAI){
            runAITurn();
        }
    }


    //EVENTS
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////

    private void callEventEffect(Event event){
        serverApplyEventEffect(event);
        nextTurnButton.setDisable(false);
        logger.info("Successfully called : Event constructor");
    }

    private void callToArms(Player player){
        previousBehaviour =currentBehaviour;
        setCurrentBehaviour(Behaviour.CALL_TO_ARMS);
        setActivePlayer(player);
        update();
        int foeCount =0;
        int weaponCount =0;
        for(AdventureCard card: player.getCardsInHand()){
            if(card instanceof Foe){ foeCount++;}
            if(card instanceof Weapon){ weaponCount++;}
        }
        if(!(player instanceof AbstractAI)) {
            okAlert(player.getPlayerName() + ", you must discard 1 Weapon. If you have no weapons, you must discard 2 Foes ", "Call to Arms");
        }
        if(foeCount==0 && weaponCount==0){
            if(!(player instanceof AbstractAI)) {
                okAlert("No weapons or foes to discard", "Notice:");
            }
            setCurrentBehaviour(previousBehaviour);
        }
        else if(player instanceof AbstractAI){
            if(weaponCount!=0){
                for(AdventureCard card: player.getCardsInHand()){
                    if(card instanceof Weapon){
                        player.removeCardFromHand(card);
                    }
                }
            }
            else{
                int foesRemoved=0;
                for(AdventureCard card: player.getCardsInHand()){
                    if(card instanceof Foe){
                        player.removeCardFromHand(card);
                        foesRemoved++;
                    }
                    if(foesRemoved==2){
                        break;
                    }
                }
            }
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
    private void runAITurn(){
        StoryCard currentStory = serverGetCurrentStory();
        serverDrawStoryCard();
        ArrayList<Player> serverplayers = serverGetPlayers();
        System.out.println("storyDeckDraw(): " + currentStory.getName());
        //activeStoryImg = createStoryCardImageView();
        activeStoryImg.setImage(getCardImage(currentStory.getImageFilename()));
        update();

        ArrayList<Player> currentPlayerOrder = new ArrayList<>();
        int currentTurn = serverplayers.indexOf(activePlayer);
        for(int i = 0; i < NUM_PLAYERS; i++){
            currentPlayerOrder.add(serverplayers.get(currentTurn));
            currentTurn = nextPlayerIndex(currentTurn);
        }
//        ArrayList<Player> noAIPlayers = new ArrayList<>();
//        for(Player player : serverGetPlayers()){
//            if(!(player instanceof AbstractAI)){
//                noAIPlayers.add(player);
//            }
//        }
        if (currentStory instanceof Quest) {
            serverSetCurrentQuest((Quest) currentStory);
            questDraw(currentPlayerOrder);
        } else if (currentStory instanceof Event) {
            nextTurnButton.setVisible(true);
            Event gameEvent = (Event) currentStory;
            callEventEffect(gameEvent);
        } else if (currentStory instanceof Tournament) {
            nextTurnButton.setVisible(true);
            serverSetCurrentTournament((Tournament) currentStory);
            performTournament(currentPlayerOrder, serverGetCurrentTournament());
            nextTurnButton.setDisable(false);
        }
        currentPlayerIndex = nextPlayerIndex(currentPlayerIndex);
        storyDeckImg.setDisable(true);
        update();
    }


    private void setActivePlayer(Player player){
        activePlayer = player;
        serverSetActivePlayer(player);
        if(player.isHandFull()){
            handFull(player);
        }
    }


    private javafx.scene.image.Image getCardImage(String cardFileName){
        javafx.scene.image.Image img;
        String resourceFolderPath = "/Cards/";
        try {
            img = new Image(getClass().getResourceAsStream(resourceFolderPath + cardFileName));//can be url
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return img;
    }

    @Override
    public void propertyChange(PropertyChangeEvent change) {
        switch (change.getPropertyName()) {
//            case "handFull":
//                if ((Boolean) change.getNewValue()) {
//                    handFull((Player) change.getSource());
//                }
//                break;
            case "callToArms":
                Player drawPlayer = (Player) change.getSource();
                callToArms(drawPlayer);
                break;
            case "test":
                if (!((Boolean) change.getOldValue()) && (Boolean) change.getNewValue()) {
                    performTest();
                }
                break;
        }
    }
    ///////////////////////////////////////////////////////////////////////////
    //Server Actions
    ///////////////////////////////////////////////////////////////////////////
    private ArrayList<Object> listArguments(Object ...args){
        return new ArrayList<>(asList(args));
    }
    private static <T> T getServerObject(final String jsonFromServer, TypeReference<T> objectClass){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(jsonFromServer, objectClass);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    ///////////////////////////////////////////////////////////////////////////
    //Getters
    ///////////////////////////////////////////////////////////////////////////
    @SuppressWarnings("unchecked")
    private String genericGet(String methodName){
        String serverJSON = "";
        JSONObject json = new JSONObject();
        json.put("type", "get");
        json.put("methodName", methodName);
        try {
            dos.writeUTF(json.toJSONString());
            dos.flush();
        } catch (IOException E){
            E.printStackTrace();
        }
        for(int i = 0; i<10; i++){
            try {
                if(dis.available() == 0) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        serverJSON = dis.readUTF();
                        System.out.println("Server responded with: " + serverJSON);
                        break;
                    } catch (IOException E) {
                        E.printStackTrace();
                        System.out.println("Server failed to respond.");
                        return null;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return serverJSON;
    }
    @SuppressWarnings("unchecked")
    private String genericGetWithParams(String methodName, ArrayList<Class<?>> argumentTypes, ArrayList<Object> arguments){
        String serverJSON = "";
        JSONObject json = new JSONObject();
        json.put("type", "getWithParams");
        json.put("methodName", methodName);
        json.put("argumentTypes", argumentTypes);
        json.put("arguments", arguments);
        try {
            dos.writeUTF(json.toJSONString());
            dos.flush();
        } catch (IOException E){
            E.printStackTrace();
        }
        for(int i = 0; i<10; i++){
            try {
                if(dis.available() == 0) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        serverJSON = dis.readUTF();
                        System.out.println("Server responded with: " + serverJSON);
                        break;
                    } catch (IOException E) {
                        E.printStackTrace();
                        System.out.println("Server failed to respond.");
                        return null;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return serverJSON;
    }
    ///////////////////////////////////////////////////////////////////////////
    private StoryCard serverGetCurrentStory() {
        return getServerObject(
                genericGet("getCurrentStory"), new TypeReference<StoryCard>(){});
    }
    private ArrayList<Player> serverGetPlayers(){
        return getServerObject(
                genericGet("getPlayers"), new TypeReference<ArrayList<Player>>(){});
    }
    private Quest serverGetCurrentQuest(){
        return getServerObject(
                genericGet("getCurrentQuest"), new TypeReference<Quest>(){});
    }
    private Tournament serverGetCurrentTournament(){
        return getServerObject(
                genericGet("getCurrentTournament"), new TypeReference<Tournament>(){});
    }
    private Integer serverGetCurrentTurnIndex(){
        return getServerObject(
                genericGet("getCurrentTurnIndex"), new TypeReference<Integer>(){});
    }
    private Player serverGetSponsor(){
        return getServerObject(
                genericGet("getSponsor"), new TypeReference<Player>(){});
    }
    private Player serverGetActivePlayer(){
        return getServerObject(
                genericGet("getActivePlayer"), new TypeReference<Player>(){});
    }
    private HashMap<Integer,ArrayList<AdventureCard>> serverGetPreQuestStageSetup(){
        return getServerObject(
                genericGet("getPreQuestStageSetup"), new TypeReference<HashMap<Integer,ArrayList<AdventureCard>>>(){});
    }
    private ArrayList<Player> serverGetWinningPlayers(){
        return getServerObject(
                genericGet("getWinningPlayers"), new TypeReference<ArrayList<Player>>(){});
    }
    private Boolean serverValidateQuestStages(){
        return getServerObject(
                genericGet("validateQuestStages"), new TypeReference<Boolean>(){});
    }
    private Boolean serverIsWinner(){
        return getServerObject(
                genericGet("isWinner"), new TypeReference<Boolean>(){});
    }
    private Boolean serverIsKingsRecognition(){
        return getServerObject(
                genericGet("isKingsRecognition()"), new TypeReference<Boolean>(){});
    }
    @SuppressWarnings("unchecked")
    private Boolean serverIsValidDrop(AdventureCard card, Integer stageNum) {
        return getServerObject(
                genericGetWithParams("isValidDrop",
                        new ArrayList<Class<?>>(){{
                            add(card.getClass());
                            add(stageNum.getClass());
                }},
                        new ArrayList<Object>(){{
                            add(card);
                            add(stageNum);
                }})
                ,new TypeReference<Boolean>(){});
    }
    private Boolean serverGetMerlinIsUsed(AdventureCard card){
        return getServerObject(
                genericGetWithParams("getMerlinIsUsed",
                new ArrayList<Class<?>>(){
                    {
                        add(card.getClass());
                    }},
                new ArrayList<Object>(){{
                        add(card);
                    }}),
                new TypeReference<Boolean>(){});
    }
    ///////////////////////////////////////////////////////////////////////////
    //Setters
    ///////////////////////////////////////////////////////////////////////////
    @SuppressWarnings("unchecked")
    private void genericSet(String methodName, Object... args){
        JSONObject json = new JSONObject();
        json.put("type", "set");
        json.put("methodName", methodName);
        json.put("argumentTypes", new ArrayList<Class<?>>(){{for(Object arg : Arrays.asList(args))add(arg.getClass());}});
        json.put("arguments", new ArrayList<Object>(){{this.addAll(Arrays.asList(args));}});
        try {
            dos.writeUTF(json.toJSONString());
            dos.flush();
        } catch (IOException E) {
            E.printStackTrace();
        }
    }
    ///////////////////////////////////////////////////////////////////////////
    @SuppressWarnings("unchecked")
    private void serverDrawStoryCard() {
        genericSet("drawStoryCard");
    }
    @SuppressWarnings("unchecked")
    private void serverSetActivePlayer(Player player) {
        genericSet("setActivePlayer", player);
    }
    @SuppressWarnings("unchecked")
    private void serverSetCurrentQuest(Quest quest) {
        genericSet("setCurrentQuest", quest);
    }
    @SuppressWarnings("unchecked")
    private void serverSetSponsor(Player player) {
        genericSet("setSponsor", player);
    }
    @SuppressWarnings("unchecked")
    private void serverSetInTestCurrentQuest(Boolean value) {
        genericSet("setInTestCurrentQuest", value);
    }
    @SuppressWarnings("unchecked")
    private void serverClearQuest() {
        genericSet("clearQuest");
    }
    @SuppressWarnings("unchecked")
    private void serverDrawAdventureCard(Player sponsor) {
        genericSet("drawAdventureCard", sponsor);
    }
    @SuppressWarnings("unchecked")
    private void serverSetKingsRecognition(Boolean value) {
        genericSet("setKingsRecognition", value);
    }
    @SuppressWarnings("unchecked")
    private void serverSetMerlinIsUsed(AdventureCard card, Boolean value) {
        genericSet("setMerlinIsUsed", card, value);
    }
    @SuppressWarnings("unchecked")
    private void serverResetPotentialStages(){
        genericSet("resetPotentialStages");
    }

    @SuppressWarnings("unchecked")
    private void serverAddToPotentialStage(AdventureCard card, Integer stageNum) {
        genericSet("addToPotentialStage", card, stageNum);
    }
    @SuppressWarnings("unchecked")
    private void serverSetPotentialStage(ArrayList<AdventureCard> stage, Integer stageNum) {
        genericSet("setPotentialStage", stage, stageNum);
    }
    @SuppressWarnings("unchecked")
    private void serverAddCardToSponsorHand(AdventureCard card) {
        genericSet("addCardToSponsorHand", card);
    }
    @SuppressWarnings("unchecked")
    private void serverRemoveFromPotentialStage(AdventureCard card, Integer stageNum) {
        genericSet("removeFromPotentialStage", card, stageNum);
    }
    @SuppressWarnings("unchecked")
    private void serverSetCurrentTournament(Tournament tournament) {
        genericSet("setCurrentTournament", tournament);
    }
    @SuppressWarnings("unchecked")
    private void serverClearPreQuestStageSetup() {
        genericSet("clearPreQuestStageSetup");
    }
    @SuppressWarnings("unchecked")
    private void serverApplyEventEffect(Event event) {
        genericSet("applyEventEffect", event);
    }
    private void serverAddStageToCurrentQuest(Integer stageNum) {
        genericSet("addStageToCurrentQuest", stageNum);
    }
    ///////////////////////////////////////////////////////////////////////////
    //Daemon
    ///////////////////////////////////////////////////////////////////////////
    class BackgroundWorker extends Thread {
        private BooleanProperty updateState;
        BackgroundWorker() {
            updateState = new SimpleBooleanProperty(this, "bool", false);
            setDaemon(true);
        }

        public boolean getUpdateState() {
            return updateState.get();
        }

        public BooleanProperty updateState() {
            return updateState;
        }

        @Override
        @SuppressWarnings("InfiniteLoopStatement")
        public void run() {
            while (true) {
                try {
                    String inputStreamContents = pdis.readUTF();
                    System.out.println("Background worker received command: " + inputStreamContents);
                    org.json.JSONObject serverCommand  = new org.json.JSONObject(inputStreamContents);
                    if(serverCommand.has("behaviour")) {
                        disabledPane.setVisible(false);
                        switch (serverCommand.getString("behaviour")) {
                            case "DEFAULT":
                                setCurrentBehaviour(Behaviour.DEFAULT);
                                break;
                            case "DISABLED":
                                setCurrentBehaviour(Behaviour.DISABLED);
                                disabledPane.setVisible(true);
                                break;
                            case "TOURNAMENT":
                                setCurrentBehaviour(Behaviour.TOURNAMENT);
                                break;
                            case "CALL_TO_ARMS":
                                setCurrentBehaviour(Behaviour.CALL_TO_ARMS);
                                break;
                            case "DISCARD":
                                setCurrentBehaviour(Behaviour.DISCARD);
                                break;
                            case "BID":
                                setCurrentBehaviour(Behaviour.BID);
                                break;
                            case "QUEST_MEMBER":
                                setCurrentBehaviour(Behaviour.QUEST_MEMBER);
                                break;
                            case "SPONSOR":
                                setCurrentBehaviour(Behaviour.SPONSOR);
                                break;
                        }
                    }
                    if(serverCommand.has("update")) {
                        updateState.setValue(serverCommand.getString("update").equals("true"));
                        System.out.println("Update called? " + getUpdateState());
                    }
                    if(serverCommand.has("player")) {
                        thisPlayer = getServerObject(serverCommand.getString("player"), new TypeReference<Player>() {
                        });
                    }
                } catch(Exception ignored) {
                }

            }
        }
    }

}


