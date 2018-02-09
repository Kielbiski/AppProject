package quest;

import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players = new ArrayList<>();

    public Game()
    {

    }

    public void gameLoop()
    {
        boolean gameOver = false;
        while(!gameOver)
        {
            for (int i = 0; i < players.size(); ++i)
            {
                players.get(i).setShields(players.get(i).getShields() + 1);
                System.out.println(players.get(i).getPlayerName() + " has " + players.get(i).getShields() + " shields");
                if (players.get(i).getShields() >= 20)
                {
                    gameOver = true;
                    break;
                }
            }
        }
    }

}
