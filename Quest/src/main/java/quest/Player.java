package quest;

import java.util.ArrayDeque;
import java.util.List;
import java.util.ArrayList;


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
    private CardCollection<AdventureCard> cardsInHand = new CardCollection<>();
    private CardCollection<AdventureCard> cardsOnTable = new CardCollection<>();
    private CardCollection<AdventureCard> cardsPlaying = new CardCollection<>();

    Player(String paramName){
        playerName = paramName ;
        shields = 0;
        battlePoints =0;
        playerRank = Rank.SQUIRE;
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
        return cardsInHand.getSize();
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
        cardsInHand.addCard(paramCard);
    }
    
    public void addCardToTable(AdventureCard paramCard){
        cardsOnTable.addCard(paramCard);
    }
    
    public void addCardToPlaying(AdventureCard paramCard){
        cardsPlaying.addCard(paramCard);
    }

    public void removeCardFromHand(AdventureCard paramCard){
        cardsInHand.removeCard(paramCard);
    }
    
    public void removeCardFromTable(AdventureCard paramCard){
        cardsOnTable.removeCard(paramCard);
    }
    
    public void removeCardFromPlaying(AdventureCard paramCard){
        cardsPlaying.removeCard(paramCard);
    }

    public boolean tooManyCards(){
        return cardsInHand.getSize() > 12;
    }

    private int calculateCardCollectionPoint(CardCollection<AdventureCard> paramCardList) {
        int iterateArray =0;
        int point = 0;
        while (iterateArray < paramCardList.getSize()) {
            point += paramCardList.getCard(iterateArray).getBattlePoints();
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
        battlePoints+=calculateCardCollectionPoint(this.cardsPlaying);
        battlePoints+=calculateCardCollectionPoint(this.cardsOnTable);
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
