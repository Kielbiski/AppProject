package quest;

public class AdventureCard extends Card
{
    protected int battlePoints;
    protected int bids;
    protected int bonusBattlePoints;
    protected int bonusBids;

    AdventureCard(String name, String imageFilename){
        super(name, imageFilename);
        battlePoints = 0;
        bids = 0;
        bonusBattlePoints = 0; 
        bonusBids = 0;
    }

    public int getBattlePoints(){
        return battlePoints;
    }
    
    public int getBids(){
        return bids;
    }
    
    public int getBonusBattlePoints() {
        return bonusBattlePoints;
    }
    
    public int getBonusBids(){
        return bonusBids;
    }

}
