package quest;

public class Ally extends Card {

    private BonusCondition bonusBattlePointsCondition, bonusBidsCondition;

    public Ally(String paramName, String paramImageFilename, int paramBattlePoints, int paramBids, int paramBonusBattlePoints, int paramBonusBids,
                BonusCondition pBonusBattlePointsCondition, BonusCondition pBonusBidsCondition){
        super(paramName, paramImageFilename);
        battlePoints = paramBattlePoints;
        bids = paramBids;
        bonusBattlePoints = paramBonusBattlePoints;
        bonusBids = paramBonusBids;
        bonusBattlePointsCondition = pBonusBattlePointsCondition;
    }

    @Override
    public int getBattlePointsInGame(GameState state)
    {
        if(bonusBattlePointsCondition.doesBonusApply(state))
        {
            return bonusBattlePoints;
        }
        else
        {
            return battlePoints;
        }
    }

    @Override
    public int getBidsInGame(GameState state)
    {
        if(bonusBidsCondition.doesBonusApply(state))
        {
            return bonusBids;
        }
        else
        {
            return bids;
        }
    }
}
