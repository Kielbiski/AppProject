package quest;


import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Tournament extends Card {
    private ArrayList <Player> playerList = new ArrayList<Player>();
    private int roundsPlayed;
    private int shields ; //How many shield the winner gets

    public Tournament(ArrayList<Player> paramPlayerList){
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
            if(winnerValue > playerList.get(i).getCurrentPlayPoints()){playerList.remove(i);}
            else if(winnerValue == playerList.get(i).getCurrentPlayPoints()){}
            else {
                winnerValue = playerList.get(i).getCurrentPlayPoints();
                playerList.remove(i+1);
            }
        }
    }

    public boolean checkTie(){ return (playerList.size() > 1) ? true : false;}

    public int getRoundsPlayed(){return roundsPlayed;}

    public ArrayList<Player> getRemainingPlayer(){return playerList;}

    public int getShields(){return shields;}

    public void tournamentWinner(){
        if(playerList.size()>1){
            System.out.println("The following players won :");
            for(int i=0 ; i < playerList.size();i++){
                playerList.get(i).setShields(playerList.get(i).getShields() + shields);
                System.out.println(playerList.get(i).getPlayerName());
            }
        }
        else{
            System.out.println("The following player won :");
            playerList.get(0).setShields( playerList.get(0).getShields() + shields);
            System.out.println(playerList.get(0).getPlayerName());
        }

    }


}
