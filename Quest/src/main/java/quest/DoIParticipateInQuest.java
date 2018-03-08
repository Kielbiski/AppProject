package quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public abstract class DoIParticipateInQuest {

    private static final Logger logger = LogManager.getLogger(App.class);

    int lastStagePoints;

    public abstract boolean doIParticipateInQuest(ArrayList<AdventureCard> paramCard, int paramNumStage);

    public abstract ArrayList<AdventureCard> playStage (ArrayList<AdventureCard> paramCard);

    ArrayList<AdventureCard>  foeList (ArrayList<AdventureCard> paramCard, int paramPoints)
    {
        logger.info("Create a list of foes only");
        ArrayList<AdventureCard> foeList= new ArrayList<>();
        for(AdventureCard card : paramCard)
        {
            if ((card instanceof Foe) && (card.getBattlePoints() < paramPoints))
            {
                foeList.add(card);
            }
        }

        return  foeList;
    }


    public  ArrayList<AdventureCard> lastStage (ArrayList<AdventureCard> paramCard){

        ArrayList<AdventureCard> cardPlaying = new ArrayList<>();
        for(AdventureCard card : paramCard)
        {
            if ((card instanceof Ally) ||  (card instanceof Weapon))
            {
                cardPlaying.add(card);
            }

        }

        return cardPlaying;
    }


}
