package quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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

    private static final Logger logger = LogManager.getLogger(App.class);
    protected final int HAND_LIMIT = 12;

    protected String playerName;
    protected int battlePoints;
    protected int shields;
    protected int currentBid;
    protected Rank playerRank;
    //private AbstractAI aI;
    protected ArrayList<AdventureCard> cardsOnTable = new ArrayList<>();
    protected ArrayList<AdventureCard> cardsInHand = new ArrayList<>();
    protected List<PropertyChangeListener> listener = new ArrayList<>();
    protected boolean handFull;

    Player(){}

    /*
    Player(String paramName, int i){
        playerName = paramName ;
        shields = 0;
        battlePoints =0;
        currentBid = 0;
        playerRank = Rank.SQUIRE;
        if(i==1)
        {
            aI = new Strategy1();
        }
        else
        {
            aI = new Strategy1();
        }
        //logger.info("Successfully called  "+this.getPlayerName()+" constructor with strategy: "+ this.aI.typeStrategy+".");
    }

    */
    Player(String paramName){
        playerName = paramName ;
        shields = 0;
        battlePoints =0;
        currentBid = 0;
        playerRank = Rank.SQUIRE;

        logger.info("Successfully called  "+this.getPlayerName()+" constructor.");
    }

    public ArrayList<AdventureCard> getCardsOnTable()
    {
        logger.info("Returning " + this.playerName+ " cards on table.");
        return cardsOnTable;
    }

    public boolean isHandFull() {
        return handFull;
    }

    public void setHandFull(boolean full) {
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
        return currentBid;
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
    }
    
    public void setPlayerRank(Rank paramRank){
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
    
//    public void addCardToPlaying(AdventureCard paramCard){
//        cardsPlaying.addCard(paramCard);
//    }

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
            if(card.getName().toLowerCase().equals(matchCard.getName().toLowerCase())){
                return false;
            }
        }
        return true;

    }
    
//    public void removeCardFromPlaying(AdventureCard paramCard){
//        cardsPlaying.removeCard(paramCard);
//    }

    public boolean tooManyCards(){
        logger.info("Verifying if " + this.playerName +" has more than 12 cards.");
        return cardsInHand.size() > 12;
    }

    int calculateCardsBattlePoints(ArrayList<AdventureCard> paramCardList, Quest currentQuest) {
        int totalBattlePoints = 0;
        for(AdventureCard adventureCard : paramCardList) {
            if(adventureCard instanceof Ally){
                for(AdventureCard checkAdventureCard: paramCardList){
                    if((((Ally) adventureCard).getAffectedEntity().toLowerCase().equals(checkAdventureCard.getName().toLowerCase())) || (((Ally) adventureCard).getAffectedEntity().toLowerCase().equals(currentQuest.getName())) && (adventureCard != checkAdventureCard)){
                        adventureCard.setBattlePoints(adventureCard.getBattlePoints() + adventureCard.getBonusBattlePoints());
                        break;
                    }
                }
            }

            totalBattlePoints += adventureCard.getBattlePoints();
        }

        return totalBattlePoints;

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
        logger.info("Returning " + this.playerName +" calculated battle points :" + battlePoints+ " .");
        return battlePoints;
    }

    public int getRequiredShieldsForNextRank() {
        switch(playerRank)
        {
            case SQUIRE:
                logger.info("Returning the number of shields needed for "+this.playerName +" to proceed to the next rank. ");
                return 5;
            case KNIGHT:
                logger.info("Returning the number of shields needed for "+this.playerName +" to proceed to the next rank. ");
                return 7;
            case CHAMPION_KNIGHT:
                logger.info("Returning the number of shields needed for "+this.playerName +" to proceed to the next rank. ");
                return 10;
            default:
                logger.info("Default case for "+this.playerName +" rank. ");
                return 99;
        }
    }

    public void confirmRank() {
        int requiredShields = this.getRequiredShieldsForNextRank();
        if(playerRank != Rank.KNIGHT_OF_THE_ROUND_TABLE && shields >= requiredShields){
            playerRank = playerRank.next();
            shields -= requiredShields;
        }
        logger.info("Confirming " + this.playerName +" rank.");
    }
    private void notifyListeners(Object object, String property, boolean oldFull, boolean newFull) {
        for (PropertyChangeListener name : listener) {
            name.propertyChange(new PropertyChangeEvent(this, property, oldFull, newFull));
        }
    }

    public void addChangeListener(PropertyChangeListener newListener) {
        listener.add(newListener);
    }

}
