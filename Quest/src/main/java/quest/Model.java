package quest;

import java.util.ArrayList;

public class Model
{
    private ArrayList<Player> players = new ArrayList<>();
    private CardCollection<AdventureCard> deckOfAdventureCards = new CardCollection<>();
    private int currentTurnIndex = 0;
    private int NUM_CARDS = 12;

    Model() {
        players.add(new Player("Robert"));
        players.add(new Player("Bob"));
        players.add(new Player("Bobbo"));
        players.add(new Player("Bobert"));
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

    public Player getPlayerWithHighestRank() {
        int highestShieldCount = 0;
        Player playerWithHighestRank = players.get(0);
        for(Player player : players){
            if (player.getShields() > highestShieldCount) {
                highestShieldCount = player.getShields();
                playerWithHighestRank = player;
            }
        }
        return playerWithHighestRank;
    }

    public void nextTurn(){
        if(players.size() == currentTurnIndex){
            currentTurnIndex = 0 ;
        }
        else{
            currentTurnIndex++;
        }
    }

    public void shuffleAndDeal(){
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
