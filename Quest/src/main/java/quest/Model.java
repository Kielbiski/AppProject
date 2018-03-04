package quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Array;
import java.util.*;

public class Model
{
    private static final Logger logger = LogManager.getLogger(App.class);

    private ArrayList<Player> players = new ArrayList<>();
    private Stack<AdventureCard> deckOfAdventureCards = new Stack<>();
    private Stack<StoryCard> deckOfStoryCards = new Stack<>();
    private HashMap<Integer,ArrayList<AdventureCard>> preQuestStageSetup = new HashMap<>();
    private StoryCard currentStory;
    private Quest currentQuest;
    private Player sponsor;
    private int currentTurnIndex = 0;
    private int NUM_CARDS = 12;


    public StoryCard getCurrentStory() {
        logger.info("Returning current story.");
        return currentStory;
    }

    public HashMap<Integer, ArrayList<AdventureCard>> getPreQuestStageSetup() {
        return preQuestStageSetup;
    }

    public void setCurrentQuest(Quest currentQuest) {
        this.currentQuest = currentQuest;
        preQuestStageSetup.clear();
        for(int i = 0; i<currentQuest.getNumStage();i++){
            preQuestStageSetup.put(i,new ArrayList<AdventureCard>());
        }
    }

    public Quest getCurrentQuest() {
        return currentQuest;
    }

    public Player getSponsor() {
        logger.info("Returning the sponsor.");
        return sponsor;
    }

    public void setSponsor(Player sponsor) {
        logger.info("Set the sponsor.");
        this.sponsor = sponsor;
    }

    private QuestStage createStage(ArrayList<AdventureCard> cardsForStage){
        for(AdventureCard adventureCard : cardsForStage) {
            if (adventureCard instanceof Foe) {
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
                for (AdventureCard adventureCard :  getPreQuestStageSetup().get(i)) {
                    if ((adventureCard instanceof Test) && (getPreQuestStageSetup().get(i).size() > 1)) {
                        return false;
                    }
                    if ((adventureCard instanceof Ally)) {
                        return false;
                    }
                    currentStageBattlePoints += adventureCard.getBattlePoints();
                    if (adventureCard instanceof Foe) {
                        foeCount++;
                    }
                }
                if (currentStageBattlePoints > lastStageBattlePoints) {
                    lastStageBattlePoints = currentStageBattlePoints;
                } else {
                    return false;
                }
                if (foeCount > 1) {
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
        numberOfEachStoryCard.put(new ChivalrousDeed(), 1);
        numberOfEachStoryCard.put(new CourtCalledToCamelot(), 2);
        numberOfEachStoryCard.put(new KingsCallToArms(), 1);
        numberOfEachStoryCard.put(new KingsRecognition(), 2);
        numberOfEachStoryCard.put(new Plague(), 1);
        numberOfEachStoryCard.put(new Pox(), 1);
        numberOfEachStoryCard.put(new ProsperityThroughoutTheRealm(), 1);
        numberOfEachStoryCard.put(new QueensFavor(), 2);
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

    public void addPlayer(String name){
        players.add(new Player(name));
        logger.info(name + "is joining the game.");
    }

    void shuffleAndDeal(){
        Collections.shuffle(deckOfAdventureCards);
        for(Player player : players) {
            for (int i = 0; i < NUM_CARDS; i++) {
                if (!(deckOfAdventureCards.empty())) {
                    player.addCardToHand(deckOfAdventureCards.pop());
                }
            }
        }
        logger.info(" Deal 12 cards to each players.");
    }

    void drawAdventureCard(Player currentPlayer){
        logger.info(currentPlayer.getPlayerName() + " draw an adventure card.");
        currentPlayer.addCardToHand(deckOfAdventureCards.pop());
    }

    void addToPotentialStage(AdventureCard card, int stageNum){
        preQuestStageSetup.get(stageNum).add(card);
    }
    void resetPotentialStages(){
        preQuestStageSetup.clear();
        for(int i = 0; i<currentQuest.getNumStage();i++){
            preQuestStageSetup.put(i,new ArrayList<AdventureCard>());
        }
    }

    void drawStoryCard(){
        if(!(deckOfStoryCards.isEmpty())) {
            currentStory = deckOfStoryCards.pop();
            logger.info("Draw card from story deck.");
        }
    }
}

