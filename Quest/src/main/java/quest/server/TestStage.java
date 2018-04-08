package quest.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import quest.client.App;

import java.util.ArrayList;

public class TestStage extends QuestStage {

    private static final Logger logger = LogManager.getLogger(App.class);

    private AdventureCard sponsorTestCard;
    TestStage(){}

    public void setSponsorTestCard(AdventureCard sponsorTestCard) {
        this.sponsorTestCard = sponsorTestCard;
    }

    TestStage(AdventureCard sponsorTestCard, ArrayList<Player> participatingPlayers)
    {
        super(participatingPlayers);
        this.sponsorTestCard = sponsorTestCard;
        logger.info("Successfully called : TestStage constructor");
    }

    public AdventureCard getSponsorTestCard() {
        logger.info("Returning this sponsored card :"+sponsorTestCard.getName()+".");
        return sponsorTestCard;
    }
}
