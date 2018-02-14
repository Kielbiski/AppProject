package quest;

import java.util.ArrayList;

public class GameModel
{
    ArrayList<Player> players = new ArrayList<>();
    int currentTurnIndex = 0;

    CardCollection<AdventureCard> adventureDeck = new CardCollection();
    CardCollection<StoryCard> storyDeck = new CardCollection();
    CardCollection<AdventureCard> adventureDiscardPile = new CardCollection();
    CardCollection<StoryCard> storyDiscardPile = new CardCollection();

    public GameModel()
    {
        players.add(new Player("Cody"));
        players.add(new Player("Jay"));
        players.add(new Player("Jeremy"));
        players.add(new Player("Robert"));
    }

    public void startGame()
    {
        if(players.size() > 0 && adventureDeck.getSize() > 0 && storyDeck.getSize() > 0)
        {
            shuffleAndDeal();
            //setCurrentTurnPlayer(players.get(currentTurnIndex));
            startTurn();
        }
        else
        {
            endGame();
        }
    }

    public void endGame()
    {
        System.out.println("Game over");
    }

    public void nextTurn()
    {
        state.setCurrentStory(null);
        if(players.size() == 0)
        {
            endGame();
            return;
        }
        if(++currentTurnIndex >= players.size())
        {
            currentTurnIndex = 0;
        }
        state.setCurrentTurnPlayer(players.get(currentTurnIndex));
        startTurn();
    }

    public void shuffleAndDeal()
    {
        adventureDeck.shuffle();
        storyDeck.shuffle();
        for(int i = 0; i < players.size(); ++i)
        {
            players.get(i).drawCards(12, adventureDeck);
        }
    }

    public void startTurn()
    {
        drawStoryCard();
        //Notify view that a new turn has started
    }

    public void ready()
    {
        drawStoryCard();
    }

    public void selectCard(AdventureCard card)
    {
        state.getCurrentTurnPlayer().selectCard(card);
    }

    public void drawStoryCard()
    {
        state.setCurrentStory(storyDeck.drawCard());
        if(state.getCurrentStory() != null)
        {
//            state.getCurrentStory().onCardPlayed.execute(state);
        }
        else
        {
            //TODO: when a deck runs out, add the discard pile back into the deck and reshuffle
            endGame();
        }
    }
    
    while (true) {
        System.out.println("Game loop.");
        break;
    }

}
