package quest;

public class AdventureCard extends Card
{
    int battlePoints;
    int bids;
    int bonusBattlePoints;
    int bonusBids;

    AdventureCard(String name, String imageFilename)
    {
        super(name, imageFilename);
        battlePoints = bids = bonusBattlePoints = bonusBids = 0;
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
