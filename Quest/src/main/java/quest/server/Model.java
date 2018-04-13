package quest.server;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import quest.client.App;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

import static java.lang.System.exit;
import static quest.server.Rank.KNIGHT_OF_THE_ROUND_TABLE;

public class Model implements PropertyChangeListener
{
    private static final Logger logger = LogManager.getLogger(App.class);

    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Player> currentPlayerOrder = new ArrayList<>();
    private int currentPlayerOrderIndex;
    private Stack<AdventureCard> deckOfAdventureCards = new Stack<>();
    private Stack<StoryCard> deckOfStoryCards = new Stack<>();
    private Stack<AdventureCard> discardOfAdventureCards = new Stack<>();
    private Stack<StoryCard> discardOfStoryCards = new Stack<>();
    private HashMap<Integer,ArrayList<AdventureCard>> preQuestStageSetup = new HashMap<>();
    private StoryCard currentStory;
    private Player activePlayer;
    private Quest currentQuest;
    private Tournament currentTournament;
    private Player sponsor;
    private int currentTurnIndex = 0;
    private List<PropertyChangeListener> listener = new ArrayList<>();
    private ArrayList<Player> winningPlayers = new ArrayList<>();
    private boolean kingsRecognition = false;
    private boolean change = false;

    /////////////////////////////////////////////////
    public boolean getMerlinIsUsed(AdventureCard card){
        Merlin merlin = (Merlin) card;
        return merlin.isUsed();
    }
    public void setMerlinIsUsed(AdventureCard card, boolean value){
        Merlin merlin = (Merlin) card;
        merlin.setIsUsed(value);
    }
    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Player p) {
        this.activePlayer = p;
    }

    public Tournament getCurrentTournament() {
        return currentTournament;
    }

    public Player getSpecificPlayer(Player p){
        for(Player player : players){
            if(player.getPlayerName().equals(p.getPlayerName())){
                return player;
            }
        }
        return null;
    }


    public void setCurrentTournament(Tournament currentTournament) {
        this.currentTournament = currentTournament;
    }

    public void setInTestCurrentQuest(Boolean value){
        currentQuest.setInTest(value);
    }

    public void changed(){
        this.change = !this.change;
        notifyListeners("changed", Boolean.TRUE,!this.change,this.change);
    }


    /////////////////////////////////////////////////

    public void applyEventEffect(Event event){
        switch (event.getName()) {
            case "Chivalrous Deed":
                event.applyEvent(players, null);
                break;
            case "Court Called To Camelot":
                event.applyEvent(players, null);
                break;
            case "King's Call To Arms":
                event.applyEvent(players, activePlayer);
                break;
            case "King's Recognition":
                event.applyEvent(null, null);
                break;
            case "Plague":
                event.applyEvent(null, activePlayer);
                break;
            case "Pox":
                event.applyEvent(players, activePlayer);
                break;
            case "Prosperity Throughout The Realm":
                event.applyEvent(players, null);
                break;
            case "Queen's Favor": {
                event.applyEvent(players, null);
                break;
            }
        }
    }

    public StoryCard getCurrentStory() {
        logger.info("Returning current story.");
        return currentStory;
    }

    public HashMap<Integer, ArrayList<AdventureCard>> getPreQuestStageSetup() {
        return preQuestStageSetup;
    }

    public void setCurrentQuest(Quest currentQuest) {
        this.currentQuest = currentQuest;
        currentQuest.addChangeListener(this);
        preQuestStageSetup.clear();
        for(int i = 0; i<currentQuest.getNumStage();i++){
            preQuestStageSetup.put(i,new ArrayList<>());
        }
    }

    public void clearQuest(){
        for(Player player: players){
            ArrayList<AdventureCard> found = new ArrayList<>();
            for(AdventureCard card:player.getCardsOnTable()){
                if(card instanceof Amour){
                    found.add(card);
                }
            }
            player.getCardsOnTable().removeAll(found);
        }
        this.discardOfStoryCards.add(currentQuest);
        this.currentQuest =null;
    }

    public Quest getCurrentQuest() {
        return currentQuest;
    }

    public boolean isKingsRecognition() {
        return kingsRecognition;
    }

    public void setKingsRecognition(boolean kingsRecognition) {
        this.kingsRecognition = kingsRecognition;
    }

    public Player getSponsor() {
        logger.info("Returning the sponsor.");
        return sponsor;
    }

    public void setSponsor(Player sponsor) {
        logger.info("Set the sponsor.");
        this.sponsor = sponsor;
    }
    public void clearPreQuestStageSetup(){
        preQuestStageSetup.clear();
    }
    public void addStageToCurrentQuest(int stageNum){
        currentQuest.addStage(createStage(preQuestStageSetup.get(stageNum)));
    }

    public QuestStage createStage(ArrayList<AdventureCard> cardsForStage){
        for(AdventureCard adventureCard : cardsForStage) {
            if (adventureCard instanceof Foe) {
                if(currentQuest.getQuestFoes().contains(adventureCard)){
                    adventureCard.setBattlePoints(adventureCard.getBattlePoints() + adventureCard.getBonusBattlePoints());
                }
                return new FoeStage(cardsForStage, new ArrayList<>());
            } else if (adventureCard instanceof Test) {
                return new TestStage(adventureCard, new ArrayList<>());
            }
        }
        return null;
    }

    public boolean validateQuestStages() {
        int lastStageBattlePoints = 0;
        int testCount =0;
        for (int i = 0; i < getCurrentQuest().getNumStage(); i++) {
            int currentStageBattlePoints = 0;
            int foeCount = 0;
            if(getPreQuestStageSetup().get(i) == null){
                return false;
            }
            for (AdventureCard adventureCard :  getPreQuestStageSetup().get(i)) {
                int adventureCardBattlePoints = adventureCard.getBattlePoints();
                if (adventureCard instanceof Test) {
                    if(getPreQuestStageSetup().get(i).size() > 1) {
                        return false;
                    }
                    testCount++;
                    break;
                } else if ((adventureCard instanceof Ally)) {
                    return false;
                } else if (adventureCard instanceof Foe) {
                    for(AdventureCard foe : currentQuest.getQuestFoes()){
                        if(adventureCard.getName().toLowerCase().equals(foe.getName().toLowerCase())){
                            adventureCardBattlePoints += adventureCard.getBonusBattlePoints();
                            break;
                        }
                    }
                    foeCount++;
                }
                currentStageBattlePoints += adventureCardBattlePoints;
            }
            if ((getPreQuestStageSetup().get(i).get(0) instanceof Test) || (currentStageBattlePoints > lastStageBattlePoints)) {
                lastStageBattlePoints = currentStageBattlePoints;
            } else {
                return false;
            }
            if(foeCount == 0 && testCount == 0 || foeCount > 1){
                return false;
            }
        }
        return testCount <= 1;
    }

    public Model() {



        //Trim white border from cards and convert to .pngs to avoid white bounding box

        //Initializing all cards

        //Allies

        ArrayList<Foe> allFoes = new ArrayList<>();
        allFoes.add(new BlackKnight());
        allFoes.add(new Boar());
        allFoes.add(new Dragon());
        allFoes.add(new EvilKnight());
        allFoes.add(new Giant());
        allFoes.add(new GreenKnight());
        allFoes.add(new Mordred());
        allFoes.add(new RobberKnight());
        allFoes.add(new SaxonKnight());
        allFoes.add(new Saxons());
        allFoes.add(new Thieves());
        logger.info("storing all foes in allFoess array");

        ArrayList<Weapon> allWeapons = new ArrayList<>();
        allWeapons.add(new BattleAx());
        allWeapons.add(new Dagger());
        allWeapons.add(new Excalibur());
        allWeapons.add(new Horse());
        allWeapons.add(new Lance());
        allWeapons.add(new Sword());
        logger.info("storing all weapons in allWeapons array");

        //Quest

        ArrayList<Quest> allQuests = new ArrayList<>();

        allQuests.add(new BoarHunt());
        allQuests.add(new DefendTheQueensHonor());
        allQuests.add(new JourneyThroughTheEnchantedForest());
        allQuests.add(new RepelTheSaxonRaiders());
        allQuests.add(new RescueTheFairMaiden());
        allQuests.add(new SearchForTheHolyGrail());
        allQuests.add(new SearchForTheQuestingBeast());
        allQuests.add(new SlayTheDragon());
        allQuests.add(new TestOfTheGreenKnight());
        allQuests.add(new VanquishKingArthursEnemies());
        logger.info("storing all quest in allQuests array");


        //Events
        ArrayList<Event> allEvents = new ArrayList<>();
        allEvents.add(new ChivalrousDeed());
        allEvents.add(new CourtCalledToCamelot());
        allEvents.add(new KingsCallToArms());
        allEvents.add(new KingsRecognition());
        allEvents.add(new Plague());
        allEvents.add(new Pox());
        allEvents.add(new ProsperityThroughoutTheRealm());
        allEvents.add(new QueensFavor());
        logger.info("storing all events in allEvents array");

        //Allies
        ArrayList<Ally> allAllies = new ArrayList<>();
        allAllies.add(new KingArthur());
        allAllies.add(new KingPellinore());
        allAllies.add(new Merlin());
        allAllies.add(new QueenGuinevere());
        allAllies.add(new QueenIseult());
        allAllies.add(new SirGalahad());
        allAllies.add(new SirGawain());
        allAllies.add(new SirLancelot());
        allAllies.add(new SirPercival());
        allAllies.add(new SirTristan());
        allAllies.add(new Amour("Amour", "Amour.jpg"));
        logger.info("storing all allies in allAllies array");


        //Create HashMap to store number of occurrences of each AdventureCard

        HashMap<AdventureCard, Integer> numberOfEachAdventureCard = new HashMap<>();

        //Foes
        numberOfEachAdventureCard.put(new BlackKnight(), 3);
        numberOfEachAdventureCard.put(new Boar(), 4);
        numberOfEachAdventureCard.put(new Dragon(), 1);
        numberOfEachAdventureCard.put(new EvilKnight(), 6);
        numberOfEachAdventureCard.put(new Giant(), 2);
        numberOfEachAdventureCard.put(new GreenKnight(), 2);
        numberOfEachAdventureCard.put(new Mordred(), 4);
        numberOfEachAdventureCard.put(new RobberKnight(), 7);
        numberOfEachAdventureCard.put(new SaxonKnight(), 8);
        numberOfEachAdventureCard.put(new Saxons(), 5);
        numberOfEachAdventureCard.put(new Thieves(), 8);
        logger.info("storing all foes and their instances in numberOfEachAdventureCard HashMap");

        //Weapons
        numberOfEachAdventureCard.put(new BattleAx(), 8);
        numberOfEachAdventureCard.put(new Dagger(), 6);
        numberOfEachAdventureCard.put(new Excalibur(), 2);
        numberOfEachAdventureCard.put(new Horse(), 11);
        numberOfEachAdventureCard.put(new Lance(), 6);
        numberOfEachAdventureCard.put(new Sword(), 16);
        logger.info("storing all weapons and their instances in numberOfEachAdventureCard HashMap");

        //Allies
        numberOfEachAdventureCard.put(new KingArthur(), 1);
        numberOfEachAdventureCard.put(new KingPellinore(), 1);
        numberOfEachAdventureCard.put(new Merlin(), 1);
        numberOfEachAdventureCard.put(new QueenIseult(), 1);
        numberOfEachAdventureCard.put(new SirGalahad(), 1);
        numberOfEachAdventureCard.put(new SirGawain(), 1);
        numberOfEachAdventureCard.put(new SirLancelot(), 1);
        numberOfEachAdventureCard.put(new SirPercival(), 1);
        numberOfEachAdventureCard.put(new SirTristan(), 1);
        numberOfEachAdventureCard.put(new Amour("Amour", "Amour.jpg"), 8);
        logger.info("storing all allies and their instances in numberOfEachAdventureCard HashMap");


        //Tests
        numberOfEachAdventureCard.put(new TestOfMorganLeFey(), 2);
        numberOfEachAdventureCard.put(new TestOfTemptation(), 2);
        numberOfEachAdventureCard.put(new TestOfTheQuestingBeast(), 2);
        numberOfEachAdventureCard.put(new TestOfValor(), 2);
        //Add each AdventureCard to deckOfAdventureCards

        for(AdventureCard adventureCard : numberOfEachAdventureCard.keySet()){
            for(int i = 0; i < numberOfEachAdventureCard.get(adventureCard); i++) {
                deckOfAdventureCards.add(adventureCard);
            }
        }
        deckOfAdventureCards.add(new Merlin());
        logger.info("storing all adventure cards into the deck of adventure cards.");

        //Create HashMap to store number of occurrences of each StoryCard

        HashMap<StoryCard, Integer> numberOfEachStoryCard = new HashMap<>();

        //Quests
        numberOfEachStoryCard.put(new BoarHunt(), 2);
        numberOfEachStoryCard.put(new RepelTheSaxonRaiders(), 2);
        numberOfEachStoryCard.put(new RescueTheFairMaiden(), 1);
        numberOfEachStoryCard.put(new SearchForTheHolyGrail(), 1);
        numberOfEachStoryCard.put(new SearchForTheQuestingBeast(),1);
        numberOfEachStoryCard.put(new SlayTheDragon(),1);
        numberOfEachStoryCard.put(new TestOfTheGreenKnight(), 1);
        numberOfEachStoryCard.put(new VanquishKingArthursEnemies(), 2);
        logger.info("storing all quests and their instances in numberOfEachStoryCard HashMap");

        //Events
        ProsperityThroughoutTheRealm prosp = new ProsperityThroughoutTheRealm();
        prosp.addChangeListener(this);

        QueensFavor fav = new QueensFavor();
        fav.addChangeListener(this);

        KingsCallToArms call = new KingsCallToArms();
        call.addChangeListener(this);

        numberOfEachStoryCard.put(new ChivalrousDeed(), 1);
        numberOfEachStoryCard.put(new CourtCalledToCamelot(), 2);
        numberOfEachStoryCard.put(call, 1);
        numberOfEachStoryCard.put(new KingsRecognition(), 2);
        numberOfEachStoryCard.put(new Plague(), 1);
        numberOfEachStoryCard.put(new Pox(), 1);
        numberOfEachStoryCard.put(prosp, 1);
        numberOfEachStoryCard.put(fav, 2);
        logger.info("storing all events and their instances in numberOfEachStoryCard HashMap");

        //Tournaments
        numberOfEachStoryCard.put(new TournamentAtCamelot(), 1);
        numberOfEachStoryCard.put(new TournamentAtOrkney(), 1);
        numberOfEachStoryCard.put(new TournamentAtTintagel(), 1);
        numberOfEachStoryCard.put(new TournamentAtYork(), 1);
        logger.info("storing all tournaments and their instances in numberOfEachStoryCard HashMap");

        //Add each StoryCard to deckOfStoryCards
        for(StoryCard storyCard : numberOfEachStoryCard.keySet()){
            for(int i = 0; i < numberOfEachStoryCard.get(storyCard); i++) {
                deckOfStoryCards.add(storyCard);
            }
        }
        deckOfStoryCards.add(fav);
        deckOfStoryCards.add(prosp);

        logger.info("storing all story cards into the deck of story cards.");
    }

    public ArrayList<Player> getPlayers() {
        logger.info("Returning list of player in game.");
        return players;
    }

    public Stack<AdventureCard> getDeckOfAdventureCards() {
        logger.info("Returning deck of adventure cards.");
        return deckOfAdventureCards;
    }

    public Stack<StoryCard> getDeckOfStoryCards() {
        logger.info("Returning deck of story cards.");
        return deckOfStoryCards;
    }

    public int getCurrentTurnIndex() {
        logger.info("Returning current index for player turn.");
        return currentTurnIndex;
    }

    public void nextTurn(){
        if(currentTurnIndex == players.size()){
            currentTurnIndex = 0;
            logger.info("Set current index for player turn to "+ currentTurnIndex +".");
        }
        else{
            currentTurnIndex++;
            logger.info("Set current index for player turn to "+ currentTurnIndex +".");
        }
        if(activePlayer instanceof AbstractAI){
            runAITurn();
        } else {
            notifyListeners("nextTurn", Boolean.TRUE, -1, currentTurnIndex);
        }
    }

    private void runAITurn(){
        drawStoryCard();
        ArrayList<Player> serverplayers = players;
        System.out.println("storyDeckDraw(): " + currentStory.getName());
        ArrayList<Player> currentPlayerOrder = new ArrayList<>();
        int currentTurn = serverplayers.indexOf(activePlayer);
        for(int i = 0; i < players.size(); i++){
            currentPlayerOrder.add(serverplayers.get(currentTurn));
            currentTurn = nextPlayerIndex(currentTurn);
        }
//        ArrayList<Player> noAIPlayers = new ArrayList<>();
//        for(Player player : getPlayers()){
//            if(!(player instanceof AbstractAI)){
//                noAIPlayers.add(player);
//            }
//        }
        if (currentStory instanceof Quest) {
            setCurrentQuest((Quest) currentStory);
            questDraw(activePlayer);
        } else if (currentStory instanceof Event) {
            Event gameEvent = (Event) currentStory;
            applyEventEffect(gameEvent);
        } else if (currentStory instanceof Tournament) {
            setCurrentTournament((Tournament) currentStory);
//            performTournament(currentPlayerOrder, getCurrentTournament());
        }
    }

    public void addPlayerToGame(Player player){
        players.add(player);
    }

    public void addPlayer(String playerType, String name){
        if(playerType.equals("Human")){
            Player newPlayer = new Player(name);
            newPlayer.addChangeListener(this);
            players.add(newPlayer);
        } else {
            AbstractAI aI;
            if(name.toLowerCase().endsWith("_s2")) {
                aI = new Strategy2(name.substring(0,name.length() - 3));
            } else if (name.toLowerCase().endsWith("_s1")){
                aI = new Strategy1(name.substring(0,name.length() - 3));
            } else {
                aI = new Strategy1(name);
            }
            System.out.println(aI.strategy);
            aI.addChangeListener(this);
            players.add(aI);
        }

        logger.info(name + "is joining the game.");
    }

    public void removeFromStoryDeck(StoryCard storyCard){
        logger.info("Removing:"+storyCard.getName()+" from the story card.");
        deckOfStoryCards.remove(storyCard);
    }

    public void addCardToSponsorHand(AdventureCard card){
        logger.info("Adding:"+card.getName()+" to sponsor hand.");
        sponsor.addCardToHand(card);
    }

    public void dealCards(Stack<AdventureCard> deck){
        //Collections.shuffle(deckOfAdventureCards);
        for(Player player : players) {
            int NUM_CARDS = 12;
            for (int i = 0; i < NUM_CARDS; i++) {
                if (!(deck.empty())) {
                    player.addCardToHand(deck.pop());
                }
            }
        }
        logger.info(" Deal 12 cards to each players.");
    }

    public void addToAdventureDeck(AdventureCard adventureCard){
        logger.info(" Add a card to the adventure card:"+adventureCard.getName()+" ." );
        deckOfAdventureCards.push(adventureCard);
    }

    public void addToStoryDeck(StoryCard storyCard){
        deckOfStoryCards.push(storyCard);
    }

    public void drawAdventureCard(Player ActivePlayer){
        logger.info(ActivePlayer.getPlayerName() + " draw an adventure card.");
        //check if hand is full, if so set some state in the player is full. then in controller create an alert that says hand is full.
        ActivePlayer.addCardToHand(deckOfAdventureCards.pop());
    }

    void addToDiscardAdventure(AdventureCard card){
        logger.info("Discarding :"+card.getName()+" to the discard card.");
        discardOfAdventureCards.add(card);
    }

    public void addToPotentialStage(AdventureCard card, int stageNum){
        logger.info("Add the following card"+ card.getName()+ "the following potential stage"+stageNum+"to pre-stage");
        preQuestStageSetup.get(stageNum).add(card);
    }
    public void setPotentialStage(ArrayList<AdventureCard> stage, int stageNum){
        logger.info("Set the following potential stage"+stageNum+"to pre-stage");
        preQuestStageSetup.put(stageNum,stage);
    }
    public void removeFromPotentialStage(AdventureCard card, int stageNum){
        logger.info("Remove the following card"+ card.getName()+ "the following potential stage"+stageNum+"to pre-stage");
        preQuestStageSetup.get(stageNum).remove(card);
    }
    public void resetPotentialStages(){
        preQuestStageSetup.clear();
        for(int i = 0; i<currentQuest.getNumStage();i++){
            preQuestStageSetup.put(i,new ArrayList<>());
        }
        logger.info("Resetting potential pre-stage");
    }

