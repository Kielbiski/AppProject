package quest;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Model
{
    private ArrayList<Player> players = new ArrayList<>();
    private CardCollection<AdventureCard> deckOfAdventureCards= new CardCollection<>();
    private int currentTurnIndex = 0;
    private int NUM_CARDS = 12;

    Model() {
//
//        players.add(new Player("rob"));
//        players.add(new Player("n"));
//        players.add(new Player("q"));
//        players.add(new Player("w"));


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
        Foe saxonKnight = new Foe("Saxon Knight", "F_Saxon_Knight.jpg",5, 10);
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
        Quest boarhunt = new Quest("Boar Hunt", "Q_Boar_HUnt.jpg",2,boar);
        Quest defendTheQueensHonor = new Quest("Defend The Queen's Honor", "Q_Defend_The_Queens_Honor.jpg",4,allFoes);


    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void addPlayer(String name) {
        players.add(new Player(name));
    }

    public CardCollection<AdventureCard> getDeckOfCards() {
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

    private void shuffleAndDeal(){
        deckOfAdventureCards.shuffle();
        for(Player player : players) {
            for (int i = 0; i < NUM_CARDS; i++) {
                player.addCardToHand((AdventureCard)deckOfAdventureCards.pop());
            }
        }
    }

    private void drawAdventureCard( Player playerOne, int x ){
        for (int i = 0; i < x; i++) {
            playerOne.addCardToHand((AdventureCard)deckOfAdventureCards.pop());
        }

    }
}
