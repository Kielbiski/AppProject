package quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class TestStage extends QuestStage {

    private static final Logger logger = LogManager.getLogger(App.class);

    private AdventureCard sponsorTestCard;

    TestStage(AdventureCard sponsorTestCard, ArrayList<Player> participatingPlayers)
    {
        super(participatingPlayers);
        this.sponsorTestCard = sponsorTestCard;
        logger.info("Successfully called : TestStage constructor");
    }

    public ArrayList<Player> getWinners() {
        Player winningPlayer= null;
        int currentHighestBid = 0;
        for(Player player : this.getParticipatingPlayers())
        {
            if (player.getCurrentBid() > currentHighestBid)
            {
                currentHighestBid = player.getCurrentBid();
                winningPlayer = player;
                logger.info("Current player with highest bid" + winningPlayer +" for this testStage." );
            }
        }

        logger.info("Returning" + winningPlayer +" as the testStage winner." );
        ArrayList<Player> winningPlayerArray = new ArrayList<>();
        winningPlayerArray.add(winningPlayer);
        return winningPlayerArray;
    }
}
