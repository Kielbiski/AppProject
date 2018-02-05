package quest;

import junit.framework.TestCase;
import quest.Tournament;

import java.util.ArrayList;

public class TournamentTest extends TestCase{

    ArrayList <Player> playerList;
    Tournament firstTournament;

    public void testGetShields(){

        playerList = new ArrayList<Player>();
        playerList.add(new Player("Kobe B Bryant"));
        playerList.add(new Player("Michael Jeffrey Jordan"));
        playerList.add(new Player("LeBron Raymone James"));
        playerList.add(new Player("Thomas Edward Patrick Brady Jr"));
        firstTournament = new Tournament("Death","",playerList);
        assertEquals(4,firstTournament.getShields());

    }

    public void testcheckTie(){

        testGetShields();
        assertEquals(true, firstTournament.checkTie());

    }

   public void testGetRoundsPlayed(){
        int i =25;
        testGetShields();

        for (Player player : playerList) {
            player.setCurrentPlayPoints(i);
        }

        firstTournament.tournamentPlay();
        assertEquals(1, firstTournament.getRoundsPlayed());

        for (Player player : playerList) {
           player.setCurrentPlayPoints(i++);
        }

        firstTournament.tournamentPlay();
        assertEquals(2, firstTournament.getRoundsPlayed());

    }

    public void testTournamentWinner(){

        testGetRoundsPlayed();
        ArrayList<Player> newPlayerList = firstTournament.tournamentWinner();
        assertSame(newPlayerList, firstTournament.tournamentWinner());

    }

}