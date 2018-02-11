package quest;

public class EffectConditionCurrentQuest
{
    private String questName = "";

    public EffectConditionCurrentQuest(String paramQuestName)
    {
        questName = paramQuestName;
    }

    public boolean doesBonusApply(GameState state)
    {
        return state.getCurrentStory() instanceof Quest && state.getCurrentStory().getName().equals(questName);
    }
}
