package quest;

import junit.framework.TestCase;
import quest.Player;

import java.util.ArrayList;

public class PlayerTest extends TestCase {

    Player playerOne = new Player("Kobe B Bryant");

    public void testGetPlayerName(){

        assertEquals("Kobe B Bryant",playerOne.getPlayerName());

    }

    public void testGetSetPlayerRank(){

        assertEquals("Squire",playerOne.getPlayerRank());
        playerOne.setPlayerRank("Knight");
        assertEquals("Knight",playerOne.getPlayerRank());

    }

    public void testGetSetPlayerShields(){

        assertEquals(0, playerOne.getShields());
        playerOne.setShields(10);
        assertEquals(10, playerOne.getShields());

    }

    public void testGetSetPlayerPoints(){

        assertEquals(0, playerOne.getCurrentPlayPoints());
        playerOne.setCurrentPlayPoints(10);
        assertEquals(10, playerOne.getCurrentPlayPoints());

    }

    public void testGetNumberCardhand(){

        playerOne.addCardOnHands(new Ally("King Arthur", "",10));
        assertEquals(1, playerOne.getNumCardHand());
        playerOne.removeCardOnHands(0);
        assertEquals(0, playerOne.getNumCardHand());

    }

    public void testAddRemoveCardHand(){

        assertEquals(0, playerOne.cardOnHands.size());
        playerOne.addCardOnHands(new Ally("King Arthur", "",10));
        assertEquals(1, playerOne.cardOnHands.size());
        playerOne.removeCardOnHands(0);
        assertEquals(0, playerOne.cardOnHands.size());

    }

    public void testAddRemoveCardOnTable(){

        assertEquals(0, playerOne.cardOnTable.size());
        playerOne.addCardOnTable(new Ally("King Arthur", "",10));
        assertEquals(1, playerOne.cardOnTable.size());
        playerOne.removeCardOnTable(0);
        assertEquals(0, playerOne.cardOnTable.size());

    }

    public void testAddRemoveCardPlaying(){

        assertEquals(0, playerOne.cardPlaying.size());
        playerOne.addCardPlaying(new Ally("King Arthur", "",10));
        assertEquals(1, playerOne.cardPlaying.size());
        playerOne.removeCardPlaying(0);
        assertEquals(0, playerOne.cardPlaying.size());

    }


}
