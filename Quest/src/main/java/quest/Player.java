package quest;

import java.util.ArrayDeque;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;


enum Rank {SQUIRE, KNIGHT, CHAMPION_KNIGHT, KNIGHT_OF_THE_ROUND_TABLE;
    public Rank next()
    {
        if(ordinal() >= Rank.values().length)
        {
            return null;
        }
        else
        {
            return Rank.values()[ordinal() + 1];
        }
    }
}

public class Player {

    private String playerName;
    private int battlePoints;
    private int shields;
    private Rank playerRank;
    private ArrayList<AdventureCard> cardsOnTable = new ArrayList<>();
    private ArrayList<AdventureCard> cardsInHand = new ArrayList<>();

    Player(String paramName){
        playerName = paramName ;
        shields = 0;
        battlePoints =0;
        playerRank = Rank.SQUIRE;
    }

    public ArrayList<AdventureCard> getCardsOnTable() {
        return cardsOnTable;
    }

    public ArrayList<AdventureCard> getCardsInHand() {
        return cardsInHand;
    }

    public String getPlayerName(){
        return playerName;
    }
    
    public int getShields(){
        return shields;
    }
    
    public Rank getPlayerRank(){
        return playerRank;
    }
    
    public int getNumCardsInHand(){
        return cardsInHand.size();
    }

    public void addBonusPoint (int paramBonusPoint){ 
        battlePoints += paramBonusPoint;
    }
    
    public void resetBattlePoints (){ 
        battlePoints = 0;
    }
    
    public void setShields(int paramShields){
        shields = paramShields;
    }
    
    public void setPlayerRank(Rank paramRank){
        playerRank = paramRank;
    }

    public void addCardToHand(AdventureCard paramCard){
        cardsInHand.add(paramCard);
    }
    
    public void addCardToTable(AdventureCard paramCard){
        cardsOnTable.add(paramCard);
    }
    
//    public void addCardToPlaying(AdventureCard paramCard){
//        cardsPlaying.addCard(paramCard);
//    }

    public void removeCardFromHand(AdventureCard paramCard){
        cardsInHand.remove(paramCard);
    }
    
    public void removeCardFromTable(AdventureCard paramCard){
        cardsOnTable.remove(paramCard);
    }
    
//    public void removeCardFromPlaying(AdventureCard paramCard){
//        cardsPlaying.removeCard(paramCard);
//    }

    public boolean tooManyCards(){
        return cardsInHand.size() > 12;
    }

    private int calculateCardsBP(ArrayList<AdventureCard> paramCardList) {
        int iterateArray =0;
        int point = 0;
        while (iterateArray < paramCardList.size()) {
            point += paramCardList.get(iterateArray).getBattlePoints();
            iterateArray++;
        }
        return point;
    }

    public int calculateBattlePoints() {
        switch (playerRank){
            case SQUIRE:
                battlePoints += 5;
                break;
            case KNIGHT:
                battlePoints += 10;
                break;
            case CHAMPION_KNIGHT:
                battlePoints += 20;
                break;
            default:
                break;
        }
//        battlePoints+=calculateCardCollectionPoint(this.cardsPlaying);
        battlePoints+=calculateCardsBP(this.cardsOnTable);
        return battlePoints;
    }

    private int getRequiredShieldsForNextRank() {
        switch(playerRank){
            case SQUIRE:
                return 5;
            case KNIGHT:
                return 7;
            case CHAMPION_KNIGHT:
                return 10;
            default:
                return 99;
        }
    }

    public void confirmRank() {
        int requiredShields = this.getRequiredShieldsForNextRank();
        if(playerRank != Rank.KNIGHT_OF_THE_ROUND_TABLE && shields >= requiredShields){
            playerRank = playerRank.next();
            shields -= requiredShields;
        }
    }
}
