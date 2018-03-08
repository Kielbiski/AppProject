package quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Tournament extends StoryCard {

    private static final Logger logger = LogManager.getLogger(App.class);

    private ArrayList <Player> playerList = new ArrayList<>();
    private int roundsPlayed;
    private int shields ; //How many shield the winner gets
    private int currentTurnIndex;
    private int currentRound=1;
    private boolean tournamentOver = false;
    private ArrayList<Player> winners = new ArrayList<>();
    private Player currentPlayer;

    Tournament(String paramName, String paramImageFilename, int paramShields, ArrayList<Player> paramPlayerList){
        super(paramName, paramImageFilename);
        playerList.addAll(paramPlayerList);
        roundsPlayed = 1;
        shields = paramShields + playerList.size();
        logger.info("Successfully called :" + this.getName() + " constructor");
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }
    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    public boolean checkTie(){
        logger.info("Checking if " + this.getName() +" has more than one winner.");
        return (playerList.size() > 1);
    }

    public int getRoundsPlayed(){
        logger.info("Returning number of rounds this " + this.getName()+ " has.");
        return roundsPlayed;
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

    private void wipeWeapons(){
        for(Player player: playerList){
            ArrayList<AdventureCard> found = new ArrayList<>();
            for(AdventureCard card: player.getTournamentCards()){
                if(card instanceof Weapon || card instanceof Amour){
                    found.add(card);
                }
            }
            //discard instead
            player.getTournamentCards().removeAll(found);
        }
    }

    public int getCurrentTurnIndex() {
        return currentTurnIndex;
    }

    public void setCurrentTurnIndex(int currentTurnIndex) {
        this.currentTurnIndex = currentTurnIndex;
    }

    public Player getCurrentPlayer() {

        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public boolean isTournamentOver() {

        return tournamentOver;
    }

    public void setTournamentOver(boolean tournamentOver) {
        this.tournamentOver = tournamentOver;
    }

    private ArrayList<Player> getTournamentWinners(){
        ArrayList<Player> playersWithHighestBP = playerList;
        ArrayList<Player> returnList = new ArrayList<>();
        playersWithHighestBP.sort(Comparator.comparing(object2 -> (object2.getRankBattlePoints() + object2.calculateCardsBattlePoints(object2.getTournamentCards()))));
        Collections.reverse(playersWithHighestBP);
        int highestBP =0;
        highestBP += playersWithHighestBP.get(0).getRankBattlePoints();
        highestBP += playersWithHighestBP.get(0).calculateCardsBattlePoints(playersWithHighestBP.get(0).getTournamentCards());

        for(Player player : playersWithHighestBP){
            if((player.getRankBattlePoints() + player.calculateCardsBattlePoints(player.getTournamentCards())) == highestBP){
                returnList.add(player);
            }
            else{
                break;
            }
        }
        return returnList;
    }




    public ArrayList<Player> getWinners() {
        return winners;
    }

    public void setWinners(ArrayList<Player> winners) {
        this.winners = winners;
    }

    public void nextTurn(){
        currentTurnIndex++;
        if(currentTurnIndex >= playerList.size()){
            winners = getTournamentWinners();
            wipeWeapons();
            for (Player player : playerList) {
                player.moveFromTournamentToTable();
            }
            if(winners.size()>1){
                if (currentRound==1) {
                    currentTurnIndex = 0;
                    ArrayList<Player> remainingPlayers = new ArrayList<>();
                    for (Player player : playerList) {
                        if(winners.contains(player)){
                            remainingPlayers.add(player);
                        }
                    }
                    playerList = remainingPlayers;
                    Collections.reverse(playerList);
                    currentPlayer = getPlayerList().get(currentTurnIndex);
                    currentRound++;
                }
                else if(currentRound>1){
                    for (Player player : winners) {
                        rewardWinner(player);
                    }
                }
            }
            else{
                rewardWinner(winners.get(0));
            }
            logger.info("Set current index for player turn to "+ currentTurnIndex +".");
        }
        else{
            currentPlayer = getPlayerList().get(currentTurnIndex);
            logger.info("Set current index for player turn to "+ currentTurnIndex +".");
        }
    }


    public void rewardWinner(Player winner) {
        winner.setShields(winner.getShields() + shields);
        setTournamentOver(true);
        logger.info("Returning the number players of that won the " + this.getName() +".");

    }
}

