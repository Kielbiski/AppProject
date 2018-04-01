package quest.server;

import java.util.ArrayList;

public class NextBidStrategy2 extends NextBid{

    NextBidStrategy2() {

    }


    public ArrayList<AdventureCard> nextBid (ArrayList<AdventureCard> paramCardList){

        return foeList(paramCardList, 25);

    }

    public ArrayList<AdventureCard> discardAfterWinningTest (ArrayList<AdventureCard> paramCardList ){

        return this.nextBid(paramCardList);
    }



}
