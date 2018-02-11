package quest;

public class EffectConditionNoEffect implements EffectCondition
{
    public boolean doesBonusApply(GameState state, Player owner)
    {
        return false;
    }
}
