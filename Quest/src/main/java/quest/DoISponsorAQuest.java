package quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public abstract class DoISponsorAQuest {

    private static final Logger logger = LogManager.getLogger(App.class);


    public abstract ArrayList<AdventureCard> lastStage (ArrayList<AdventureCard> paramCard);

    public boolean doISponsor(ArrayList<Player> paramPlayerList, ArrayList<AdventureCard> paramCard, int paramNumstage, int paramShields) {
        logger.info("Strategy 2 : Answering  do I sponsor quest?");

        ArrayList<Player> teamPlayer = paramPlayerList;
        ArrayList<AdventureCard> tempCard = cardList(paramCard);

        for (Player player : teamPlayer) {
            if (paramShields >= player.getRequiredShieldsForNextRank()) {

                logger.info("The answer is no.");
                return false;

            }
        }

        if (tempCard.size() >= paramNumstage) {
            logger.info("The answer is yes.");
            return true;
        }

        logger.info("The answer is no.");

        return false;
    }


    public ArrayList<AdventureCard> cardList(ArrayList<AdventureCard> paramCard) {

        ArrayList<AdventureCard> cardList = new ArrayList<>();
        for (AdventureCard card : paramCard) {
            if ((card instanceof Foe) || (card instanceof Test)) {
                cardList.add(card);
            }
        }

        return cardList;
    }




    public  AdventureCard midStage (ArrayList<AdventureCard> paramCard)
    {
        ArrayList<AdventureCard> cardList = new ArrayList<>();
        for(AdventureCard card : paramCard)
        {
            if (card instanceof Test)
            {
                return card;

            }
            if (card instanceof Foe)
            {
                cardList.add(card);

            }

        }

        return cardList.get(cardList.size());

    }


    public  abstract ArrayList<AdventureCard> firstStage (ArrayList<AdventureCard> paramCard);
}