//    private ArrayList<Player> finalTournament(ArrayList<Player> tournamentParticipants){
//        Tournament knightsOfTheRoundTableTournament = new Tournament("Knights of the Round Table Tournament", "", 0, tournamentParticipants);
//        return knightsOfTheRoundTableTournament.getTournamentWinner();
//    }

    public ArrayList<Player> getWinningPlayers() {
        logger.info(" returning winning player.");
        return winningPlayers;
    }

    public boolean isValidDrop(AdventureCard card, int stageNum){
        if (card instanceof Ally) {//?
            return false;
        }
        for(AdventureCard matchCard: getPreQuestStageSetup().get(stageNum)){
            if(card.getName().equals(matchCard.getName())){
                return false;
            }
        }
        return true;
    }

    public boolean isWinner() {
        logger.info(" Verifying if there only one winner");
        ArrayList<Player> knightsOfTheRoundTable = new ArrayList<>();
        for(Player player : players){
            if (player.getPlayerRank() == KNIGHT_OF_THE_ROUND_TABLE) {
                knightsOfTheRoundTable.add(player);
            }
        }
        if (knightsOfTheRoundTable.size() == 1 ){
            winningPlayers = knightsOfTheRoundTable;
            System.out.println(winningPlayers);
            return true;
        }
        else if (knightsOfTheRoundTable.size() > 1) {
            winningPlayers =knightsOfTheRoundTable;
            return true;
        }
        return false;

    }

    private int nextPlayerIndex(int index){
        int nextIndex = index;
        if(nextIndex >= players.size()-1){
            nextIndex = 0;
        } else{
            nextIndex++;
        }
        return nextIndex;
    }

    public void drawStoryCard(){
        if(!(deckOfStoryCards.isEmpty())) {
            System.out.println("DRAW STORY CARD CALLED");
            currentStory = deckOfStoryCards.pop();
            currentPlayerOrder.clear();
            int currentTurn = currentTurnIndex;

            for (int i = 0; i < players.size(); i++) {
                currentPlayerOrder.add(players.get(currentTurn));
                currentTurn = nextPlayerIndex(currentTurn);
            }

            if (currentStory instanceof Quest) {
                setCurrentQuest((Quest) currentStory);
                currentPlayerOrderIndex = 0;
                questDraw(currentPlayerOrder.get(currentPlayerOrderIndex));
            } else if (currentStory instanceof Event) {
                Event gameEvent = (Event) currentStory;
                System.out.println("PRE-EVENT: " + gameEvent);
                for(Player p : players){
                    System.out.println(p.getCardsInHand().size());
                }
                applyEventEffect(gameEvent);
                System.out.println("APPLIED EVENT: " + gameEvent);
                for(Player p : players){
                    System.out.println(p.getCardsInHand().size());
                }
                notifyListeners("event complete",Boolean.TRUE);
            } else if (currentStory instanceof Tournament) {
                setCurrentTournament((Tournament) currentStory);
                //performTournament(currentPlayerOrder, getCurrentTournament());
            }
            if(currentStory instanceof KingsRecognition){
                kingsRecognition = true;
            }
            logger.info("Draw card from story deck.");
        }
    }

    private void questDraw(Player player) {
        //Player sponsor;
        setActivePlayer(player);
        int validCardCount = 0;
        for(AdventureCard adventureCard : player.getCardsInHand()){
            if((adventureCard instanceof Foe) || (adventureCard instanceof Test)) {
                validCardCount++;
            }
        }
        if(validCardCount < currentQuest.getNumStage()){
            if(!(player instanceof AbstractAI)) {
                notifyListeners("unable to sponsor", player);
            }
        } else {
            if (!(player instanceof AbstractAI)){
                notifyListeners("would you like to sponsor",player);
//                    if (alertResult) {
//                        sponsor = activePlayer;
//                        setSponsor(sponsor);
//                        performQuest(sponsor, (Quest) serverResponse);
//                        nextTurnButton.setVisible(false);
//                        continueButton.setVisible(true);
//                        break;
//                    }
            }
            else{
                //AI CODE
                player.getCardsInHand();
                ((AbstractAI) player).doISponsor(currentPlayerOrder, player.getCardsInHand(), (Quest) currentQuest);
                boolean aiResult = ((AbstractAI) player).doISponsor(currentPlayerOrder, player.getCardsInHand(), (Quest) currentQuest);
                if (aiResult) {
                    sponsor = activePlayer;
                    setSponsor(sponsor);
//                    performQuest(sponsor, (Quest) currentQuest);
                }
            }
        }
        //MOVE TO OWN METHOD
//        if(getSponsor() == null){
//            setActivePlayer(currentTurnPlayer);
//            nextTurnButton.setVisible(true);
//            nextTurnButton.setDisable(false);
//            continueButton.setVisible(false);
//        }
    }

    public void declineSponsor(){
        currentPlayerOrderIndex++;
        if (currentPlayerOrderIndex > players.size()){
            currentPlayerOrderIndex = 0;
            setActivePlayer(players.get(currentTurnIndex));
            notifyListeners("no sponsor", activePlayer);
        }
        else {
            currentPlayerOrderIndex++;
            questDraw(players.get(currentPlayerOrderIndex));
        }
    }

    public void performQuest(Player sponsor) {
       currentQuest.addChangeListener(this);
       setSponsor(sponsor);
       currentQuest.setSponsor(sponsor);
       setActivePlayer(sponsor);
       //add these bits to the notification
        System.out.println("BIG FUCK");
        notifyListeners("perform quest",sponsor);

//        for(int i = 0;i<quest.getNumStage();i++){
//            createStagePane(i);
//        }
//        addQuestPlayers(quest);
//        if(sponsor instanceof AbstractAI){
//            serverSetPotentialStage(((AbstractAI) sponsor).sponsorQuestFirstStage(sponsor.getCardsInHand()),0);
//            sponsor.removeCardsAI(((AbstractAI) sponsor).sponsorQuestFirstStage(sponsor.getCardsInHand()));
//            for(int i=1; i<quest.getNumStage()-1;i++){
//                serverSetPotentialStage(((AbstractAI) sponsor).sponsorQuestMidStage(sponsor.getCardsInHand()),i);
//                sponsor.removeCardsAI(((AbstractAI) sponsor).sponsorQuestMidStage(sponsor.getCardsInHand()));
//            }
//            serverSetPotentialStage(((AbstractAI) sponsor).sponsorQuestLastStage(sponsor.getCardsInHand()),quest.getNumStage()-1);
//            sponsor.removeCardsAI(((AbstractAI) sponsor).sponsorQuestLastStage(sponsor.getCardsInHand()));
//            for(int i = 0; i<serverGetCurrentQuest().getNumStage();i++){
//                serverAddStageToCurrentQuest(i);
//            }
//            setCurrentBehaviour(Behaviour.QUEST_MEMBER);
//            serverGetCurrentQuest().startQuest();
//            if(!serverGetCurrentQuest().isInTest()){
//                setCurrentBehaviour(Behaviour.QUEST_MEMBER);
//                setActivePlayer(serverGetCurrentQuest().getCurrentPlayer());
//            }
//        }
    }

    public void continueAction(String behaviour){
        switch (behaviour) {
            case "SPONSOR":
                continueSponsor();
                break;
            case "QUEST_MEMBER":
                continueQuestMember();
                break;
            case "TOURNAMENT":
                continueTournament();
                break;
        }
    }

    private void continueSponsor(){
        if (validateQuestStages()) {
            for(int i = 0; i<getCurrentQuest().getNumStage();i++){
                addStageToCurrentQuest(i);
            }
            notifyListeners("set behaviour", Boolean.TRUE, "","QUEST_MEMBER");
            currentQuest.startQuest();
            if(!getCurrentQuest().isInTest()){
                notifyListeners("set behaviour", Boolean.TRUE, "","QUEST_MEMBER");
                setActivePlayer(getCurrentQuest().getCurrentPlayer());
            }
        } else {
            notifyListeners("setup valid quest", activePlayer);
            for (int i = 0; i < getCurrentQuest().getNumStage(); i++) {
                for (AdventureCard card : getPreQuestStageSetup().get(i)) {
                    //ADD METHOD ADD CARD TO SPONSOR HAND
                    addCardToSponsorHand(card);
                }
            }
            resetPotentialStages();
        }
    }

    private void continueQuestMember(){
        getCurrentQuest().nextTurn();
        if(getCurrentQuest().isFinished()){
            getWinningPlayers();
        }
        else{
            if(getCurrentQuest().isInTest()) {
                setActivePlayer(getCurrentQuest().getCurrentPlayer());
            }
            else{
                setActivePlayer(getCurrentQuest().getCurrentPlayer());
            }
        }
    }

    private void continueTournament(){
        getCurrentTournament().nextTurn();
        if(getCurrentTournament().isTournamentOver()){
            if(isWinner()){
                System.out.println("Game over," + getWinningPlayers().get(0) + " wins");
                System.exit(0);
            }
            else{
                tournamentOver();
            }
        }
        else{
            setActivePlayer(getCurrentTournament().getCurrentPlayer());
        }
    }

    //TOURNAMENT
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    private void performTournament(ArrayList<Player> currentPlayerOrder, Tournament tournament) {
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
                    ai.playCardsAI(ai.whatIPlay(ai.getCardsInHand(), getPlayers(), getCurrentTournament().getShields()));
                    getCurrentTournament().nextTurn();
                    if(getCurrentTournament().isTournamentOver()){
                        tournamentOver();
                    }
                    else{
                        setActivePlayer(getCurrentTournament().getCurrentPlayer());
                    }
                }
            }
        }

    }

    private void addTournamentPlayers(Tournament currentTournament){
        ArrayList<Player> tournamentPlayers = new ArrayList<>();
        ArrayList<Player> serverPlayers = getPlayers();
        for(int i = 0; i < players.size(); i++){
            activePlayer = serverPlayers.get(i);
            if(!(activePlayer instanceof AbstractAI)) {
                    notifyListeners("join tournament", activePlayer);
                    //-----------------------------------------------------------
                     tournamentPlayers.add(serverPlayers.get(i));
                     //----------------------------------------------------------
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
        if(getCurrentTournament().getName().equals("Tournament Final")){
            for (Player player : getCurrentTournament().getWinners()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, player.getPlayerName() + " won the the Game!");
                DialogPane dialog = alert.getDialogPane();
                dialog.getStylesheets().add(getClass().getResource("/CSS/Alerts.css").toExternalForm());
                dialog.getStyleClass().add("alertDialogs");
                alert.showAndWait();
            }
            exit(0);
        }

        for (Player player : getCurrentTournament().getWinners()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, player.getPlayerName() + " won the the Tournament, and received " + getCurrentTournament().getShields() + " shields!", ButtonType.OK);
            DialogPane dialog = alert.getDialogPane();
            dialog.getStylesheets().add(getClass().getResource("/CSS/Alerts.css").toExternalForm());
            dialog.getStyleClass().add("alertDialogs");
            alert.showAndWait();
        }
        getCurrentTournament().setTournamentOver(true);
        setCurrentTournament(null);
        setActivePlayer(getPlayers().get(getCurrentTurnIndex()));
        getWinningPlayers();
    }

    private void handFull(Player player,boolean oldFull){
        logger.info("Notify that the hand is full.");
        if(player instanceof AbstractAI){
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
        } else {
            notifyListeners("handfull", player, oldFull, true);
        }
    }

    private void callToArms(Player player){
        //later do somethign different here if player type is AI
        logger.info("Call the arm to track for this"+player.getPlayerName()+".");
        notifyListeners(player);
    }
    private void notifyListeners(Object object) {
        logger.info("Notify listener of callToArm");
        for (PropertyChangeListener name : listener) {
            name.propertyChange(new PropertyChangeEvent(object, "callToArms","",""));
        }
    }

    private void notifyListeners(String property, Object object) {
        logger.info("Notify listener of" +property);
        for (PropertyChangeListener name : listener) {
            name.propertyChange(new PropertyChangeEvent(object, property,"",""));
        }
    }

    private void notifyListeners(String property, Object object, boolean oldFull, boolean newFull) {

        logger.info("Inform listener of the "+ property);
        for (PropertyChangeListener name : listener) {
            name.propertyChange(new PropertyChangeEvent(object, property, oldFull, newFull));
        }
    }

    private void notifyListeners(String property, Object object, int oldFull, int newFull) {
        logger.info("Inform listener of the "+ property);
        for (PropertyChangeListener name : listener) {
            name.propertyChange(new PropertyChangeEvent(object, property, oldFull, newFull));
        }
    }

    private void notifyListeners(String property, Object object, String oldFull, String newFull) {
        logger.info("Inform listener of the "+ property);
        for (PropertyChangeListener name : listener) {
            name.propertyChange(new PropertyChangeEvent(object, property, oldFull, newFull));
        }
    }

    public void addChangeListener(PropertyChangeListener newListener) {
        logger.info("Adding a new listener");
        listener.add(newListener);
    }

    @Override
    public void propertyChange(PropertyChangeEvent change) {
        switch (change.getPropertyName()) {
            case "stage":
                if (change.getOldValue() != change.getNewValue()) {
                    for (Player player : currentQuest.getPlayerList()) {
                        drawAdventureCard(player);
                    }
                }
                break;
            case "handFull":
                if ((Boolean) change.getNewValue()) {
                     handFull((Player)change.getSource(),(Boolean)change.getOldValue());
                }
                break;
            case "deckDraw": {
                Player drawPlayer = (Player) change.getSource();
                drawPlayer.addCardToHand(deckOfAdventureCards.pop());
                if (drawPlayer.isHandFull()) {
                    handFull(drawPlayer, false);
                }
                break;
            }
            case "callToArms": {
                Player drawPlayer = (Player) change.getSource();
                callToArms(drawPlayer);
                break;
            }
        }
    }
}


