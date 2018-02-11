package quest;

public class EffectConditionCardOnTable implements EffectCondition
{
    private String cardName = "";

    public EffectConditionCardOnTable(String paramCardName)
    {
        cardName = paramCardName;
    }

    public boolean doesBonusApply(GameState state, Player owner)
    {
        return false; //Should check if player who owns this card has a card with cardName on the table
    }
}
