package quest;

public class GameState
{
    private Card currentStory;
    Player currentTurnPlayer;

    public GameState()
    {
    }

    public Card getCurrentStory() { return currentStory; }
    public Player getCurrentTurnPlayer() { return currentTurnPlayer; }

    public void setCurrentStory(Card pCurrentStory) { currentStory = pCurrentStory;}
    public void setCurrentTurnPlayer(Player pCurrentTurnPlayer) { currentTurnPlayer = pCurrentTurnPlayer;}
}
