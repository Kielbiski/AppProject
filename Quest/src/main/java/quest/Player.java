package quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;


enum Rank {SQUIRE, KNIGHT, CHAMPION_KNIGHT, KNIGHT_OF_THE_ROUND_TABLE;
    public Rank next()
    {
        if(ordinal() >= Rank.values().length)
        {
            return KNIGHT_OF_THE_ROUND_TABLE;
        }
        else
        {
            return Rank.values()[ordinal() + 1];
        }
    }
}

public class Player {

    private static final Logger logger = LogManager.getLogger(App.class);
    private final int HAND_LIMIT = 12;

    private String playerName;
    private int battlePoints;
    private int shields;
    private int currentBid;
    private Rank playerRank;
    //private AbstractAI aI;
    private ArrayList<AdventureCard> cardsOnTable = new ArrayList<>();
    private ArrayList<AdventureCard> cardsInHand = new ArrayList<>();
    private ArrayList<AdventureCard> tournamentCards = new ArrayList<>();
    private List<PropertyChangeListener> listener = new ArrayList<>();
    protected boolean handFull;

    Player(String paramName){
        playerName = paramName ;
        shields = 0;
        battlePoints =0;
        currentBid = 0;
        playerRank = Rank.SQUIRE;

        logger.info("Successfully called  "+this.getPlayerName()+" constructor.");
    }

    public ArrayList<AdventureCard> getTournamentCards() {
        return tournamentCards;
    }

    public ArrayList<AdventureCard> getCardsOnTable()
    {
        logger.info("Returning " + this.playerName+ " cards on table.");
        return cardsOnTable;
    }

    public boolean isHandFull() {
        return handFull;
    }

    private void setHandFull(boolean full) {
        boolean previousState = this.handFull;
        this.handFull = full;
        notifyListeners(this,"handFull",previousState,this.handFull);

    }

    public ArrayList<AdventureCard> getCardsInHand()
    {
        logger.info("Returning " + this.playerName+ " cards on hand.");
        return cardsInHand;
    }

    public String getPlayerName()
    {
        logger.info("Returning " + this.playerName+ ".");
        return playerName;
    }
    
    public int getShields()
    {
        logger.info("Returning " + this.playerName+ " # shields.");
        return shields;
    }
    
    public Rank getPlayerRank()
    {
        logger.info("Returning " + this.playerName+ " rank.");
        return playerRank;
    }

    public int getBidDiscount(Quest currentQuest){
        int bids = 0;
        for(AdventureCard adventureCard : cardsOnTable){
            if(adventureCard instanceof Ally){
                bids = adventureCard.getBids();
                for(AdventureCard checkAdventureCard: cardsOnTable){
                    if((((Ally) adventureCard).getAffectedEntity().equals(checkAdventureCard.getName())) || (((Ally) adventureCard).getAffectedEntity().toLowerCase().equals(currentQuest.getName())) && (adventureCard != checkAdventureCard)){
                        bids = adventureCard.getBids() + adventureCard.getBonusBids();
                        break;
                    }
                }
            }
        }
        return bids;
    }

    public String stringifyRank(){
        switch(playerRank){
            case SQUIRE:
                return "Squire";
            case KNIGHT:
                return "Knight";
            case CHAMPION_KNIGHT:
                return "Champion Knight";
            case KNIGHT_OF_THE_ROUND_TABLE:
                return "Knight of the Round Table";
            default:
                return "";
        }
    }

    public int getNumCardsInHand()
    {
        logger.info("Returning " + this.playerName+ " number of card on hand.");
        return cardsInHand.size();
    }

    public void addBonusPoint (int paramBonusPoint)
    {
        logger.info("Adding" + paramBonusPoint + " bonus point to " + this.playerName);
        battlePoints += paramBonusPoint;
    }
    
    public void resetBattlePoints ()
    {
        logger.info("Resetting " + this.playerName+ " battle points.");
        battlePoints = 0;
    }

    public int getBattlePoints()
    {
        logger.info("Returning" + this.playerName+ " battle points : " + battlePoints + ".");
        return battlePoints;
    }

    public int getCurrentBid()
    {
        logger.info("Returning " + this.playerName+ " bids:" +currentBid+" .");
        return currentBid ;
    }

    public void setCardsOnTable(ArrayList<AdventureCard> cardsOnTable)
    {
        logger.info("Set " + this.playerName+ " cards on table.");
        this.cardsOnTable = cardsOnTable;
    }

    public void setCurrentBid(int currentBid)
    {
        logger.info("Set " + this.playerName+ " bids.");
        this.currentBid = currentBid;
    }

    public void setShields(int paramShields)
    {
        logger.info("Set " + this.playerName+ " shields.");
        shields = paramShields;
        updateRank(shields);
    }

    private void setPlayerRank(Rank paramRank){
        logger.info("Set " + this.playerName+ " rank.");
        playerRank = paramRank;
    }

    public void addCardToHand(AdventureCard paramCard){
        logger.info("Adding the following card "+ paramCard.getName()+" to " + this.playerName+ " hand.");
        cardsInHand.add(paramCard);
        if(cardsInHand.size()>HAND_LIMIT){
            setHandFull(true);
        }
    }
    
