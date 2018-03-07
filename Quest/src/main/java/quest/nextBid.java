package quest;

import java.util.*;

public abstract class nextBid {


    public  abstract ArrayList<AdventureCard> nextBid (ArrayList<AdventureCard> paramCardList);

    public abstract ArrayList<AdventureCard> discardAfterWinningTest(ArrayList<AdventureCard> paramCardList );

    public ArrayList<AdventureCard>  foeList (ArrayList<AdventureCard> paramCard, int paramPoints)
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
