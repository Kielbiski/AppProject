package quest;

import java.util.ArrayList;

public class Model
{
    private ArrayList<Player> players ;
    private CardCollection<AdventureCard> deckOfAdventureCards;
    private int currentTurnIndex;
    private int NUM_CARDS ;

    Model() {
        players = new ArrayList<>();
        deckOfAdventureCards = new CardCollection<>();
        currentTurnIndex = 0;
        NUM_CARDS = 12;
        //Initializing all cards

        //
    }

    public ArrayList<Player> getPlayers() {
        return players;
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
