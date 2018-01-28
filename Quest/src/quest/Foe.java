package quest;

public class Foe extends Card {

    private int battlePoints = 0;
    private int bonusPointsFromQuest = 0;
    private Quest effectCausingQuest = null;

    public Foe(String paramName, String paramImageFilename, int paramBattlePoints, int paramBonusPointsFromQuest, Quest paramEffectCausingQuest)
    {
        super(paramName, paramImageFilename);
        battlePoints = paramBattlePoints;
        bonusPointsFromQuest = paramBonusPointsFromQuest;
        effectCausingQuest = paramEffectCausingQuest;
    }

    private int applyQuestEffect(Quest currentQuest){
        if(currentQuest.name == effectCausingQuest.name) {
            battlePoints += bonusPointsFromQuest;
        }
    }


    public int getBattlePoints(){
        return battlePoints;
    }

}
