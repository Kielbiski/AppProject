package quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import  java.util.*;

public class DoIParticipateInTournamentStrategy1 extends DoIParticipateInTournamentAI{

    private static final Logger logger = LogManager.getLogger(App.class);

    DoIParticipateInTournamentStrategy1()
    {

        logger.info("Calling do doIParticipateInTournamentSt1 (strategy 1) constructor. ");

    }


    public boolean doIParticipateInTournament(ArrayList <Player> paramPlayerList, int paramShields)
    {
        logger.info("Strategy 1 : Answering to a tournament");

        for(Player player : paramPlayerList)
        {
            if (paramShields >= player.getRequiredShieldsForNextRank())
            {

                logger.info("The answer is Yes.");
                return true;

            }
        }

        logger.info("The answer is no.");

        return false;
    }


    public ArrayList<AdventureCard>  whatIPlay (ArrayList<AdventureCard> paramCard)
    {

        logger.info("Return strategy 1 cards to play for the tournament.");
        return AlliesAndWeapons(paramCard);

    }

}
