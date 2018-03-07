package quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class Tournament extends StoryCard {

    private static final Logger logger = LogManager.getLogger(App.class);

    private ArrayList <Player> playerList = new ArrayList<>();
    private int roundsPlayed;
    private int shields ; //How many shield the winner gets

    Tournament(String paramName, String paramImageFilename, ArrayList<Player> paramPlayerList){
        super(paramName, paramImageFilename);
        playerList.addAll(paramPlayerList);
        roundsPlayed = 1;
        shields = playerList.size();
        logger.info("Successfully called :" + this.getName() + " constructor");
    }



    /*Loop backward into the collection to find the max point a players has
      and remove player who have less than max from the collection.
     */

//    public void tournamentPlay(GameState state){
//        int winnerValue = playerList.get(playerList.size()-1).calculateBattlePoints(state);
//        for(int i=playerList.size()-2; i >= 0; i--){
//            if(winnerValue >= playerList.get(i).calculateBattlePoints(state)){
//                playerList.remove(i);
//            }
//            else {
//                //Set the new winnerValue and remove all players already checked in the list (their Battle Points will always be less than the new value)
//                winnerValue = playerList.get(i).calculateBattlePoints(state);
//                playerList.subList(i+1, playerList.size()).clear();
//            }
//        }
//        roundsPlayed++;
//    }

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

