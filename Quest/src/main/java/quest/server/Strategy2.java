package quest.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import quest.client.App;

class Strategy2 extends AbstractAI {

    private static final Logger logger = LogManager.getLogger(App.class);

    Strategy2(String paramName)
    {
        super(paramName);
        this.strategy ="Strategy 2 was used";
        this.TournamentAnswer = new DoIParticipateInTournamentStrategy2();
        this.nextBid= new NextBidStrategy2();
        this.quest= new DoIParticipateInQuestStrategy2();
        this.sponsorQuest = new DoISponsorAQuestStrategy2();
        logger.info("Successfully called : AI Strategy 2 constructor for"+this.getPlayerName()+".");
    }

}
