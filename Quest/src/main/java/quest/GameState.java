package quest;

import java.util.ArrayList;

public class GameState {

    private Card currentStory;
    private ArrayList<Player> players = new ArrayList<>();

    public GameState()
    {
        players.add(new Player("Player 1"));
        players.add(new Player("Player 2"));
    }

    public Card getCurrentStory() {return currentStory;}
    public ArrayList<Player> getPlayers() {return players;}
}
