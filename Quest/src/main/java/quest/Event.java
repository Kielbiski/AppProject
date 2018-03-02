package quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Event extends StoryCard {

    private static final Logger logger = LogManager.getLogger(App.class);

    public Event(String paramName, String paramImageFilename)
    {

        super(paramName, paramImageFilename);
        logger.info("Successfully called : Event constructor");

    }

}

