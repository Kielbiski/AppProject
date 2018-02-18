package quest;

public class Foe extends AdventureCard {
    public Foe(String paramName, String paramImageFilename, int paramBattlePoints, int paramBonusPointsFromQuest)
    {
        super(paramName, paramImageFilename);
        battlePoints = paramBattlePoints;
        bonusBattlePoints = paramBonusPointsFromQuest;
    }
}
