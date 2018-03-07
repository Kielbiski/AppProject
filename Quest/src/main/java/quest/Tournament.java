package quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class Tournament extends StoryCard {

    private static final Logger logger = LogManager.getLogger(App.class);

    private ArrayList <Player> playerList = new ArrayList<>();
    private int roundsPlayed;
    private int shields ; //How many shield the winner gets

    Tournament(String paramName, String paramImageFilename, int paramShields, ArrayList<Player> paramPlayerList){
        super(paramName, paramImageFilename);
        playerList.addAll(paramPlayerList);
        roundsPlayed = 1;
        this.shields = paramShields;
        logger.info("Successfully called :" + this.getName() + " constructor");
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public boolean checkTie(){
        logger.info("Checking if " + this.getName() +" has more than one winner.");
        return (playerList.size() > 1);
    }

    public int getRoundsPlayed(){
        logger.info("Returning number of rounds this " + this.getName()+ " has.");
        return roundsPlayed;
    }

    public ArrayList<Player> getRemainingPlayers(){
        logger.info("Returning remaining players of the " + this.getName() +".");
        return playerList;
    }

    public int getShields(){
        logger.info("Returning number of shields this " + this.getName() +" has.");
        return shields;
    }

    public void setRoundsPlayed(int paramRoundPlayed ){
        logger.info("Setting " + this.getName() +" round.");
        roundsPlayed = paramRoundPlayed;
    }

    public void removePlayer(Player player ){
        logger.info("Removing from this  " + this.getName() +" the following player: "+ player+".");
        playerList.remove(player);
    }

    public ArrayList<Player> getTournamentWinner() {
        for (Player player : playerList) {
            player.setShields(player.getShields() + shields);
        }
        logger.info("Returning the number players of that won the " + this.getName() +".");
        return playerList;

    }
}

