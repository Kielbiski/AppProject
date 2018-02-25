package quest;

import java.util.ArrayList;

public class Quest extends StoryCard { //story card

    private ArrayList <Player> playerList;
    private Player sponsor;
    private int numStage;
    private int currentStage;
    private int shields;
    ArrayList <Foe> questFoes = new ArrayList<>();

    Quest(String paramName, String paramImageFilename, int paramNumStage) {
        super(paramName, paramImageFilename);
        numStage = paramNumStage;
        currentStage = 0;
        shields = numStage;
    }

    Quest(String paramName, String paramImageFilename, int paramNumStage, Foe questFoe) {
        super(paramName, paramImageFilename);
        questFoes.add(questFoe);
        numStage = paramNumStage;
        currentStage = 0;
        shields = numStage;
    }

    Quest(String paramName, String paramImageFilename, int paramNumStage, ArrayList<Foe> paramQuestFoes) {
        super(paramName, paramImageFilename);
        questFoes = paramQuestFoes;
        numStage = paramNumStage;
        currentStage = 0;
        shields = numStage;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    public Player getSponsor() {
        return sponsor;
    }

    public void setSponsor(Player sponsor) {
        this.sponsor = sponsor;
    }

    public ArrayList<Foe> getQuestFoes() {
        return questFoes;
    }

    public int getShields() {
        return shields;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public int getNumStage() {
        return numStage;
    }


    public void setCurrentStage(int currentStage) {
         this.currentStage = currentStage;
    }

    public void questPlayStageNoTest() {
        int winnerValue = sponsor.calculateBattlePoints();
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
        currentStage++;
    }


    public ArrayList<Player> questWinners() {
        for (Player player : playerList) {
            player.setShields(player.getShields() + shields);
        }
        return playerList;
    }

}


