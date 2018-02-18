package quest;

public class GameState
{
    private Player currentTurnPlayer;
    private int bonusQuestShields;

    public GameState()
    {
        bonusQuestShields = 0;
    }

    public Player getCurrentTurnPlayer() { return currentTurnPlayer; }
    public int getBonusQuestShields() {return bonusQuestShields; }

    public void setCurrentTurnPlayer(Player pCurrentTurnPlayer) { currentTurnPlayer = pCurrentTurnPlayer;}
    public void setBonusQuestShields(int shields) { bonusQuestShields = shields; }
}
