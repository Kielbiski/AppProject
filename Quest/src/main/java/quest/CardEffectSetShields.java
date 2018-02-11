package quest;

public class CardEffectSetShields implements CardEffect
{
    public void execute(GameState state)
    {
        int shields = Math.max(0, state.getCurrentTurnPlayer().getShields() - 2);
        state.getCurrentTurnPlayer().setShields(shields);
    }
}
