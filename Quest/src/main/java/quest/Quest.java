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
        int w = 0;
        while (w < playerList.size()) {
            playerList.get(w).calculateBattlePoints();
            w++;
        }
        int winnerValue = hostPlayer.getBattlePoints();
        int i = 0;
        int j = playerList.size();
        while (i < j) {
            if (winnerValue > playerList.get(i).getBattlePoints()) {
                playerList.remove(i);
                j--;
            } else (winnerValue == playerList.get(i).getBattlePoints()) {
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
