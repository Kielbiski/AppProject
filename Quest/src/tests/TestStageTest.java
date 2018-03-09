package quest;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestStageTest {

    private Player jay = new Player("Jay");
    private Player tom = new Player("Tom");
    private Player Jeremy = new Player("Jeremy");
    private Player Robert = new Player("Robert");

    private ArrayList<Player> participatingPlayers = new ArrayList<>();


    @Test
    public void getTestWinner()
    {

        ArrayList<Player> participatingPlayers = new ArrayList<>();

        jay.setCurrentBid(4);
        tom.setCurrentBid(3);
        Jeremy.setCurrentBid(2);
        Robert.setCurrentBid(1);

        participatingPlayers.add(jay);
        participatingPlayers.add(tom);
        participatingPlayers.add(Jeremy);
        participatingPlayers.add(Robert);

        AdventureCard testCard = new AdventureCard("Test of temptation", "");

        TestStage testStage = new TestStage (testCard,participatingPlayers );
//
//        assertEquals(jay, testStage.getTestWinner());

    }
}