package quest;

public interface EffectCondition
{
    boolean doesBonusApply(GameState state, Player owner);
}
