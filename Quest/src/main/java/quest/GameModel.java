package quest;

import java.util.ArrayList;

public class GameModel
{
    private ArrayList<Player> players = new ArrayList<>();
    private CardCollection<Card> deckOfCards = new CardCollection<>();
    private int currentTurnIndex = 0;


    public GameModel()
    {
        players.add(new Player("Random"));
        players.add(new Player("Jay"));
        players.add(new Player("Jeremy"));
        players.add(new Player("Robert"));

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
        for(Player player : players)
        {
            player.drawCards(12, deckOfCards);
        }
    }

    public void selectCard(AdventureCard card)
    {
        //state.getCurrentTurnPlayer().selectCard(card);
    }

}
