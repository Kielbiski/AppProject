package quest;

public class Card {
    private String name;
    private String imageFilename;

    protected int battlePoints;
    protected int bids;

    protected int bonusBattlePoints;
    protected int bonusBids;

    Card(String paramName, String paramImageFilename){
        name = paramName;
        imageFilename = paramImageFilename;
        battlePoints = bids = bonusBattlePoints = bonusBids = 0;
    }
    public String getName(){
        return name;
    }
    public String getImageFilename(){
        return imageFilename;
    }
    public int getBattlePoints(){
        return battlePoints;
    }
    public int getBids(){
        return bids;
    }
    public int getBonusBattlePoints() {return bonusBattlePoints;}
    public int getBonusBids() {return bonusBids;}

    public int getBattlePointsInGame(GameState state){ return battlePoints; }
    public int getBidsInGame(GameState state) { return bids; }
}
