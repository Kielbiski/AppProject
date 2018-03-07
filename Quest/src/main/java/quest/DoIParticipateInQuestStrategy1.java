package quest;

import java.util.*;
import java.util.Comparator;

public class DoIParticipateInQuestStrategy1 extends DoIParticipateInQuest {

    public  boolean doIParticipateInQuest(ArrayList<AdventureCard> paramCard, int paramNumStage) {
        ArrayList<AdventureCard> weaponList = new ArrayList<>();
        ArrayList<AdventureCard> allyList = new ArrayList<>();
        ArrayList<AdventureCard> foeList = foeList(paramCard, 20);
        for (AdventureCard card : paramCard) {
            if (card instanceof Ally) {
                allyList.add(card);
            }

            if (card instanceof Weapon) {
                weaponList.add(card);
            }
        }
        return (((allyList.size() / paramNumStage) > 2) || ((weaponList.size() / paramNumStage) > 2) || (((allyList.size() + weaponList.size()) / paramNumStage) > 2)) && foeList.size() > 2;
    }


    public  ArrayList<AdventureCard> playStage (ArrayList<AdventureCard> paramCard){

        ArrayList<AdventureCard> cardPlayingWeapon = new ArrayList<>();
        ArrayList<AdventureCard> cardPlayingAllie = new ArrayList<>();
        ArrayList<AdventureCard> cardPlaying = new ArrayList<>();
        int totalPoint= 0;
        paramCard.sort(Comparator.comparing(AdventureCard::getBattlePoints));
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
        paramCard.sort(Comparator.comparing(AdventureCard::getBattlePoints));
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
