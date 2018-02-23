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
    private int currentTurnIndex = 0;
    private int NUM_CARDS = 12;

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
        Quest boarhunt = new Quest("Boar Hunt", "Q_Boar_Hunt.jpg", 2, new Boar());
        Quest defendTheQueensHonor = new Quest("Defend The Queen's Honor", "Q_Defend_The_Queens_Honor.jpg", 4, allFoes);
        Quest journeyThroughTheEnchantedForest = new Quest("Journey Through The Enchanted Forest", "Q_Journey_Through_The_Enchanted_Forest.jpg", 3, new EvilKnight());
        ArrayList<Foe> saxonFoes = new ArrayList<>();
        saxonFoes.add(new SaxonKnight());
        saxonFoes.add(new Saxons());
        Quest repelTheSaxonRaiders = new Quest("Repel The Saxon Raiders", "Q_Repel_The_Saxon_Raiders.jpg", 2, saxonFoes);
        Quest rescueTheFairMaiden = new Quest("Rescue The Fair Maiden", "Q_Rescue_The_Fair_Maiden.jpg", 3, new BlackKnight());
        Quest testOfTheGreenKnight = new Quest("Test Of The Green Knight", "Q_Test_Of_The_Green_Knight.jpg", 4, new GreenKnight());
        Quest searchForTheQuestingBeast = new Quest("Search For The Questing Beast", "Q_Search_For_The_Questing_Beast.jpg", 4);
        Quest slayTheDragon = new Quest("Slay The Dragon", "Q_Slay_The_Dragon.jpg", 3, new Dragon());
        Quest searchForTheHolyGrail = new Quest("Search For The Holy Grail", "Q_Search_For_The_Holy_Grail.jpg", 5, allFoes);
        Quest vanquishKingArthursEnemies = new Quest("Vanquish King Arthur's Enemies", "Q_Vanquish_King_Arthurs_Enemies.jpg", 3);

        ArrayList<Quest> allQuests = new ArrayList<>();
        allQuests.add(repelTheSaxonRaiders);
        allQuests.add(rescueTheFairMaiden);
        allQuests.add(testOfTheGreenKnight);
        allQuests.add(searchForTheQuestingBeast);
        allQuests.add(slayTheDragon);
        allQuests.add(searchForTheHolyGrail);
        allQuests.add(vanquishKingArthursEnemies);

        //Add all adventure cards to deckOfStoryCards
        HashMap<AdventureCard, Integer> numberOfEachCard = new HashMap<>();
        numberOfEachCard.put(new BlackKnight(), 3);
        numberOfEachCard.put(new Boar(), 4);
        numberOfEachCard.put(new Dragon(), 1);
        numberOfEachCard.put(new EvilKnight(), 6);
        numberOfEachCard.put(new Giant(), 2);
        numberOfEachCard.put(new GreenKnight(), 2);
        numberOfEachCard.put(new Mordred(), 4);
        numberOfEachCard.put(new RobberKnight(), 7);
        numberOfEachCard.put(new SaxonKnight(), 8);
        numberOfEachCard.put(new Saxons(), 5);
        numberOfEachCard.put(new Thieves(), 8);
        for(AdventureCard adventureCard : numberOfEachCard.keySet()){
            for(int i = 0; i < numberOfEachCard.get(adventureCard); i++) {
                deckOfAdventureCards.add(adventureCard);
            }
        }
        //Add all story cards to deckOfStoryCards
        deckOfStoryCards.addAll(allQuests);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Stack<AdventureCard> getDeckOfAdventureCards() {
        return deckOfAdventureCards;
    }

    public int getCurrentTurnIndex() {
        return currentTurnIndex;
    }

    public void nextTurn(){
        if(players.size() == currentTurnIndex){
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
            for (int i = 0; i < NUM_CARDS; ++i) {
                if (!(deckOfAdventureCards.empty())) {
                    player.addCardToHand(deckOfAdventureCards.pop());
                }
            }
        }
    }

    void drawAdventureCards( Player playerOne, int x ){
        for (int i = 0; i < x; i++) {
            playerOne.addCardToHand(deckOfAdventureCards.pop());
        }

    }
}
