package quest;


import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public abstract class AbstractAI {

    protected int strategy;
    protected String typeStrategy;

    public boolean doISponsorAQuest(ArrayList<Player> paramPlayerList, int paramShields , Player paramPlayer)
    {
        ArrayList<Player> temPlayer = paramPlayerList;
        temPlayer.remove(paramPlayer);

        for(Player player : temPlayer) {
            if (paramShields >= player.getRequiredShieldsForNextRank()) {
                return false;
            }
        }

        int needThreeToParticipate=0;
        Set<Integer> set = new TreeSet<>();

        for(AdventureCard card : paramPlayer.getCardsInHand()) {
            if (card instanceof Foe) {
                set.add(card.getBattlePoints());
            }
            if(card instanceof Test){
                needThreeToParticipate++;
            }
        }

        if((set.size()+needThreeToParticipate)>= 3){
            return true;
        }

        return false;

    }

    public abstract boolean doIParticipateInTournament(ArrayList<Player> paramPlayerList, int paramShields);

    public abstract boolean doIParticipateInQuest(Player paramPlayer, int paramNumStage);

    public ArrayList<AdventureCard> nextBid (Player paramPlayer, int paramHighestBid)
    {

        ArrayList<AdventureCard> foeList;

        foeList = getListCard(paramPlayer);

        if(foeList.size() > paramHighestBid)
        {

            return foeList;

        }

        foeList.clear();

        return foeList;

    }

    public abstract ArrayList<AdventureCard> discardAfterWinningTest (Player paramPlayer, int paramHighestBid);

    public ArrayList<AdventureCard> getListCard (Player paramPlayer)
    {
        int i;

        if(this.strategy== 1)
        {

            i = 20;
        }

        else
        {
            i = 25;
        }
        ArrayList<AdventureCard> foeList= new ArrayList<>();
        for(AdventureCard card : paramPlayer.getCardsInHand())
        {
            if ((card instanceof Foe) && (card.getBattlePoints() < i))
            {
                foeList.add(card);
            }
        }

        return foeList;

    }
}
