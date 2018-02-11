package quest;

public class AdventureCard extends Card
{
    protected int battlePoints;
    protected int bids;
    protected int bonusBattlePoints;
    protected int bonusBids;

    protected EffectCondition bonusBattlePointsCondition, bonusBidsCondition;

    protected CardEffect onCardPlayed;
    //    protected CardEffect onCardPlayedSpecial;
//    protected CardEffect oncePerTurn;

    public AdventureCard(String name, String imageFilename)
    {
        super(name, imageFilename);
        battlePoints = bids = bonusBattlePoints = bonusBids = 0;
        bonusBattlePointsCondition = new EffectConditionNoEffect();
        bonusBidsCondition = new EffectConditionNoEffect();
    }

    public int getBattlePoints(){
        return battlePoints;
    }
    public int getBids(){
        return bids;
    }
    public int getBonusBattlePoints() {return bonusBattlePoints;}
    public int getBonusBids() {return bonusBids;}

    public int getBattlePointsInGame(GameState state, Player owner)
    {
        if(bonusBattlePointsCondition.doesBonusApply(state, owner))
        {
            return bonusBattlePoints;
        }
        else
        {
            return battlePoints;
        }
    }

    public int getBidsInGame(GameState state, Player owner)
    {
        if(bonusBidsCondition.doesBonusApply(state, owner))
        {
            return bonusBids;
        }
        else
        {
            return bids;
        }
    }
}
