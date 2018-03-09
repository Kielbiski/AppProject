package quest;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

class Strategy1 extends AbstractAI {

    private static final Logger logger = LogManager.getLogger(App.class);

    Strategy1(String paramName)
    {
        super(paramName);
        this.strategy ="Strategy 1 was used";
        this.TournamentAnswer = new DoIParticipateInTournamentStrategy1();
        this.nextBid= new NextBidStrategy1();
        this.quest= new DoIParticipateInQuestStrategy1();

        logger.info("Calling strategy1 AI constructor for: "+paramName+".");
    }
}
