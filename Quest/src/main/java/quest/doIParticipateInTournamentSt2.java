package quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;

public class doIParticipateInTournamentSt2 extends doIParticipateInTournamentAI {

    private static final Logger logger = LogManager.getLogger(App.class);

    doIParticipateInTournamentSt2()
    {

        logger.info("Calling do doIParticipateInTournamentSt2 (strategy 2) constructor");

    }


    public boolean doIParticipateInTournament(ArrayList<Player> paramPlayerList, int paramShields)
    {

        logger.info("Strategy 2 : Answering to a tournament");
        logger.info("The answer is yes.");
        return true;

    }

    public ArrayList<AdventureCard> whatIPlay (ArrayList<AdventureCard> paramCard)
    {

        ArrayList<AdventureCard> tempCard = AllieAndWeapons(paramCard);
        tempCard.sort(Comparator.comparing(object2 -> object2.getBattlePoints()));
        int battlePoints = 0;
        int i =0;
        ArrayList<AdventureCard> tempCard2 = new ArrayList<>() ;
        while (battlePoints <= 50)
        {

            battlePoints= tempCard.get(i).getBattlePoints();
            tempCard2.add(tempCard.get(i));
            i++;

        }
        logger.info("Return strategy 2 cards to play for the tournament.");
        return tempCard2;
    }

}
