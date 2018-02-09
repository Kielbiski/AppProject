package quest;

import java.util.ArrayList;

public class GameModel
{
    ArrayList<Player> players = new ArrayList<>();
    Player currentTurnPlayer;
    int currentTurnIndex = 0;

    Deck adventureDeck = new Deck();
    Deck storyDeck = new Deck();
    private Card currentStory;

    public GameModel()
    {
        players.add(new Player("Cody"));
        players.add(new Player("Jay"));
        players.add(new Player("Jeremy"));
        players.add(new Player("Robert"));

        adventureDeck.fillWithAdventureCards();
        storyDeck.fillWithStoryCards();
    }

    public void startGame()
    {
        if(players.size() > 0)
        {
            shuffleAndDeal();
            currentTurnPlayer = players.get(currentTurnIndex);
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
        currentStory = null;
        if(players.size() == 0)
        {
            endGame();
            return;
        }
        if(++currentTurnIndex >= players.size())
        {
            currentTurnIndex = 0;
        }
        currentTurnPlayer = players.get(currentTurnIndex);
        startTurn();
    }

    public void shuffleAndDeal()
    {
        adventureDeck.shuffle();
        storyDeck.shuffle();
        for(int i = 0; i < players.size(); ++i)
        {
            players.get(i).addCardsToHand(adventureDeck.drawCards(12));
        }
    }

    public void startTurn()
    {
        //Notify view that a new turn has started
    }

    public void ready()
    {
        drawStoryCard();
    }

    public void selectCard(Card card)
    {
        currentTurnPlayer.addCardToPlaying(card);
    }

    public void drawStoryCard()
    {
        currentStory = storyDeck.drawCard();
    }

    public Card getCurrentStory() {return currentStory;}
    public Player getCurrentTurnPlayer() {return currentTurnPlayer;}
}
