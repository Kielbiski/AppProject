package quest;

import java.util.*;
import java.util.Comparator;

public class DoIParticipateInQuestStrategy2 extends DoIParticipateInQuest{

    public  boolean doIParticipateInQuest(ArrayList<AdventureCard> paramCard, int paramNumStage){

        lastStagePoints=0;

        ArrayList<AdventureCard> foeList;

        foeList = foeList(paramCard, 25);

        if (foeList.size() > 2 ) {

            int totalStagePoint = 0;
            for (AdventureCard card : paramCard) {
                if ((card instanceof Ally) || (card instanceof Weapon)) {

                    totalStagePoint += card.getBattlePoints();

                }

            }
            return (paramNumStage == 3) && (totalStagePoint >= 60) || (paramNumStage == 2) && (totalStagePoint >= 30);

        }

        return false;

    }


    public  ArrayList<AdventureCard> playStage (ArrayList<AdventureCard> paramCard){

        ArrayList<AdventureCard> cardPlaying = new ArrayList<>();
        int totalPoint= 0;
        paramCard.sort(Comparator.comparing(AdventureCard::getBattlePoints));
        for(AdventureCard card : paramCard)
        {
            if ((card instanceof Ally) ||  (card instanceof Weapon))
            {
                totalPoint+= card.battlePoints;
                cardPlaying.add(card);
                if(totalPoint>(lastStagePoints+10))
                {
                    return cardPlaying;
                }


            }

        }

        return cardPlaying;
        
    }




}
