package quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Weapon extends AdventureCard
{
    private static final Logger logger = LogManager.getLogger(App.class);

    Weapon(String paramName, String paramImageFilename, int paramBattlePoints)
    {
        super(paramName, paramImageFilename);
        battlePoints = paramBattlePoints;
        logger.info("Successfully weapon :" + this.getName() + " constructor");
    }
}
