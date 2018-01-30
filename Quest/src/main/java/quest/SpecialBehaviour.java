package quest;

public class SpecialBehaviour {
    private int bonusBattlePoints;
    private int bonusBids;
    SpecialBehaviour(int paramBonusBattlePoints, int paramBonusBids){
        bonusBattlePoints = paramBonusBattlePoints;
        bonusBids = paramBonusBids;
    }

    public int getBonusBattlePoints() {
        return bonusBattlePoints;
    }

    public int getBonusBids() {
        return bonusBids;
    }
}
