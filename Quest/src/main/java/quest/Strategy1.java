package quest;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class Strategy1 extends AbstractAI {

    private static final Logger logger = LogManager.getLogger(App.class);

    Strategy1(String paramName)
    {

        this.playerName = paramName ;
        this.shields = 0;
        this.battlePoints =0;
        this.currentBid = 0;
        this.playerRank = Rank.SQUIRE;
        this.strategy ="Strategy 1 is used";
        this.TournamentAnswer = new DoIParticipateInTournamentStrategy1();
        this.nextBid= new nextBidSt1();
        this.quest= new DoIParticipateInQuestStrategy1();

        logger.info("Calling strategy1 AI constructor for: "+paramName+".");
    }







}
