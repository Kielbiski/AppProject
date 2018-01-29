package quest;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Player {
    private String playerName;
    private int currentPlayPoints;
    private int shields;
    private ArrayList <Card> cardOnHands = new ArrayList<Card>();
    private ArrayList <Card> cardOnTable = new ArrayList<Card>();
    private ArrayList <Card> cardPlaying = new ArrayList<Card>();

    public Player(String paramName){
        playerName = paramName ;
        currentPlayPoints = 0;
        shields = 0;

    }

    public String getPlayerName(){ return playerName;}
    public int getShields(){ return shields;}
    public void setShields(int x){  shields =x; }
    public int getCurrentPlayPoints(){ return currentPlayPoints;}

}
