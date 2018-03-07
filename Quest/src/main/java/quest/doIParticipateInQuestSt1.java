package quest;

import java.util.*;
import java.util.Comparator;

public class doIParticipateInQuestSt1 extends doIParticipateInQuest {

    public  boolean doIParticipateInQuest(ArrayList<AdventureCard> paramCard, int paramNumStage){
        ArrayList<AdventureCard> weaponList= new ArrayList<>();
        ArrayList<AdventureCard> allyList= new ArrayList<>();
        ArrayList<AdventureCard> foeList= foeList(paramCard, 20);
        for(AdventureCard card : paramCard)
        {
            if (card instanceof Ally)
            {
                allyList.add(card);
            }

            if (card instanceof Weapon)
            {
                weaponList.add(card);
            }

        }

        if (((allyList.size()/paramNumStage)> 2 ) || ((weaponList.size()/paramNumStage)> 2 ) || ((( allyList.size()+ weaponList.size())/paramNumStage)>2 )){


            if(foeList.size()>2){
                return true;
            }

            return false;

        }

        return false;


    }


    public  ArrayList<AdventureCard> playStage (ArrayList<AdventureCard> paramCard){

        ArrayList<AdventureCard> cardPlayingWeapon = new ArrayList<>();
        ArrayList<AdventureCard> cardPlayingAllie = new ArrayList<>();
        ArrayList<AdventureCard> cardPlaying = new ArrayList<>();
        int totalPoint= 0;
        paramCard.sort(Comparator.comparing(object2 -> object2.getBattlePoints()));
        for(AdventureCard card : paramCard)
        {
            if (card instanceof Ally)
            {
                totalPoint+= card.battlePoints;
                cardPlayingAllie.add(card);
                if(cardPlayingAllie.size() >= 2)
                {
                    cardPlaying.add(cardPlayingAllie.get(0));
                    cardPlaying.add(cardPlayingAllie.get(1));
                    return cardPlaying;

                }


            }
            if (card instanceof Weapon){

                cardPlayingWeapon.add(card);
                if(cardPlayingWeapon.size() >= 2)
                {
                    cardPlaying.add(cardPlayingAllie.get(0));
                    cardPlaying.add(cardPlayingAllie.get(1));
                    return cardPlaying;

                }

            }
        }

        cardPlaying.clear();
        return cardPlaying;

    }


    public  ArrayList<AdventureCard> lastStage (ArrayList<AdventureCard> paramCard){

        ArrayList<AdventureCard> cardPlaying = new ArrayList<>();
        int totalPoint= 0;
        paramCard.sort(Comparator.comparing(object2 -> object2.getBattlePoints()));
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
