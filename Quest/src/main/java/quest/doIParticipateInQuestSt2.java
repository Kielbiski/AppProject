package quest;

import java.util.*;
import java.util.Comparator;

public class doIParticipateInQuestSt2 extends doIParticipateInQuest{

    public  boolean doIParticipateInQuest(ArrayList<AdventureCard> paramCard, int paramNumStage){

        lastStagePoints=0;

        ArrayList<AdventureCard> foeList;

        foeList = foeList(paramCard, 25);

        if (foeList.size() > 2 )
        {

            int totalStagePoint=0;
            int numAlly = 0;
            for(AdventureCard card : paramCard)
            {
                if ((card instanceof Ally) ||  (card instanceof Weapon))
                {

                    totalStagePoint += card.getBattlePoints();

                }
                if(card instanceof Ally)
                {

                    numAlly++;

                }

            }
            if((paramNumStage==3) && (totalStagePoint >= 60))
            {
                return true;
            }

            if((paramNumStage==2) && (totalStagePoint >= 30))
            {
                return true;
            }

        }

        return false;

    }


    public  ArrayList<AdventureCard> playStage (ArrayList<AdventureCard> paramCard){

        ArrayList<AdventureCard> cardPlaying = new ArrayList<>();
        int totalPoint= 0;
        paramCard.sort(Comparator.comparing(object2 -> object2.getBattlePoints()));
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


    public  ArrayList<AdventureCard> lastStage (ArrayList<AdventureCard> paramCard){

        ArrayList<AdventureCard> cardPlaying = new ArrayList<>();
        int totalPoint= 0;
        for(AdventureCard card : paramCard)
        {
            if ((card instanceof Ally) ||  (card instanceof Weapon))
            {
                totalPoint+= card.battlePoints;
                cardPlaying.add(card);


            }

        }

        return cardPlaying;


    }


}
