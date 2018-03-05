package quest;

import java.util.ArrayList;
import java.util.*;

public class Strategy2 extends AbstractAI {


    Strategy2(){

        this.strategy=2;
        this.typeStrategy = "Strategy 2";
    }


    public boolean doIParticipateInTournament(ArrayList<Player> paramPlayerList, int paramShields)
    {

        return true;

    }


    public boolean doIParticipateInQuest(Player paramPlayer, int paramNumStage)
    {
        ArrayList<AdventureCard> foeList;

        foeList = getListCard(paramPlayer);

        if (foeList.size() > 2 ){

            int totalStagePoint=0;
            int numAlly = 0;
            for(AdventureCard card : paramPlayer.getCardsInHand()) {
                if ((card instanceof Ally) ||  (card instanceof Weapon))
                {
                    totalStagePoint += card.getBattlePoints();
                }
                if(card instanceof Ally){
                    numAlly++;
                }

                if((numAlly>=paramNumStage) && (paramNumStage==3) && (totalStagePoint >= 30)){
                    return true;
                }

                if((numAlly>=paramNumStage) && (paramNumStage==2) && (totalStagePoint >= 30)){
                    return true;
                }

                if((numAlly>=paramNumStage) && (paramNumStage==1) && (totalStagePoint >= 10)){
                    return true;
                }

                return false;

            }

        }

        return false;
    }


    public ArrayList<AdventureCard> discardAfterWinningTest (Player paramPlayer, int paramHighestBid)
    {
        return this.nextBid(paramPlayer, paramHighestBid);
    }
}
