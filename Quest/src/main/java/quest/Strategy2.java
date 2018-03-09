package quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.*;

public class Strategy2 extends AbstractAI {

    private static final Logger logger = LogManager.getLogger(App.class);

    Strategy2(String paramName)
    {
        super(paramName);
        this.strategy ="Strategy 2 was used";
        this.TournamentAnswer = new DoIParticipateInTournamentStrategy2();
        this.nextBid= new NextBidStrategy2();
        this.quest= new DoIParticipateInQuestStrategy2();
        logger.info("Successfully called : AI Strategy 2 constructor for"+this.getPlayerName()+".");
    }
    
}
