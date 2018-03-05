package quest;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TournamentTest {

    private Player jay = new Player("Jay");
    private Player tom = new Player("Tom");
    private Player jeremy = new Player("Jeremy");
    private Player robert = new Player("Robert");

    private ArrayList<Player> participatingPlayers = new ArrayList<>();

    @Test
    public void checkTie()
    {

        participatingPlayers.add(jay);
        participatingPlayers.add(tom);
        participatingPlayers.add(jeremy);
        participatingPlayers.add(robert);

        Tournament tournament = new Tournament("Tournament", "", participatingPlayers);

        assertEquals(tournament.checkTie(), true);

        assertEquals(tournament.getRemainingPlayers(), participatingPlayers);


    }

    @Test
    public void SetRoundsPlayed()
    {

        participatingPlayers.add(jay);
        participatingPlayers.add(tom);
        participatingPlayers.add(jeremy);
        participatingPlayers.add(robert);

        Tournament tournament = new Tournament("Tournament", "", participatingPlayers);

        tournament.setRoundsPlayed(2);

        assertEquals(tournament.getRoundsPlayed(), 2);

        tournament.setRoundsPlayed(4);

        assertEquals(tournament.getRoundsPlayed(), 4);

    }

    @Test
    public void getRoundsPlayed()
    {

        participatingPlayers.add(jay);
        participatingPlayers.add(tom);
        participatingPlayers.add(jeremy);
        participatingPlayers.add(robert);

        Tournament tournament = new Tournament("Tournament", "", participatingPlayers);

        assertEquals(tournament.getRoundsPlayed(), 1);

        tournament.setRoundsPlayed(2);

        assertEquals(tournament.getRoundsPlayed(), 2);
    }

    @Test
    public void getRemainingPlayers()
    {
        participatingPlayers.add(jay);
        participatingPlayers.add(tom);
        participatingPlayers.add(jeremy);
        participatingPlayers.add(robert);

        Tournament tournament = new Tournament("Tournament", "", participatingPlayers);

        assertEquals(tournament.getRemainingPlayers(), participatingPlayers);

        tournament.removePlayer(jay );
        participatingPlayers.remove(jay );

        assertEquals(tournament.getRemainingPlayers(), participatingPlayers);

        tournament.removePlayer(tom );
        participatingPlayers.remove(tom );

        assertEquals(tournament.getRemainingPlayers(), participatingPlayers);

        tournament.removePlayer(jeremy );
        participatingPlayers.remove(jeremy );

        assertEquals(tournament.getRemainingPlayers(), participatingPlayers);

        tournament.removePlayer(robert );
        participatingPlayers.remove(robert );

        assertEquals(tournament.getRemainingPlayers(), participatingPlayers);

    }

    @Test
    public void getShields()
    {

        participatingPlayers.add(jay);
        participatingPlayers.add(tom);
        participatingPlayers.add(jeremy);
        participatingPlayers.add(robert);

        Tournament tournament = new Tournament("Tournament", "", participatingPlayers);

        assertEquals(tournament.getShields(), 4);

        participatingPlayers.remove(jeremy);
        participatingPlayers.remove(robert);

        Tournament tournament2 = new Tournament("Tournament", "", participatingPlayers);

        assertEquals(tournament2.getShields(), 2);

    }

    @Test
    public void getTournamentWinner()
    {

        jay.setShields(4);
        tom.setShields(3);
        jeremy.setShields(5);
        robert.setShields(9);

        participatingPlayers.add(jay);
        participatingPlayers.add(tom);
        participatingPlayers.add(jeremy);
        participatingPlayers.add(robert);

        Tournament tournament = new Tournament("Tournament", "", participatingPlayers);

        ArrayList<Player> participatingPlayers2 = tournament.getTournamentWinner();

        assertEquals( participatingPlayers2.get(0).getShields(), jay.getShields() );

        assertEquals( participatingPlayers2.get(1).getShields(), tom.getShields() );

        assertEquals( participatingPlayers2.get(2).getShields(), jeremy.getShields() );

        assertEquals( participatingPlayers2.get(3).getShields(), robert.getShields() );


        participatingPlayers.remove(tom );

        participatingPlayers.remove(robert);

        participatingPlayers.remove(jay );

        Tournament tournament2 = new Tournament("Tournament", "", participatingPlayers);

        assertEquals(tournament2.getTournamentWinner().get(0).getShields(), 10);
    }

    @Test
    public void removePlayer()
    {
        participatingPlayers.add(jay);
        participatingPlayers.add(tom);
        participatingPlayers.add(jeremy);
        participatingPlayers.add(robert);

        participatingPlayers.remove(jay);

        assertEquals(participatingPlayers.size(), 3);

        participatingPlayers.remove(tom);

        assertEquals(participatingPlayers.size(), 2);

    }
}