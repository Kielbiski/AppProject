package quest.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import quest.client.App;

class Amour extends Ally
{
    private static final Logger logger = LogManager.getLogger(App.class);
    Amour(){}
    Amour(String pName, String pImageFilename)
    {
        super(pName, pImageFilename, 10, 1, 0, 0, "");
        logger.info("Successfully called : Amour constructor");

    }
}
