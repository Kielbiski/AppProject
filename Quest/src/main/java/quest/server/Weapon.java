package quest.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import quest.client.App;

public class Weapon extends AdventureCard
{
    private static final Logger logger = LogManager.getLogger(App.class);
    Weapon(){className = "Weapon";}
    Weapon(String paramName, String paramImageFilename, int paramBattlePoints)
    {
        super(paramName, paramImageFilename);
        className = "Weapon";
        battlePoints = paramBattlePoints;
        logger.info("Successfully weapon :" + this.getName() + " constructor");
    }

}
