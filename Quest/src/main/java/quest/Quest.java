package quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class Quest extends StoryCard { //story card

    private static final Logger logger = LogManager.getLogger(App.class);

    private ArrayList <Player> playerList;
    private ArrayList<QuestStage> stages = new ArrayList<>();
    private Player sponsor;
    private int numStage;
    private int currentStage;
    private int shields;
    ArrayList <Foe> questFoes = new ArrayList<>();

    Quest(String paramName, String paramImageFilename, int paramNumStage)
    {

        super(paramName, paramImageFilename);
        numStage = paramNumStage;
        currentStage = 0;
        shields = numStage;
        logger.info("Successfully called : Quest constructor.");

    }

    Quest(String paramName, String paramImageFilename, int paramNumStage, Foe questFoe)
    {
        super(paramName, paramImageFilename);
        questFoes.add(questFoe);
        numStage = paramNumStage;
        currentStage = 0;
        shields = numStage;
        logger.info("Successfully called : Quest constructor.");

    }

    Quest(String paramName, String paramImageFilename, int paramNumStage, ArrayList<Foe> paramQuestFoes)
    {

        super(paramName, paramImageFilename);
        questFoes = paramQuestFoes;
        numStage = paramNumStage;
        currentStage = 0;
        shields = numStage;
        logger.info("Successfully called : Quest constructor.");

    }

    public void setStages(){

    }

    public void addStage(QuestStage stage){
        stages.add(stage);
    }

    public ArrayList<Player> getPlayerList()
    {

        logger.info("Returning player list in "+ this.getName()+" quest");
        return playerList;

    }

    public void setPlayerList(ArrayList<Player> playerList)
    {

        logger.info("Setting player list in "+ this.getName()+" quest");
        this.playerList = playerList;

    }

    public Player getSponsor()
    {
        logger.info("Returning sponsor of the "+ this.getName()+" quest," + sponsor+".");
        return sponsor;
    }

    public void setSponsor(Player sponsor)
    {
        logger.info("Set"+ sponsor + " has sponsor of the "+ this.getName()+" quest.");
        this.sponsor = sponsor;
    }

    public ArrayList<Foe> getQuestFoes()
    {
        logger.info("Returning foes in the "+ this.getName()+" quest.");
        return questFoes;
    }

    public int getShields()
    {
        logger.info("Returning number of shields (" + shields+ " ) in the "+ this.getName()+" quest.");
        return shields;
    }

    public int getCurrentStage()
    {
        logger.info("Returning current stage (" + currentStage + " ) in the "+ this.getName()+" quest.");
        return currentStage;
    }

    public int getNumStage()
    {

        logger.info("Returning numStage of stage (" + currentStage+ " ) in the "+ this.getName()+" quest.");
        return numStage;
    }


    public void setCurrentStage(int currentStage)
    {

        logger.info("Setting current stage to (" + currentStage+ " ) in the "+ this.getName()+" quest.");
        this.currentStage = currentStage;
    }

    public void questPlayStageNoTest() {

        int winnerValue = sponsor.calculateBattlePoints();
        int i = 0;
        int j = playerList.size();

        while (i < j)
        {
            if (winnerValue > playerList.get(i).calculateBattlePoints()) {
                playerList.remove(i);
                j--;
            } else {
                i++;
            }
        }

        logger.info("Eliminating player who did pass the current stage in the "+ this.getName()+" quest");
        currentStage++;

    }


    public ArrayList<Player> questWinners()
    {
        for (Player player : playerList)
        {
            player.setShields(player.getShields() + shields);
        }

        logger.info("Returning player list that won the "+ this.getName()+" quest");
        return playerList;
    }

}