    public void addCardToTable(AdventureCard paramCard){
        logger.info("Adding the following card "+ paramCard.getName()+" to " + this.playerName+ " cards on the table.");
        cardsOnTable.add(paramCard);
    }
    public void addCardToTournamnet(AdventureCard paramCard){
        logger.info("Adding the following card "+ paramCard.getName()+" to " + this.playerName+ " cards on the table.");
        tournamentCards.add(paramCard);
    }

    public void moveFromTournamentToTable(){
        ArrayList<AdventureCard> cardsToMove = new ArrayList<>();
        for(AdventureCard card: tournamentCards){
            if(card instanceof Ally){
                cardsToMove.add(card);
            }
        }
        //send to discard
        tournamentCards.clear();
        cardsOnTable.addAll(cardsToMove);
    }

    public void removeCardFromHand(AdventureCard paramCard){
        logger.info("Removing the following card "+ paramCard.getName()+" from " + this.playerName+ " hand.");
        cardsInHand.remove(paramCard);
        if(isHandFull()){
            if(cardsInHand.size()<=HAND_LIMIT){
                setHandFull(false);
            }
        }
    }
    
    public void removeCardFromTable(AdventureCard paramCard){
        logger.info("Removing the following card "+ paramCard.getName()+" from " + this.playerName+ " cards on the table.");
        cardsOnTable.remove(paramCard);
    }

    public boolean isValidDrop(AdventureCard card){
        for(AdventureCard matchCard: cardsOnTable){
            if(matchCard instanceof Weapon|| matchCard instanceof Amour){
                if(card.getName().toLowerCase().equals(matchCard.getName().toLowerCase())){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean tooManyCards(){
        logger.info("Verifying if " + this.playerName +" has more than 12 cards.");
        return cardsInHand.size() > 12;
    }

    int calculateCardsBattlePoints(ArrayList<AdventureCard> paramCardList, Quest currentQuest) {
        int totalBattlePoints = 0;
        int currentBattlePoints;
        for(AdventureCard adventureCard : paramCardList) {
            currentBattlePoints = adventureCard.getBattlePoints();
            if(adventureCard instanceof Ally){
                for(AdventureCard checkAdventureCard: paramCardList){
                    if((((Ally) adventureCard).getAffectedEntity().toLowerCase().equals(checkAdventureCard.getName().toLowerCase())) || (((Ally) adventureCard).getAffectedEntity().toLowerCase().equals(currentQuest.getName())) && (adventureCard != checkAdventureCard)){
                        currentBattlePoints = adventureCard.getBattlePoints() + adventureCard.getBonusBattlePoints();
                        break;
                    }
                }
            }

            totalBattlePoints += currentBattlePoints;
        }

        return totalBattlePoints;
    }
    int calculateCardsBattlePoints(ArrayList<AdventureCard> paramCardList) {
        int totalBattlePoints = 0;
        for(AdventureCard adventureCard : paramCardList) {
            totalBattlePoints += adventureCard.getBattlePoints();
        }
        return totalBattlePoints;
    }

    public int getRankBattlePoints() {
        switch (playerRank){
            case SQUIRE:
                battlePoints = 5;
                break;
            case KNIGHT:
                battlePoints = 10;
                break;
            case CHAMPION_KNIGHT:
                battlePoints = 20;
                break;
            default:
                break;
        }
        logger.info("Returning " + this.playerName +" calculated battle points :" + battlePoints+ " .");
        return battlePoints;
    }

    private int getRankShields(Rank playerRank) {
        int rankShields = 0;
        switch (playerRank){
            case SQUIRE:
                rankShields = 0;
                break;
            case KNIGHT:
                rankShields = 5;
                break;
            case CHAMPION_KNIGHT:
                rankShields = 12;
                break;
            case KNIGHT_OF_THE_ROUND_TABLE:
                rankShields = 22;
                break;
            default:
                break;
        }
        logger.info("Returning " + this.playerName +" calculated battle points :" + battlePoints+ " .");
        return rankShields;
    }

    private void updateRank(int numShields){
        Rank rank;
        if(numShields < 5){
            rank = Rank.SQUIRE;
        } else if (numShields < 12){
            rank = Rank.KNIGHT;
        } else if (numShields < 22) {
            rank = Rank.CHAMPION_KNIGHT;
        } else {
            rank = Rank.KNIGHT_OF_THE_ROUND_TABLE;
        }
        setPlayerRank(rank);
    }

    public void playCardsAI(ArrayList<AdventureCard> cardsToPlay){
        for(AdventureCard adventureCard : cardsToPlay){
            addCardToTable(adventureCard);
            removeCardFromHand(adventureCard);
        }
    }

    public int getRequiredShieldsForNextRank(){ return getRankShields(playerRank.next())-shields; }

    private void notifyListeners(Object object, String property, boolean oldFull, boolean newFull) {
        for (PropertyChangeListener name : listener) {
            name.propertyChange(new PropertyChangeEvent(this, property, oldFull, newFull));
        }
    }

    public void addChangeListener(PropertyChangeListener newListener) {
        listener.add(newListener);
    }

}
