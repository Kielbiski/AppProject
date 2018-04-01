package quest.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import quest.client.App;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

public class DoIParticipateInTournamentStrategy2 extends DoIParticipateInTournamentAI {

    private static final Logger logger = LogManager.getLogger(App.class);

    DoIParticipateInTournamentStrategy2()
    {

        logger.info("Calling do doIParticipateInTournamentSt2 (strategy 2) constructor");

    }


    public boolean doIParticipateInTournament(ArrayList<Player> paramPlayerList, int paramShields)
    {

        logger.info("Strategy 2 : Answering to a tournament");
        logger.info("The answer is yes.");
        return true;

    }

    public ArrayList<AdventureCard> whatIPlay (ArrayList<AdventureCard> paramCard, ArrayList<Player> paramPlayerList, int paramInt)
    {
        ArrayList<AdventureCard> alliesAndWeapons = AlliesAndWeapons(paramCard);
        alliesAndWeapons.sort(Comparator.comparing(AdventureCard::getBattlePoints));
        int battlePoints;
        ArrayList<AdventureCard> cardsToPlay = new ArrayList<>() ;
        for(AdventureCard adventureCard : alliesAndWeapons){
            battlePoints= adventureCard.getBattlePoints();
            cardsToPlay.add(adventureCard);
            if(battlePoints >= 50){
                break;
            }
        }
        logger.info("Return strategy 2 cards to play for the tournament.");
        return new ArrayList<>(new HashSet<>(cardsToPlay));
    }

}
