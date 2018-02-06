package quest;

public class Ally extends Card {
    private int battlePoints;
    private int bids;

    private int bonusBattlePoints;
    private int bonusBids;

    private BonusCondition bonusCondition;
    private SpecialBehaviour special;

    public Ally(String paramName, String paramImageFilename, int paramBattlePoints, int paramBids, int paramBonusBattlePoints, int paramBonusBids){
        super(paramName, paramImageFilename);
        battlePoints = paramBattlePoints;
    }
    protected void setSpecialBehaviour(SpecialBehaviour paramSpecialBehaviour){
        special = paramSpecialBehaviour;
    }
    protected void applySpecialBehaviour(){

    }
    protected void setBattlePoints(int paramBattlePoints){
        battlePoints = paramBattlePoints;
    }
    protected void setBids(int paramBids){
        bids = paramBids;
    }
    public int getBattlePoints(){
        return battlePoints;
    }
    public int getBids(){
        return bids;
    }
    public int getBonusBattlePoints() {return bonusBattlePoints;}
    public int getBonusBids() {return bonusBids;}
}
