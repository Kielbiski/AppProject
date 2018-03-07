package quest;

import java.util.ArrayList;

public class nextBidSt1 extends nextBid {

    nextBidSt1() {

    }


    public ArrayList<AdventureCard> nextBid (ArrayList<AdventureCard> paramCardList){


        ArrayList<AdventureCard> tempCard = new ArrayList<>();
         tempCard= foeList(paramCardList, 20);
         return tempCard;

    }

    public ArrayList<AdventureCard> discardAfterWinningTest (ArrayList<AdventureCard> paramCardList ){

        return this.nextBid(paramCardList);
    }

}
