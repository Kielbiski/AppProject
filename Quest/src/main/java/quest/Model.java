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
