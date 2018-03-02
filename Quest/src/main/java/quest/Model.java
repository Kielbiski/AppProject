package quest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

public class Model
{
    private ArrayList<Player> players = new ArrayList<>();
    private Stack<AdventureCard> deckOfAdventureCards = new Stack<>();
    private Stack<StoryCard> deckOfStoryCards = new Stack<>();
    private StoryCard currentStory;
    private Player sponsor;
    private int currentTurnIndex = 0;
    private int NUM_CARDS = 12;

    public StoryCard getCurrentStory() {
        return currentStory;
    }

    public Player getSponsor() {
        return sponsor;
    }

    public void setSponsor(Player sponsor) {
        this.sponsor = sponsor;
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

        ArrayList<Weapon> allWeapons = new ArrayList<>();
        allWeapons.add(new BattleAx());
        allWeapons.add(new Dagger());
        allWeapons.add(new Excalibur());
        allWeapons.add(new Horse());
        allWeapons.add(new Lance());
        allWeapons.add(new Sword());

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

        //Weapons
        numberOfEachAdventureCard.put(new BattleAx(), 8);
        numberOfEachAdventureCard.put(new Dagger(), 6);
        numberOfEachAdventureCard.put(new Excalibur(), 2);
        numberOfEachAdventureCard.put(new Horse(), 11);
        numberOfEachAdventureCard.put(new Lance(), 6);
        numberOfEachAdventureCard.put(new Sword(), 16);

        //Add each AdventureCard to deckOfAdventureCards

        for(AdventureCard adventureCard : numberOfEachAdventureCard.keySet()){
            for(int i = 0; i < numberOfEachAdventureCard.get(adventureCard); i++) {
                deckOfAdventureCards.add(adventureCard);
            }
        }

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

        //Events
        numberOfEachStoryCard.put(new ChivalrousDeed(), 1);
        numberOfEachStoryCard.put(new CourtCalledToCamelot(), 2);
        numberOfEachStoryCard.put(new KingsCallToArms(), 1);
        numberOfEachStoryCard.put(new KingsRecognition(), 2);
        numberOfEachStoryCard.put(new Plague(), 1);
        numberOfEachStoryCard.put(new Pox(), 1);
        numberOfEachStoryCard.put(new ProsperityThroughoutTheRealm(), 1);
        numberOfEachStoryCard.put(new QueensFavor(), 2);

        //Tournaments
        numberOfEachStoryCard.put(new TournamentAtCamelot(), 1);
        numberOfEachStoryCard.put(new TournamentAtOrkney(), 1);
        numberOfEachStoryCard.put(new TournamentAtTintagel(), 1);
        numberOfEachStoryCard.put(new TournamentAtYork(), 1);

        //Add each StoryCard to deckOfStoryCards

        for(StoryCard storyCard : numberOfEachStoryCard.keySet()){
            for(int i = 0; i < numberOfEachStoryCard.get(storyCard); i++) {
                deckOfStoryCards.add(storyCard);
            }
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Stack<AdventureCard> getDeckOfAdventureCards() {
        return deckOfAdventureCards;
    }

    public Stack<StoryCard> getDeckOfStoryCards() {
        return deckOfStoryCards;
    }

    public int getCurrentTurnIndex() {
        return currentTurnIndex;
    }

    public void nextTurn(){
        if(currentTurnIndex == 3){
            currentTurnIndex = 0 ;
        }
        else{
            currentTurnIndex++;
        }
    }

    public void addPlayer(String name){
        players.add(new Player(name));
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
    }

    void drawAdventureCard(Player currentPlayer){
        currentPlayer.addCardToHand(deckOfAdventureCards.pop());
    }

    void drawStoryCard(){
        if(!(deckOfStoryCards.isEmpty())) {
            currentStory = deckOfStoryCards.pop();
            System.out.println("drawStoryCard(): " + currentStory.getName());
        }
    }
}
