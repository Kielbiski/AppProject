package quest;

import java.util.List;
import java.util.ArrayList;


enum Rank {SQUIRE, KNIGHT, CHAMPION_KNIGHT, KNIGHT_OF_THE_ROUND_TABLE};

public class Player {


    private String playerName;
    private int currentPlayPoints;
    private int shields;
    private Rank playerRank;
    private ArrayList <Card> cardsInHand = new ArrayList<>();
    private ArrayList <Card> cardsOnTable = new ArrayList<>();
    private ArrayList <Card> cardsPlaying = new ArrayList<>();

    public Player(String paramName){
        playerName = paramName ;
        currentPlayPoints = 0;
        shields = 0;
        playerRank = Rank.SQUIRE;
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
    public int getNumCardsInHand(){
        return cardsInHand.size();
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

    public void addCardToHand(Card paramCard){
        cardsInHand.add(paramCard);
    }

    public void addCardToTable(Card paramCard){
        cardsOnTable.add(paramCard);
    }

    public void addCardToPlaying(Card paramCard){
        cardsPlaying.add(paramCard);
    }

    public void addCardsToHand(ArrayList<Card> cards)
    {
        cardsInHand.addAll(cards);
    }

    public void playCards()
    {
        cardsOnTable.addAll(cardsPlaying);
        cardsPlaying.clear();
    }

    public void discard(Card paramCard){
        cardsInHand.remove(paramCard);
    }

    public void removeCardOnTable(int paramIndexPos){
        cardsOnTable.remove(paramIndexPos);
    }
    public void removeCardPlaying(int paramIndexPos){
        cardsPlaying.remove(paramIndexPos);
    }

    public boolean tooManyCard()
    {
        return cardsInHand.size() > 12;
    }
}
