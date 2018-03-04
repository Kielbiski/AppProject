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
    private QuestStage currentStage = null;
    private int currentStageIndex = 0;
    private int shields;
    private int currentTurnIndex;
    private Player currentPlayer;
    private boolean isFinished = false;
    ArrayList <Foe> questFoes = new ArrayList<>();

    Quest(String paramName, String paramImageFilename, int paramNumStage)
    {

        super(paramName, paramImageFilename);
        numStage = paramNumStage;
        shields = numStage;
        logger.info("Successfully called : Quest constructor.");

    }

    Quest(String paramName, String paramImageFilename, int paramNumStage, Foe questFoe)
    {
        super(paramName, paramImageFilename);
        questFoes.add(questFoe);
        numStage = paramNumStage;
        shields = numStage;
        logger.info("Successfully called : Quest constructor.");

    }

    Quest(String paramName, String paramImageFilename, int paramNumStage, ArrayList<Foe> paramQuestFoes)
    {

        super(paramName, paramImageFilename);
        questFoes = paramQuestFoes;
        numStage = paramNumStage;
        shields = numStage;
        logger.info("Successfully called : Quest constructor.");

    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
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

    public int getNumStage()
    {

        logger.info("Returning numStage of stage (" + currentStage+ " ) in the "+ this.getName()+" quest.");
        return numStage;
    }

    public void startQuest(){
        currentStage = stages.get(currentStageIndex);
        currentStage.setParticipatingPlayers(playerList);
        currentTurnIndex = 0;
        currentPlayer = playerList.get(currentTurnIndex);
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void questWinners() {
        for (Player player : playerList)
        {
            player.setShields(player.getShields() + shields);
        }
        isFinished = true;

        logger.info("Returning player list that won the "+ this.getName()+" quest");
    }

    public void nextTurn(){
        currentTurnIndex++;
        if(currentTurnIndex >= playerList.size()){
            currentTurnIndex = 0 ;
            playerList = currentStage.getWinners();
            currentStageIndex++;
            if(currentStageIndex >= stages.size()){
                questWinners();
            } else {
                currentPlayer = getPlayerList().get(currentTurnIndex);
                currentStage = stages.get(currentStageIndex);
                currentStage.setParticipatingPlayers(playerList);
            }
            logger.info("Set current index for player turn to "+ currentTurnIndex +".");
        }
        else{
            currentPlayer = getPlayerList().get(currentTurnIndex);
            logger.info("Set current index for player turn to "+ currentTurnIndex +".");
        }
    }

}

