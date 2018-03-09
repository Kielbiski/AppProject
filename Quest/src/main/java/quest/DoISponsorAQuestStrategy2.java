package quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

public class DoISponsorAQuestStrategy2 extends DoISponsorAQuest {

    private static final Logger logger = LogManager.getLogger(App.class);

    public  ArrayList<AdventureCard> lastStage (ArrayList<AdventureCard> paramCard)
    {

        ArrayList<AdventureCard> cardPlaying = new ArrayList<>();
        int totalPoint= 0;
        int foecount =0;
        for(AdventureCard card : paramCard)
        {
            if (((card instanceof Foe)&&foecount<1) ||  (card instanceof Weapon) ){
                if (totalPoint <= 40) {
                    totalPoint += card.getBattlePoints();
                    cardPlaying.add(card);
                    foecount++;
                } else {
                    break;
                }
            }

        }

        return new ArrayList<>(new HashSet<>(cardPlaying));


    }

    public  ArrayList<AdventureCard> firstStage (ArrayList<AdventureCard> paramCard){

        paramCard.sort(Comparator.comparing(AdventureCard::getBattlePoints));
        ArrayList<AdventureCard> cardPlaying = new ArrayList<>();
        for(AdventureCard card : paramCard)
        {
            if (card instanceof Foe){
                    cardPlaying.add(card);
                    return cardPlaying;

            }


        }

        return new ArrayList<>(new HashSet<>(cardPlaying));
    }

}
