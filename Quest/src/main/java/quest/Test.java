package quest;

public class Test extends Card
{
    BonusCondition bonusCondition;
    public Test(String name, String imageFilename, int pBids, BonusCondition pBonusCondition)
    {
        super(name, imageFilename);
        bids = pBids;
        bonusCondition = pBonusCondition;
    }

    public Test(String name, String imageFilename, BonusCondition pBonusCondition)
    {
        this(name, imageFilename, 3, pBonusCondition);
    }

    @Override
    public int getBidsInGame(GameState state)
    {
        if(bonusCondition.doesBonusApply(state))
        {
            return bonusBids;
        }
        else
        {
            return bids;
        }
    }
}
