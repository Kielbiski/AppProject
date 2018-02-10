package quest;

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
    //private int currentPlayPoints;
    private int battlePoints;
    private int shields;
    private Rank playerRank;
    CardCollection cardsInHand = new CardCollection();
    CardCollection cardsOnTable = new CardCollection();
    CardCollection cardsPlaying = new CardCollection();

    public Player(String paramName){
        playerName = paramName ;
        //currentPlayPoints = 0;
        battlePoints = 0;
        shields = 0;
        playerRank = Rank.SQUIRE;
    }

    public String getPlayerName(){
        return playerName;
    }
    //public int getCurrentPlayPoints(){return currentPlayPoints;}
    public int getShields(){
        return shields;
    }
    public Rank getPlayerRank(){
        return playerRank;
    }
    public int getNumCardsInHand(){
        return cardsInHand.getSize();
    }
    public int getBattlePoints() {return battlePoints;}

    //public void setCurrentPlayPoints(int paramPlayPoint){ currentPlayPoints = paramPlayPoint;}
    public void setShields(int paramShields){
        shields = paramShields;
    }
    public void setPlayerRank(Rank paramRank){
        playerRank = paramRank;
    }

    public void addCardToHand(Card paramCard){
        cardsInHand.addCard(paramCard);
    }

    public void addCardToTable(Card paramCard){
        cardsOnTable.addCard(paramCard);
    }

    public void addCardToPlaying(Card paramCard){
        cardsPlaying.addCard(paramCard);
    }

    public void selectCard(Card card)
    {
        addCardToPlaying(card);
    }

    //Draw cards from a card collection and add them to the player's hand
    public void drawCards(int numCards, CardCollection collection)
    {
        collection.drawCards(numCards).moveAllCardsToCollection(cardsInHand);
    }

    public void playCards()
    {
        cardsPlaying.moveAllCardsToCollection(cardsOnTable);
    }

    public void discard(Card paramCard, CardCollection discardPile){
        cardsInHand.moveCardToCollection(paramCard, discardPile);
    }

    //public void removeCardFromTable(int paramIndexPos){cardsOnTable.remove(paramIndexPos);}
    //public void removeCardPlaying(int paramIndexPos){cardsPlaying.remove(paramIndexPos);}

    public boolean tooManyCards()
    {
        return cardsInHand.getSize() > 12;
    }

    public boolean hasWon()
    {
        return playerRank == Rank.KNIGHT_OF_THE_ROUND_TABLE;
    }

    public int calculateBattlePoints(GameState state)
    {
        //Calculate and return the player's total battle points
        battlePoints = 0;
        switch (playerRank)
        {
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

        for(int i = 0; i < cardsOnTable.getSize(); ++i)
        {
            if(!cardsOnTable.getCard(i).isFaceDown())
            {
                battlePoints += cardsOnTable.getCard(i).getBattlePointsInGame(state);
            }
        }

        return battlePoints;
    }

    private int getRequiredShieldsForNextRank()
    {
        switch(playerRank)
        {
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

    public void changeRank()
    {
        int requiredShields = getRequiredShieldsForNextRank();
        if(playerRank != Rank.KNIGHT_OF_THE_ROUND_TABLE && shields >= requiredShields)
        {
            playerRank = playerRank.next();
            shields -= requiredShields;
        }
    }
}
