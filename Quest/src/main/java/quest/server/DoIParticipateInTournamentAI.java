package quest.server;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class DoIParticipateInTournamentAI {

    public abstract boolean doIParticipateInTournament(ArrayList<Player> paramPlayerList, int paramShields);

    ArrayList<AdventureCard> AlliesAndWeapons(ArrayList<AdventureCard> paramCard){

        ArrayList<AdventureCard> tempCard = new ArrayList<>() ;
        for(AdventureCard card : paramCard)
        {
            if((card instanceof  Ally) || (card instanceof  Weapon))
            {
                tempCard.add(card);
            }
        }

        return new ArrayList<>(new HashSet<>(tempCard));
    }

    public abstract ArrayList<AdventureCard> whatIPlay (ArrayList<AdventureCard> paramCard,ArrayList <Player> paramPlayerList,int paramShields );
}
