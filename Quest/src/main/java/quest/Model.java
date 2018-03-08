package quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Array;
import java.util.*;

import static quest.Rank.KNIGHT_OF_THE_ROUND_TABLE;

public class Model implements PropertyChangeListener
{
    private static final Logger logger = LogManager.getLogger(App.class);

    private ArrayList<Player> players = new ArrayList<>();
    private Stack<AdventureCard> deckOfAdventureCards = new Stack<>();
    private Stack<StoryCard> deckOfStoryCards = new Stack<>();
    private Stack<AdventureCard> discardOfAdventureCards = new Stack<>();
    private Stack<StoryCard> discardOfStoryCards = new Stack<>();
    private HashMap<Integer,ArrayList<AdventureCard>> preQuestStageSetup = new HashMap<>();
    private StoryCard currentStory;
    private Player currentPlayer;
    private Quest currentQuest;
    private Tournament currentTournament;

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Tournament getCurrentTournament() {
        return currentTournament;
    }

    public void setCurrentTournament(Tournament currentTournament) {
        this.currentTournament = currentTournament;
    }

    private Player sponsor;
    private int currentTurnIndex = 0;
    private List<PropertyChangeListener> listener = new ArrayList<>();
    private ArrayList<Player> winningPlayers = new ArrayList<>();
    private boolean kingsRecognition = false;

    /////////////////////////////////////////////////

    /////////////////////////////////////////////////

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

