package quest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class Deck
{
    ArrayList<Card> cards = new ArrayList<>();

    public Deck()
    {

    }

    public void addCard(Card card)
    {
        cards.add(card);
    }

    public void shuffle()
    {
        Collections.shuffle(cards);
    }

    public Card drawCard()
    {
        if(cards.size() > 0)
        {
            return drawCards(1).get(0);
        }
        return null;
    }

    public ArrayList<Card> drawCards(int numCards)
    {
        ArrayList cardsDrawn = new ArrayList<>();
        for(int i = 0; i < numCards && cards.size() > 0; ++i)
        {
            cardsDrawn.add(cards.remove(cards.size() - 1));
        }
        return cardsDrawn;
    }

    public void fillWithAdventureCards()
    {

    }

    public void fillWithStoryCards()
    {

    }
}
