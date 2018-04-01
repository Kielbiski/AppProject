package quest.server;

import java.util.*;

public abstract class NextBid {


    public  abstract ArrayList<AdventureCard> nextBid (ArrayList<AdventureCard> paramCardList);

    public abstract ArrayList<AdventureCard> discardAfterWinningTest(ArrayList<AdventureCard> paramCardList );

    ArrayList<AdventureCard>  foeList (ArrayList<AdventureCard> paramCard, int paramPoints)
    {

        ArrayList<AdventureCard> foeList= new ArrayList<>();
        for(AdventureCard card : paramCard)
        {
            if ((card instanceof Foe) && (card.getBattlePoints() < paramPoints))
            {
                foeList.add(card);
            }
        }

        return  foeList;
    }

}
