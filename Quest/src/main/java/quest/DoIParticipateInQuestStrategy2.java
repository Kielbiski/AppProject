package quest;

import java.util.*;
import java.util.Comparator;

public class DoIParticipateInQuestStrategy2 extends DoIParticipateInQuest{

    DoIParticipateInQuestStrategy2(){
        lastStagePoints=0;
    }

    public  boolean doIParticipateInQuest(ArrayList<AdventureCard> paramCard, int paramNumStage){

        lastStagePoints=0;

        ArrayList<AdventureCard> foeList;

        foeList = foeList(paramCard, 25);

        if (foeList.size() >= 2 ) {

            int totalStagePoint = 0;
            for (AdventureCard card : paramCard) {
                if ((card instanceof Ally) || (card instanceof Weapon)) {

                    totalStagePoint += card.getBattlePoints();

                }

            }
            return (((paramNumStage == 3) && (totalStagePoint >= 60)) || ((paramNumStage == 2) && (totalStagePoint >= 30)));

        }

        return false;

    }


    public  ArrayList<AdventureCard> playStage (ArrayList<AdventureCard> paramCard){

        ArrayList<AdventureCard> cardPlaying = new ArrayList<>();
        ArrayList<AdventureCard> allyList = new ArrayList<>();
        ArrayList<AdventureCard> weaponList = new ArrayList<>();
        ArrayList<AdventureCard> amourList = new ArrayList<>();
        int totalPoint= 0;
        paramCard.sort(Comparator.comparing(AdventureCard::getBattlePoints));
        for(AdventureCard card : paramCard)
        {
            if (card instanceof Ally)
            {
                if (card instanceof Amour){

                    amourList.add(card);
                }
                else {
                    allyList.add(card);
                }
            }
            if (card instanceof Weapon){

                weaponList.add(card);
            }

        }
        if(amourList.size()!=0){

            cardPlaying.add(amourList.get(0));
            totalPoint+= amourList.get(0).getBattlePoints();
        }
        int counter = 0;
        int played =0;
        while((totalPoint<(lastStagePoints+10)) && (weaponList.size()>counter) && (allyList.size()>=1)){
            if((cardPlaying.size() < 2) && (played==0) ){

                cardPlaying.add(allyList.get(counter));
                totalPoint+= allyList.get(counter).getBattlePoints();
                played++;
            }
            else{
                cardPlaying.add(weaponList.get(counter));
                totalPoint+= weaponList.get(counter).getBattlePoints();
                counter++;
            }
        }

        lastStagePoints= totalPoint;
        return new ArrayList<>(new HashSet<>(cardPlaying));

    }



}
