package quest;

public class Ally extends AdventureCard {
    public Ally(String paramName, String paramImageFilename, int paramBattlePoints, int paramBids, int paramBonusBattlePoints, int paramBonusBids,
                EffectCondition pBonusBattlePointsCondition, EffectCondition pBonusBidsCondition){
        super(paramName, paramImageFilename);
        battlePoints = paramBattlePoints;
        bids = paramBids;
        bonusBattlePoints = paramBonusBattlePoints;
        bonusBids = paramBonusBids;
        bonusBattlePointsCondition = pBonusBattlePointsCondition;
        bonusBidsCondition = pBonusBidsCondition;
    }
}
