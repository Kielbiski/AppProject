package quest;

import java.util.ArrayList;

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

    public Card drawCard()
    {
        return cards.remove(cards.size() - 1);
    }
}
