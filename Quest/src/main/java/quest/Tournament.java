package quest;

import java.util.ArrayList;

public class Tournament extends Card {
    private ArrayList <Player> playerList = new ArrayList<Player>();
    private int roundsPlayed;
    private int shields ; //How many shield the winner gets

    public Tournament(String paramName, String paramImageFilename, ArrayList<Player> paramPlayerList){
        super(paramName, paramImageFilename);
        playerList.addAll(paramPlayerList);
        roundsPlayed = 0;
        shields = playerList.size();
    }

    /*Loop backward into the collection to find the max point a players has
      and remove player who have less than max from the collection.
     */
    public void tournamentPlay(){
        int winnerValue = playerList.get(playerList.size()-1).getCurrentPlayPoints();
        for(int i=playerList.size()-2 ; i >= 0  ;i--){
            if(winnerValue > playerList.get(i).getCurrentPlayPoints()){
                playerList.remove(i);
            }
            else if(winnerValue == playerList.get(i).getCurrentPlayPoints()){
                //Do nothing.
            }
            else {
                winnerValue = playerList.get(i).getCurrentPlayPoints();
                playerList.subList(i+1, playerList.size() ).clear();
            }
        }
        roundsPlayed++;
    }

    public boolean checkTie(){
        return (playerList.size() > 1);
    }

    public int getRoundsPlayed(){
        return roundsPlayed;
    }

    public ArrayList <Player> getRemainingPlayer(){
        return playerList;
    }

    public int getShields(){
        return shields;
    }

    public ArrayList <Player>  tournamentWinner() {
        for (Player player : playerList) {
            player.setShields(player.getShields() + shields);
        }
        return playerList;
    }
}
