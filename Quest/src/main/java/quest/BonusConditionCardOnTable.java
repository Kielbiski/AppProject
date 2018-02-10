package quest;

public class BonusConditionCardOnTable implements BonusCondition {
    private String cardName = "";

    public BonusConditionCardOnTable(String paramCardName)
    {
        cardName = paramCardName;
    }

    public boolean doesBonusApply(GameState state)
    {
        return false; //Should check if player who owns this card has a card with cardName on the table
    }
}
