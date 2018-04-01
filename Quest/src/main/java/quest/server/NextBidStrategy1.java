package quest.server;

import java.util.ArrayList;

public class NextBidStrategy1 extends NextBid {

    NextBidStrategy1() {

    }


    public ArrayList<AdventureCard> nextBid (ArrayList<AdventureCard> paramCardList){
        return foeList(paramCardList, 20);
    }

    public ArrayList<AdventureCard> discardAfterWinningTest (ArrayList<AdventureCard> paramCardList ){

        return this.nextBid(paramCardList);
    }

}
