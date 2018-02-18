package quest;

import java.util.ArrayList;

public class Quest extends Card {

    private ArrayList<Player> playerList = new ArrayList<Player>();
    private Player hostPlayer = new Player();
    private int numStage;
    private int curStage;
    private int shields;

    public Quest(String paramName, String paramImageFilename, int paramNumStage, ArrayList<Player> paramPlayerList, Player paramPlayer) {
        super(paramName, paramImageFilename);
        playerList = paramPlayerList;
        hostPlayer = paramPlayer;
        numStage = paramNumStage;
        curStage = 0;
        shields = numStage;
    }

    public int getShields() {
        return shields;
    }

    public int getNumStage() {
        return numStage;
    }

    public Player getHostPlayer() {
        return hostPlayer;
    }

    public void questPlayStageNoTest() {
        int winnerValue = hostPlayer.getCurrentPlayPoints();
        int i = 0;
        int j = playerList.size();
        while (i < j) {
            if (winnerValue > playerList.get(i).getCurrentPlayPoints()) {
                playerList.remove(i);
                j--;
            } else (winnerValue == playerList.get(i).getCurrentPlayPoints()) {
                i++;
            }
        }
        curStage++;
    }


    public ArrayList<Player> questWinners() {
        for (Player player : playerList) {
            player.setShields(player.getShields() + shields);
        }
        return playerList;
    }

}
