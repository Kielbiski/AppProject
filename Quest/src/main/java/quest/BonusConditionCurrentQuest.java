package quest;

public class BonusConditionCurrentQuest {
    private String questName = "";

    public BonusConditionCurrentQuest(String paramQuestName)
    {
        questName = paramQuestName;
    }

    public boolean doesBonusApply(GameState state)
    {
        return state.getCurrentStory() == null;
    }
}
