package quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class QuestStage {

    private static final Logger logger = LogManager.getLogger(App.class);

    private ArrayList<Player> participatingPlayers;

    QuestStage(ArrayList<Player> participatingPlayers){
        logger.info("Successfully called : questStage constructor.");
        logger.info("Setting quest stage participants");
        this.participatingPlayers = participatingPlayers;
    }

    ArrayList<Player> getParticipatingPlayers() {
        logger.info("Returning quest stage participants");
        return participatingPlayers;
    }
    public void setParticipatingPlayers(ArrayList<Player> participatingPlayers){
        this.participatingPlayers = participatingPlayers;
    }
}
