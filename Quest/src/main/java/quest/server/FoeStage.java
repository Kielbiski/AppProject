package quest.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import quest.client.App;

import java.util.ArrayList;

public class FoeStage extends QuestStage {

    private static final Logger logger = LogManager.getLogger(App.class);

    private int totalBattlePoints = 0;

    FoeStage(ArrayList<AdventureCard> sponsorCards, ArrayList<Player> participatingPlayers){
        super(participatingPlayers);
        logger.info("FoeStage: Calculating sponsor battle points");
        for(AdventureCard adventureCard : sponsorCards){
            totalBattlePoints += adventureCard.getBattlePoints();
        }
        logger.info("FoeStage: Sponsor has " + totalBattlePoints + " battle points");
        logger.info("Successfully called : FoeStage constructor");

    }

    public int getTotalBattlePoints() {
        return totalBattlePoints;
    }
}
