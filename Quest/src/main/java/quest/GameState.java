package quest;

public class GameState
{
    private Story currentStory;
    Player currentTurnPlayer;

    public GameState()
    {
    }

    public Story getCurrentStory() { return currentStory; }
    public Player getCurrentTurnPlayer() { return currentTurnPlayer; }

    public void setCurrentStory(Story pCurrentStory) { currentStory = pCurrentStory;}
    public void setCurrentTurnPlayer(Player pCurrentTurnPlayer) { currentTurnPlayer = pCurrentTurnPlayer;}
}
