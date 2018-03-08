package quest;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class NextBidStrategy2Test {

    private ArrayList<AdventureCard> adCard = new ArrayList<>();
    private ArrayList<AdventureCard> temp = new ArrayList<>();

    @Test
    public void nextBid() {

        BlackKnight KnightblackKnight=new BlackKnight();
        BlackKnight blackKnight2=new BlackKnight();
        EvilKnight evilKnight =new EvilKnight();
        Boar boar = new Boar();

        adCard.add(KnightblackKnight);
        adCard.add(blackKnight2);
        adCard.add(evilKnight);
        adCard.add(boar);

        temp.add(evilKnight);
        temp.add(boar);


        NextBid next = new NextBidStrategy2();

        assertEquals(temp.size(), next.nextBid(adCard).size());

        assertEquals(temp, next.nextBid(adCard));
    }

    @Test
    public void discardAfterWinningTest() {

        nextBid();
    }
}