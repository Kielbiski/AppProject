package quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.*;

public class Strategy2 extends AbstractAI {

    private static final Logger logger = LogManager.getLogger(App.class);

    Strategy2(String paramName)
    {
        logger.info("Calling strategy2 AI constructor for: "+paramName+".");
        this.playerName = paramName ;
        this.shields = 0;
        this.battlePoints =0;
        this.currentBid = 0;
        this.playerRank = Rank.SQUIRE;
        this.strategy ="Strategy 2 is used";
        this.TournamentAnswer = new DoIParticipateInTournamentStrategy2();
        this.nextBid= new nextBidSt2();
        this.quest= new DoIParticipateInQuestStrategy2();

    }







}
