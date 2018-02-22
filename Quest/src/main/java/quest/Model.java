package quest;

import java.util.ArrayList;
import java.util.Collections;
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


        //Foes
        Foe blackKnight = new Foe("Black Knight", "F_Black_Knight.jpg", 25, 10);
        Foe boar = new Foe("Boar", "F_Boar.jpg", 5, 15);
        Foe dragon = new Foe("Dragon", "F_Dragon.jpg", 50, 20);
        Foe evilKnight = new Foe("Evil Knight", "F_Evil_Knight.jpg", 20, 10);
        Foe giant = new Foe("Giant", "F_Giant.jpg", 40, 0);
        Foe greenKnight = new Foe("Green Knight", "F_Green_Knight.jpg", 25, 15);
        Foe mordred = new Foe("Mordred", "F_Mordred.jpg", 30, 0);
        Foe robberKnight = new Foe("Robber Knight", "F_Robber_Knight.jpg", 15, 0);
        Foe saxonKnight = new Foe("Saxon Knight", "F_Saxon_Knight.jpg", 5, 10);
        Foe saxons = new Foe("Saxons", "F_Saxons.jpg", 10, 10);
        Foe thieves = new Foe("Thieves", "F_Thieves.jpg", 5, 0);

        ArrayList<Foe> allFoes = new ArrayList<>();
        allFoes.add(blackKnight);
        allFoes.add(boar);
        allFoes.add(dragon);
        allFoes.add(evilKnight);
        allFoes.add(greenKnight);
        allFoes.add(mordred);
        allFoes.add(robberKnight);
        allFoes.add(saxonKnight);
        allFoes.add(saxons);
        allFoes.add(thieves);

        //Weapons
        Weapon battleax = new Weapon("Battle-ax", "W_Battle-ax.jpg", 15);
        Weapon dagger = new Weapon("Dagger", "W_Dagger.jpg", 5);
        Weapon excalibur = new Weapon("Excalibur", "W_Excalibur.jpg", 30);
        Weapon horse = new Weapon("Horse", "W_Horse.jpg", 10);
        Weapon lance = new Weapon("Lance", "W_Lance.jpg", 20);
        Weapon sword = new Weapon("Sword", "W_Sword.jpg", 10);

        ArrayList<Weapon> allWeapons = new ArrayList<>();
        allWeapons.add(battleax);
        allWeapons.add(dagger);
        allWeapons.add(excalibur);
        allWeapons.add(horse);
        allWeapons.add(lance);
        allWeapons.add(sword);

        //Quest
        Quest boarhunt = new Quest("Boar Hunt", "Q_Boar_Hunt.jpg", 2, boar);
        Quest defendTheQueensHonor = new Quest("Defend The Queen's Honor", "Q_Defend_The_Queens_Honor.jpg", 4, allFoes);
        Quest journeyThroughTheEnchantedForest = new Quest("Journey Through The Enchanted Forest", "Q_Journey_Through_The_Enchanted_Forest.jpg", 3, evilKnight);
        ArrayList<Foe> saxonFoes = new ArrayList<>();
        saxonFoes.add(saxonKnight);
        saxonFoes.add(saxons);
        Quest repelTheSaxonRaiders = new Quest("Repel The Saxon Raiders", "Q_Repel_The_Saxon_Raiders.jpg", 2, saxonFoes);
        Quest rescueTheFairMaiden = new Quest("Rescue The Fair Maiden", "Q_Rescue_The_Fair_Maiden.jpg", 3, blackKnight);
        Quest testOfTheGreenKnight = new Quest("Test Of The Green Knight", "Q_Test_Of_The_Green_Knight.jpg", 4, greenKnight);
        Quest searchForTheQuestingBeast = new Quest("Search For The Questing Beast", "Q_Search_For_The_Questing_Beast.jpg", 4);
        Quest slayTheDragon = new Quest("Slay The Dragon", "Q_Slay_The_Dragon.jpg", 3, dragon);
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
        deckOfAdventureCards.addAll(allWeapons);
        deckOfAdventureCards.addAll(allFoes);
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
            for (int i = 0; i < NUM_CARDS; i++) {
                player.addCardToHand(deckOfAdventureCards.pop());
            }
        }
    }

    void drawAdventureCards( Player playerOne, int x ){
        for (int i = 0; i < x; i++) {
            playerOne.addCardToHand(deckOfAdventureCards.pop());
        }

    }
}
