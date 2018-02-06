package quest;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


enum Rank {SQUIRE, KNIGHT, CHAMPION_KNIGHT, KNIGHT_OF_THE_ROUND_TABLE};

public class Player {


    private String playerName;
    private int currentPlayPoints;
    private int shields;
    private Rank playerRank;
    private int numCardHand ;
    private ArrayList <Card> cardOnHands = new ArrayList<Card>();
    private ArrayList <Card> cardOnTable = new ArrayList<Card>();
    private ArrayList <Card> cardPlaying = new ArrayList<Card>();

    public Player(String paramName){
        playerName = paramName ;
        currentPlayPoints = 0;
        shields = 0;
        playerRank = Rank.SQUIRE;
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

    public Rank getPlayerRank(){
        return playerRank;
    }

    public int getNumCardHand(){
        return numCardHand;
    }

    public void setCurrentPlayPoints(int paramPlayPoint){
        currentPlayPoints = paramPlayPoint;
    }

    public void setShields(int paramShields){
        shields = paramShields;
    }

    public void setPlayerRank(Rank paramRank){
        playerRank = paramRank;
    }

    public void addCardOnHands(Card paramCard){
        cardOnHands.add(paramCard);
        numCardHand++;
    }

    public void addCardOnTable(Card paramCard){
        cardOnTable.add(paramCard);
    }

    public void addCardPlaying(Card paramCard){
        cardPlaying.add(paramCard);
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