    public QuestStage createStage(ArrayList<AdventureCard> cardsForStage){
        for(AdventureCard adventureCard : cardsForStage) {
            if (adventureCard instanceof Foe) {
                if(currentQuest.getQuestFoes().contains(adventureCard)){
                    System.out.println("Before" + adventureCard.getName() + " -> " + adventureCard.getBattlePoints());
                    adventureCard.setBattlePoints(adventureCard.getBattlePoints() + adventureCard.getBonusBattlePoints());
                    System.out.println("After" + adventureCard.getName() + " -> " + adventureCard.getBattlePoints());
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
        for (int i = 0; i < getCurrentQuest().getNumStage(); i++) {
                int currentStageBattlePoints = 0;
                int foeCount = 0;
                int testCount =0;
                for (AdventureCard adventureCard :  getPreQuestStageSetup().get(i)) {
                    if (adventureCard instanceof Test) {
                        if(getPreQuestStageSetup().get(i).size() > 1) {
                            return false;
                        }
                        testCount++;
                    }
                    if ((adventureCard instanceof Ally)) {
                        return false;
                    }
                    if (adventureCard instanceof Foe) {
                        for(AdventureCard foe : currentQuest.getQuestFoes()){
                            if(adventureCard.getName().toLowerCase().equals(foe.getName().toLowerCase())){
                                adventureCard.setBattlePoints(adventureCard.getBattlePoints() + adventureCard.getBonusBattlePoints());
                                break;
                            }
                        }
                        foeCount++;
                    }
                    currentStageBattlePoints += adventureCard.getBattlePoints();
                }
                if (currentStageBattlePoints > lastStageBattlePoints) {
                    lastStageBattlePoints = currentStageBattlePoints;
                } else {
                    return false;
                }
                if (foeCount > 1) {
                    return false;
                }
                if(foeCount==0 && testCount ==0){
                    return false;
                }
        }
        return true;
    }

    Model() {



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
        logger.info("storing all weapons and their instances in numberOfEachAdventureCard HashMap");

        //Add each AdventureCard to deckOfAdventureCards

        for(AdventureCard adventureCard : numberOfEachAdventureCard.keySet()){
            for(int i = 0; i < numberOfEachAdventureCard.get(adventureCard); i++) {
                deckOfAdventureCards.add(adventureCard);
            }
        }
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

        deckOfStoryCards.add(new SearchForTheHolyGrail());

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
        if(currentTurnIndex == 3){
            currentTurnIndex = 0 ;
            logger.info("Set current index for player turn to "+ currentTurnIndex +".");
        }
        else{
            currentTurnIndex++;
            logger.info("Set current index for player turn to "+ currentTurnIndex +".");
        }
    }

    public void addPlayer(String playerType, String name){
        if(playerType.equals("Human")){
            Player newPlayer = new Player(name);
            newPlayer.addChangeListener(this);
            players.add(newPlayer);
        } else {
            Strategy1 aI = new Strategy1(name);
            aI.addChangeListener(this);
            players.add(aI);
        }

        logger.info(name + "is joining the game.");
    }

    void shuffleAndDeal(){
        Collections.shuffle(deckOfAdventureCards);

        for(Player player : players) {
            int NUM_CARDS = 12;
            for (int i = 0; i < NUM_CARDS; i++) {
                if (!(deckOfAdventureCards.empty())) {
                    player.addCardToHand(deckOfAdventureCards.pop());
                }
            }
        }
        logger.info(" Deal 12 cards to each players.");
    }

    public void drawAdventureCard(Player currentPlayer){
        logger.info(currentPlayer.getPlayerName() + " draw an adventure card.");
        //check if hand is full, if so set some state in the player is full. then in controller create an alert that says hand is full.
        currentPlayer.addCardToHand(deckOfAdventureCards.pop());
    }


    void addToDiscardAdventure(AdventureCard card){
        discardOfAdventureCards.add(card);
    }

    void addToPotentialStage(AdventureCard card, int stageNum){
        preQuestStageSetup.get(stageNum).add(card);
    }
    void removeFromPotentialStage(AdventureCard card, int stageNum){
        preQuestStageSetup.get(stageNum).remove(card);
    }
    void resetPotentialStages(){
        preQuestStageSetup.clear();
        for(int i = 0; i<currentQuest.getNumStage();i++){
            preQuestStageSetup.put(i,new ArrayList<>());
        }
    }

//    private ArrayList<Player> finalTournament(ArrayList<Player> tournamentParticipants){
//        Tournament knightsOfTheRoundTableTournament = new Tournament("Knights of the Round Table Tournament", "", 0, tournamentParticipants);
//        return knightsOfTheRoundTableTournament.getTournamentWinner();
//    }

    public ArrayList<Player> getWinningPlayers() {
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
        ArrayList<Player> knightsOfTheRoundTable = new ArrayList<>();
        for(Player player : players){
            if (player.getPlayerRank() == KNIGHT_OF_THE_ROUND_TABLE) {
                knightsOfTheRoundTable.add(player);
            }
        }
        if (knightsOfTheRoundTable.size() == 1 ){
            winningPlayers = knightsOfTheRoundTable;
            return true;

        }
//        else if (knightsOfTheRoundTable.size() > 1) {
//            winningPlayers = finalTournament(knightsOfTheRoundTable);
//            return true;
//        }
        return false;

    }


    void drawStoryCard(){
        if(!(deckOfStoryCards.isEmpty())) {
            currentStory = deckOfStoryCards.pop();
            if(currentStory instanceof KingsRecognition){
                kingsRecognition = true;
            }
            logger.info("Draw card from story deck.");
        }
    }

    private void handFull(Player player,boolean oldFull){
        //later do somethign different here if player type is AI
        notifyListeners(player,oldFull,true);

    }
    private void callToArms(Player player){
        //later do somethign different here if player type is AI
        notifyListeners(player);
    }
    private void notifyListeners(Object object, boolean oldFull, boolean newFull) {
        for (PropertyChangeListener name : listener) {
            name.propertyChange(new PropertyChangeEvent(object, "handFull", oldFull, newFull));
        }
    }
    private void notifyListeners(Object object) {
        for (PropertyChangeListener name : listener) {
            name.propertyChange(new PropertyChangeEvent(object, "callToArms","",""));
        }
    }

    public void addChangeListener(PropertyChangeListener newListener) {
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
                    System.out.println("");
                    //reneable with AI when jay fixes his shit
                    // handFull((Player)change.getSource(),(Boolean)change.getOldValue());
                }
                break;
            case "deckDraw": {
                Player drawPlayer = (Player) change.getSource();
                boolean wasFull = drawPlayer.isHandFull();
                drawPlayer.addCardToHand(deckOfAdventureCards.pop());
                if (drawPlayer.isHandFull()) {
                    handFull(drawPlayer, wasFull);
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

