package quest;

import javafx.scene.control.ChoiceDialog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Quest extends StoryCard { //story card

    private static final Logger logger = LogManager.getLogger(App.class);

    private ArrayList <Player> playerList;
    private ArrayList <Player> originalPlayerList;
    private ArrayList<QuestStage> stages = new ArrayList<>();
    private Player sponsor;
    private int numStage;
    private QuestStage currentStage = null;
    private int currentStageIndex = 0;
    private int shields;
    private int currentTurnIndex;
    private Player currentPlayer;

    public int getCurrentStageIndex() {
        return currentStageIndex;
    }

    public int getCurrentTurnIndex() {

        return currentTurnIndex;
    }

    private boolean inTest = false;
    private boolean isWinner = false;
    private List<PropertyChangeListener> listener = new ArrayList<>();


    public boolean isWinner() {
        return isWinner;
    }



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
        this.originalPlayerList = playerList;

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

    public ArrayList<QuestStage> getStages() {
        return stages;
    }

    public QuestStage getCurrentStage() {

        return currentStage;
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

    public boolean isInTest() {
        return inTest;
    }

    public void setInTest(boolean inTest) {
        boolean previousTestStatus = this.inTest;
        this.inTest = inTest;
        notifyListeners(this,"test",previousTestStatus,this.inTest);
    }

    public void startQuest(){
        currentStage = stages.get(currentStageIndex);
        currentStage.setParticipatingPlayers(playerList);
        currentTurnIndex = 0;
        currentPlayer = playerList.get(currentTurnIndex);
        if(currentStage instanceof TestStage){
            setInTest(true);
        }
    }

    public boolean isFinished() {
        return isFinished;
    }

    private void questWinners() {
        for (Player player : playerList)
        {
            isWinner = true;
            player.setShields(player.getShields() + shields);

        }
        isFinished = true;


        logger.info("Returning player list that won the "+ this.getName()+" quest");
    }

    public void wipeWeapons(){
        if(playerList != null) {
            for (Player player : playerList) {
                ArrayList<AdventureCard> found = new ArrayList<>();
                for (AdventureCard card : player.getCardsOnTable()) {
                    if (card instanceof Weapon) {
                        found.add(card);
                    }
                }
                player.getCardsOnTable().removeAll(found);
            }
        }
    }


    private ArrayList<Player> getFoeStageWinners(FoeStage foeStage){
        ArrayList<Player> winningPlayers = new ArrayList<>();
        logger.info("FoeStage: Going through participant battle points");
        int playerBattlePoints;
        for(Player player : foeStage.getParticipatingPlayers()){
            playerBattlePoints = (player.getRankBattlePoints() + player.calculateCardsBattlePoints(player.getCardsOnTable(), this));
            logger.info("FoeStage: "+ foeStage.getParticipatingPlayers() + " has "+ playerBattlePoints + " battle points");
            if(playerBattlePoints >= foeStage.getTotalBattlePoints()) {
                winningPlayers.add(player);
            }
        }
        logger.info("FoeStage: Returning list of winning player");
        return winningPlayers;
    }

    public void nextTurn(){
        currentTurnIndex++;
        if(currentTurnIndex >= playerList.size()){
            currentTurnIndex = 0;
            if(currentStage instanceof FoeStage) {
                playerList = getFoeStageWinners((FoeStage) currentStage);
            }
            currentStageIndex++;
            wipeWeapons();
            if(currentStageIndex >= stages.size()||playerList.size()==0){
                questWinners();
            } else {
                notifyListeners(this,"stage",currentStageIndex-1,currentStageIndex);
                currentPlayer = getPlayerList().get(currentTurnIndex);
                currentStage = stages.get(currentStageIndex);
                currentStage.setParticipatingPlayers(playerList);
                if(currentStage instanceof TestStage){
                    setInTest(true);
                }
            }

            logger.info("Set current index for player turn to "+ currentTurnIndex +".");
        }
        else{
            currentPlayer = getPlayerList().get(currentTurnIndex);
            logger.info("Set current index for player turn to "+ currentTurnIndex +".");
        }
    }

    private void notifyListeners(Object object, String property, int oldStage, int newStage) {
        for (PropertyChangeListener name : listener) {
            name.propertyChange(new PropertyChangeEvent(this, property, oldStage, newStage));
        }
    }

    private void notifyListeners(Object object, String property, boolean oldTestStatus, boolean newTestStatus) {
        for (PropertyChangeListener name : listener) {
            name.propertyChange(new PropertyChangeEvent(this, property, oldTestStatus, newTestStatus));
        }
    }

    public void addChangeListener(PropertyChangeListener newListener) {
        listener.add(newListener);
    }

}

