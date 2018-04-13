package quest.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import quest.client.App;

public class Foe extends AdventureCard {

    private static final Logger logger = LogManager.getLogger(App.class);
    Foe(){className = "Foe";}
    public Foe(String paramName, String paramImageFilename, int paramBattlePoints, int paramBonusPointsFromQuest)
    {

        super(paramName, paramImageFilename);
        className = "Foe";
        battlePoints = paramBattlePoints;
        bonusBattlePoints = paramBonusPointsFromQuest;
        logger.info("Successfully called : Foe constructor");

    }
}

