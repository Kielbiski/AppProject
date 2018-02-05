package quest;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Player {
    private String playerName;
    private int currentPlayPoints;
    private int shields;
    private String playerRank;
    private int numCardHand ;
    public ArrayList <Card> cardOnHands = new ArrayList<Card>();
    public ArrayList <Card> cardOnTable = new ArrayList<Card>();
    public ArrayList <Card> cardPlaying = new ArrayList<Card>();

    public Player(String paramName){
        playerName = paramName ;
        currentPlayPoints = 0;
        shields = 0;
        playerRank ="Squire";
        numCardHand = 0;
    }

    public String getPlayerName(){
        return playerName;
    }

    public int getCurrentPlayPoints(){
        return currentPlayPoints;
    }

    public int getShields(){
        return shields;
    }

    public String getPlayerRank(){
        return playerRank;
    }

    public int getNumCardHand(){
        return numCardHand;
    }

    public void setCurrentPlayPoints(int paramPlayPoint){
        currentPlayPoints = paramPlayPoint;
    }

    public void setShields(int paramShields){
        shields =paramShields;
    }

    public void setPlayerRank(String paramRank){
        playerRank=paramRank;
    }

    public void addCardOnHands(Card paramCar){
        cardOnHands.add(paramCar);
        numCardHand++;
    }

    public void addCardOnTable(Card paramCar){
        cardOnTable.add(paramCar);
    }

    public void addCardPlaying(Card paramCar){
        cardPlaying.add(paramCar);
    }

    public void removeCardOnHands(int parmIndexPos){
        cardOnHands.remove(parmIndexPos);
        numCardHand--;
    }

    public void removeCardOnTable(int parmIndexPos){
        cardOnTable.remove(parmIndexPos);
    }

    public void removeCardPlaying(int parmIndexPos){
        cardPlaying.remove(parmIndexPos);
    }

    public boolean tooManyCard(){
        if (numCardHand > 12) {
            return true;
        }
        else{
            return false;
        }
    }

}
