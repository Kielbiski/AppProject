package quest;

public class Foe extends Card {

    private int battlePoints = 0;
    private int bonusPointsFromQuest= 0;
    private Quest effectCausingQuest = null;

    public Foe(int paramBattlePoints, int paramBonusPointsFromQuest, Quest paramEffectCausingQuest){
        battlePoints = paramBattlePoints;
        bonusPointsFromQuest = paramBonusPointsFromQuest;
        effectCausingQuest = paramEffectCausingQuest;
    }

    private int applyQuestEffect(){ //might need to implement checking here
        battlePoints += bonusPointsFromQuest;
    }

    public int getBattlePoints(){
        return battlePoints;
    }

}
