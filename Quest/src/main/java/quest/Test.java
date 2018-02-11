package quest;

public class Test extends AdventureCard
{
    public Test(String name, String imageFilename, int pBids, EffectCondition pBonusBidsCondition)
    {
        super(name, imageFilename);
        bids = pBids;
        bonusBidsCondition = pBonusBidsCondition;
    }

    public Test(String name, String imageFilename, EffectCondition pBonusCondition)
    {
        this(name, imageFilename, 3, pBonusCondition);
    }
}
