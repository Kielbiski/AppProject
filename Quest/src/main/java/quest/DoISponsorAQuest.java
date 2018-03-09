package quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public abstract class DoISponsorAQuest {

    private static final Logger logger = LogManager.getLogger(App.class);


    public abstract ArrayList<AdventureCard> lastStage (ArrayList<AdventureCard> paramCard);

    public boolean doISponsor(ArrayList<Player> paramPlayerList, ArrayList<AdventureCard> paramCard, int paramNumstage, int paramShields) {
        logger.info("Strategy 2 : Answering  do I sponsor quest?");

        ArrayList<AdventureCard> tempCard = cardList(paramCard);

        for (Player player : paramPlayerList) {
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


    private ArrayList<AdventureCard> cardList(ArrayList<AdventureCard> paramCard) {

        ArrayList<AdventureCard> cardList = new ArrayList<>();
        for (AdventureCard card : paramCard) {
            if ((card instanceof Foe) || (card instanceof Test)) {
                cardList.add(card);
            }
        }

        return new ArrayList<AdventureCard>(new HashSet<AdventureCard>(cardList));
    }

    public  ArrayList<AdventureCard> midStage (ArrayList<AdventureCard> paramCard)
    {
        ArrayList<AdventureCard> cardList = new ArrayList<>();
        ArrayList<AdventureCard> cardListOfFoes = new ArrayList<>();
        for(AdventureCard card : paramCard)
        {
            if (card instanceof Test)
            {
                cardList.add(card);
                return cardList;

            }
            if (card instanceof Foe)
            {
                cardListOfFoes.add(card);
            }
        }
        cardListOfFoes.sort(Comparator.comparing(AdventureCard::getBattlePoints));
        Collections.reverse(cardList);
        cardList.add(cardListOfFoes.get(0));
        return cardList;
    }


    public  abstract ArrayList<AdventureCard> firstStage (ArrayList<AdventureCard> paramCard);
}