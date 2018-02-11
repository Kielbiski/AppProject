package quest;

public class GameState
{
    private StoryCard currentStory;
    private Player currentTurnPlayer;
    private int bonusQuestShields;

    public GameState()
    {
        bonusQuestShields = 0;
    }

    public StoryCard getCurrentStory() { return currentStory; }
    public Player getCurrentTurnPlayer() { return currentTurnPlayer; }
    public int getBonusQuestShields() {return bonusQuestShields;}

    public void setCurrentStory(StoryCard pCurrentStory) { currentStory = pCurrentStory;}
    public void setCurrentTurnPlayer(Player pCurrentTurnPlayer) { currentTurnPlayer = pCurrentTurnPlayer;}
    public void setBonusQuestShields(int shields) { bonusQuestShields = shields; }
}
