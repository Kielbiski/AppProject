package quest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class CardCollection<T extends Card>
{
    ArrayList<T> cards = new ArrayList<>();

    public CardCollection(){

    }

    public int getSize() { 
        return cards.size(); 
    }
        
        
    public T getCard(int i) { 
        return cards.get(i);
    }

    public void addCard(T card){
        cards.add(card);
    }

    public void removeCard (T card){
        cards.remove(card);
    }

    public void addAllCardsFromCollection(CardCollection<T> collection){
        collection.cards.addAll(this.cards);
    }

    public void moveCardToCollection(T card, CardCollection<T> collection){
        if(cards.remove(card))
        {
            collection.addCard(card);
        }
    }

    public void moveAllCardsToCollection(CardCollection<T> collection){
        collection.addAllCardsFromCollection(this);
        this.cards.clear();
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public T drawCard(){
        if(cards.size() > 0){
            return cards.remove(cards.size() - 1);
        }
        return null;
    }

    public CardCollection<T> drawCards(int numCards){
        CardCollection<T> cardsDrawn = new CardCollection<>();
        for(int i = 0; i < numCards && cards.size() > 0; ++i){
            cardsDrawn.addCard(cards.remove(cards.size() - 1));
        }
        return cardsDrawn;
    }
}
