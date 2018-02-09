package quest;

import java.util.ArrayList;

public class GameModel
{
    ArrayList<Player> players = new ArrayList<>();
    Player currentTurnPlayer;
    int currentTurnIndex = 0;
    private Card currentStory;

    public GameModel()
    {
        players.add(new Player("Player 1"));
        players.add(new Player("Player 2"));
    }

    public void startGame()
    {
        if(players.size() > 0)
        {
            currentTurnPlayer = players.get(currentTurnIndex);
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
    }

    public Card getCurrentStory() {return currentStory;}
    public Player getCurrentTurnPlayer() {return currentTurnPlayer;}
}
