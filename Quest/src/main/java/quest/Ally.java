package quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Ally extends AdventureCard {

    private static final Logger logger = LogManager.getLogger(App.class);

    public Ally(String paramName, String paramImageFilename, int paramBattlePoints, int paramBids, int paramBonusBattlePoints, int paramBonusBids)
    {

        super(paramName, paramImageFilename);
        battlePoints = paramBattlePoints;
        bids = paramBids;
        bonusBattlePoints = paramBonusBattlePoints;
        bonusBids = paramBonusBids;
        logger.info("Successfully called : Ally Card constructor");

    }
}
