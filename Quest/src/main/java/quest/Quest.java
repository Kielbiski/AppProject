package quest;

import java.util.ArrayList;

public class Quest extends Card {

    private ArrayList <Player> playerList;
    private Player hostPlayer;
    private int numStage;
    private int curStage;
    private int shields;
    private ArrayList <Foe> questFoes;


    public Quest(String paramName, String paramImageFilename, int paramNumStage) {
        super(paramName, paramImageFilename);

        questFoes = null;
        numStage = paramNumStage;
        curStage = 0;
        shields = numStage;
    }

    public Quest(String paramName, String paramImageFilename, int paramNumStage, Foe questFoe) {
        super(paramName, paramImageFilename);

        questFoes.add(questFoe);
        numStage = paramNumStage;
        curStage = 0;
        shields = numStage;
    }

    public Quest(String paramName, String paramImageFilename, int paramNumStage, ArrayList<Foe> paramQuestFoes) {
        super(paramName, paramImageFilename);

        questFoes = paramQuestFoes;
        numStage = paramNumStage;
        curStage = 0;
        shields = numStage;
    }


    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    public void setHostPlayer(Player hostPlayer) {
        this.hostPlayer = hostPlayer;
    }

    public int getShields() {
        return shields;
    }

    public int getCurStage() {
        return curStage;
    }

    public int getNumStage() {
        return numStage;
    }

    public Player getHostPlayer() {
        return hostPlayer;
    }

    public void setCurStage(int x) {
         curStage = x;
    }

    public void questPlayStageNoTest() {
        int winnerValue = hostPlayer.calculateBattlePoints();
        int i = 0;
        int j = playerList.size();
        while (i < j) {
            if (winnerValue > playerList.get(i).calculateBattlePoints()) {
                playerList.remove(i);
                j--;
            } else {
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

