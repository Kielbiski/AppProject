package quest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class CardCollection
{
    ArrayList<Card> cards = new ArrayList<>();

    public CardCollection()
    {

    }

    public int getSize() { return cards.size(); }
    public Card getCard(int i) { return cards.get(i);}

    public void addCard(Card card)
    {
        cards.add(card);
    }

    public void addAllCardsFromCollection(CardCollection collection)
    {
        collection.cards.addAll(this.cards);
    }

    public void moveCardToCollection(Card card, CardCollection collection)
    {
        if(cards.remove(card))
        {
            collection.addCard(card);
        }
    }

    public void moveAllCardsToCollection(CardCollection collection)
    {
        collection.addAllCardsFromCollection(this);
        this.cards.clear();
    }

    public void shuffle()
    {
        Collections.shuffle(cards);
    }

    public Card drawCard()
    {
        if(cards.size() > 0)
        {
            return cards.remove(cards.size() - 1);
        }
        return null;
    }

    public CardCollection drawCards(int numCards)
    {
        CardCollection cardsDrawn = new CardCollection();
        for(int i = 0; i < numCards && cards.size() > 0; ++i)
        {
            cardsDrawn.addCard(cards.remove(cards.size() - 1));
        }
        return cardsDrawn;
    }
}
