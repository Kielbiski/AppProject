package quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Foe extends AdventureCard {

    private static final Logger logger = LogManager.getLogger(App.class);

    public Foe(String paramName, String paramImageFilename, int paramBattlePoints, int paramBonusPointsFromQuest)
    {

        super(paramName, paramImageFilename);
        battlePoints = paramBattlePoints;
        bonusBattlePoints = paramBonusPointsFromQuest;
        logger.info("Successfully called : Foe constructor");

    }
}

