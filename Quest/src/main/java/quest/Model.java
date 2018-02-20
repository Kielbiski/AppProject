package quest;

import java.util.ArrayList;

public class Model
{
    private ArrayList<Player> players ;
    private CardCollection<AdventureCard> deckOfAdventureCard;
    private int currentTurnIndex ;
    private int NUM_CARDS ;

    public Model() {
        players = new ArrayList<>();
        deckOfAdventureCard = new CardCollection<>();
        currentTurnIndex = 0;
        NUM_CARDS = 12;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public CardCollection<Card> getDeckOfCards() {
        return deckOfAdventureCard;
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
        deckOfAdventureCard.shuffle();
        for(Player player : players) {
            for (int i = 0; i < NUM_CARDS; i++) {
                player.addCardToHand((AdventureCard)deckOfAdventureCard.pop());
            }
        }
    }

    private void drawAdventureCard( Player playerOne, int x ){ 
        for (int i = 0; i < x; i++) {
            playerOne.addCardToHand((AdventureCard)deckOfAdventureCard.pop()); 
        }

    }

}
