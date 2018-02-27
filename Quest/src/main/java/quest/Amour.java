package quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Amour extends AdventureCard
{
    private static final Logger logger = LogManager.getLogger(App.class);

    public Amour(String pName, String pImageFilename)
    {
        super(pName, pImageFilename);
        battlePoints = 10;
        bids = 1;
        logger.info("Successfully called : Amour constructor");

    }
}
