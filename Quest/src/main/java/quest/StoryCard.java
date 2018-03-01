package quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StoryCard extends Card
{

    private static final Logger logger = LogManager.getLogger(App.class);

    StoryCard(String name, String imageFilename)
    {
        super(name, imageFilename);
        logger.info("Successfully called : StoryCard constructor for " + this.getName() + " card.");

    }

}
