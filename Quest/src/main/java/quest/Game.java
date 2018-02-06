package quest;

import java.util.ArrayList;

public class Game {
    private GameState state = new GameState();

    public Game()
    {

    }

    public void gameLoop()
    {
        ArrayList<Player> players = state.getPlayers();
        while(true)
        {
            for (int i = 0; i < players.size(); ++i)
            {
                players.get(i).setShields(players.get(i).getShields() + 1);
                System.out.println(players.get(i).getPlayerName() + " has " + players.get(i).getShields() + " shields");
                if (players.get(i).getShields() >= 20)
                {
                    return;
                }
            }
        }
    }
}
