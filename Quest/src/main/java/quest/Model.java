package quest;

import java.util.ArrayList;

public class Model
{
    private ArrayList<Player> players = new ArrayList<>();
    private CardCollection<Card> deckOfCards = new CardCollection<>();
    private int currentTurnIndex = 0;
    private int NUM_CARDS = 12;

    public Model()
    {
        players.add(new Player("Random"));
        players.add(new Player("Jay"));
        players.add(new Player("Jeremy"));
        players.add(new Player("Robert"));
        //Initializing all variables

    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public CardCollection<Card> getDeckOfCards() {
        return deckOfCards;
    }

    public int getCurrentTurnIndex() {
        return currentTurnIndex;
    }

    public void startGame()
    {
        if(players.size() > 0 && deckOfCards.getSize() > 0)
        {
            shuffleAndDeal();
            //state.setCurrentTurnPlayer(players.get(currentTurnIndex));
        }
        else
        {
            endGame();
        }
    }

    private void endGame()
    {
        System.out.println("Game over");
    }

    public void nextTurn()
    {
        //state.setCurrentStory(null);
        if(players.size() == 0)
        {
            endGame();
            return;
        }
        if(++currentTurnIndex >= players.size())
        {
            currentTurnIndex = 0;
        }
        //state.setCurrentTurnPlayer(players.get(currentTurnIndex));
    }

    private void shuffleAndDeal()
    {
        deckOfCards.shuffle();
        for(Player player : players) {
            for (int i = 0; i < NUM_CARDS; i++) {
                if (deckOfCards.pop() instanceof AdventureCard) {
                    player.addCardToHand((AdventureCard) deckOfCards.pop());
                }
            }
        }
    }

}



