package quest.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import quest.client.App;

public class Test extends AdventureCard
{
    private static final Logger logger = LogManager.getLogger(App.class);
    Test(){className = "Test";}
    private Test(String name, String imageFilename, int pBids)
    {
        super(name, imageFilename);
        className = "Test";
        bids = pBids;
        logger.info("Successfully called : Test constructor for " + this.getName() + " card.");
    }

    public Test(String name, String imageFilename)
    {
        this(name, imageFilename, 3);
        logger.info("Successfully called : Test constructor for " + this.getName() + " card.");
    }
}
