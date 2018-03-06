package quest;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class Strategy1 extends AbstractAI {



    Strategy1(){

        this.strategy=1;
        this.typeStrategy = "Strategy 1";

    }

    public boolean doIParticipateInTournament(ArrayList<Player> paramPlayerList, int paramShields)
    {
        ArrayList<Player> teamPlayer = paramPlayerList;

        for(Player player : teamPlayer) {
            if (paramShields >= player.getRequiredShieldsForNextRank()) {
                return true;
            }
        }

        return false;
    }


    public boolean doIParticipateInQuest(Player paramPlayer, int paramNumStage)
    {
        ArrayList<AdventureCard> weaponList= new ArrayList<>();
        ArrayList<AdventureCard> allyList= new ArrayList<>();
        ArrayList<AdventureCard> foeList;
        for(AdventureCard card : paramPlayer.getCardsInHand())
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

        if (((allyList.size()/2)> 2 ) || ((weaponList.size()/2)> 2 )){

            foeList = getListCard(paramPlayer);

            if(foeList.size()>2){
                return true;
            }

            return false;

        }

        return false;
    }


    public ArrayList<AdventureCard> discardAfterWinningTest (Player paramPlayer, int paramHighestBid)
    {
        return this.nextBid(paramPlayer, paramHighestBid);
    }
}
