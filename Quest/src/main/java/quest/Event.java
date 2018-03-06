package quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Stack;

public abstract class Event extends StoryCard {

    private static final Logger logger = LogManager.getLogger(App.class);

    Event(String paramName, String paramImageFilename)
    {

        super(paramName, paramImageFilename);
        logger.info("Successfully called : Event constructor");

    }
    public abstract void applyEvent(ArrayList<Player> playersToAffect, Player activePlayer);

}

