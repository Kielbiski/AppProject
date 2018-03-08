package quest;

import java.util.ArrayList;

public abstract class DoIParticipateInTournamentAI {

    public abstract boolean doIParticipateInTournament(ArrayList<Player> paramPlayerList, int paramShields);

    public abstract ArrayList<AdventureCard>  whatIPlay (ArrayList<AdventureCard> paramCard, ArrayList<Player> paramPlayerList, int i);

    ArrayList<AdventureCard> AlliesAndWeapons(ArrayList<AdventureCard> paramCard){

        ArrayList<AdventureCard> tempCard = new ArrayList<>() ;
        for(AdventureCard card : paramCard)
        {
            if((card instanceof  Ally) || (card instanceof  Weapon))
            {
                tempCard.add(card);
            }
        }

        return  tempCard;
    }

}
