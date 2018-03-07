package quest;

import java.util.ArrayList;

public class nextBidSt2 extends nextBid{

    nextBidSt2() {

    }


    public ArrayList<AdventureCard> nextBid (ArrayList<AdventureCard> paramCardList){


        ArrayList<AdventureCard> tempCard = new ArrayList<>();
        tempCard= foeList(paramCardList, 25);
        return tempCard;

    }

    public ArrayList<AdventureCard> discardAfterWinningTest (ArrayList<AdventureCard> paramCardList ){

        return this.nextBid(paramCardList);
    }



}
