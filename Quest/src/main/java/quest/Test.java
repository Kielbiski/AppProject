package quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Test extends AdventureCard
{
    private static final Logger logger = LogManager.getLogger(App.class);

    private Test(String name, String imageFilename, int pBids)
    {
        super(name, imageFilename);
        bids = pBids;
        logger.info("Successfully called : Test constructor for " + this.getName() + " card.");
    }

    public Test(String name, String imageFilename)
    {
        this(name, imageFilename, 3);
        logger.info("Successfully called : Test constructor for " + this.getName() + " card.");
    }
}
