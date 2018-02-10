package quest;

public class BonusConditionNoBonus implements BonusCondition
{
    public boolean doesBonusApply(GameState state)
    {
        return false;
    }
}
