package quest;

public class BonusConditionCurrentQuest {
    private String questName = "";

    public BonusConditionCurrentQuest(String paramQuestName)
    {
        questName = paramQuestName;
    }

    public boolean getsBonus()
    {
        return false; //Should check game state to see if a quest is happening, and if its name matches questName
    }
}
