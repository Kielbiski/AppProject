package quest;

public class Foe extends Card {
    BonusCondition bonusCondition;

    public Foe(String paramName, String paramImageFilename, int paramBattlePoints, int paramBonusPointsFromQuest, BonusCondition paramBonusCondition)
    {
        super(paramName, paramImageFilename);
        battlePoints = paramBattlePoints;
        bonusBattlePoints = paramBonusPointsFromQuest;
        bonusCondition = paramBonusCondition;
    }

    @Override
    public int getBattlePointsInGame(GameState state)
    {
        if(bonusCondition.doesBonusApply(state))
        {
            return bonusBattlePoints;
        }
        else
        {
            return battlePoints;
        }
    }

}
