package quest;

public class Foe extends Card {

    public Foe(String paramName, String paramImageFilename, int paramBattlePoints, int paramBonusPointsFromQuest, BonusCondition paramBonusCondition)
    {
        super(paramName, paramImageFilename);
        battlePoints = paramBattlePoints;
        bonusPointsFromQuest = paramBonusPointsFromQuest;
        effectCausingQuest = paramEffectCausingQuest;
    }
    private void applyQuestEffect(Quest currentQuest) {
        if (currentQuest.getName().equals(effectCausingQuest.getName())) {
            battlePoints += bonusPointsFromQuest;
        }
    }
    public int getBattlePoints(){
        return battlePoints;
    }

}
