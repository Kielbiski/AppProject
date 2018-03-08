package quest;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DoIParticipateInTournamentStrategy1Test {

    Player jay = new Player("Jay");
    Player tom = new Player("Tom");
    Player Jimmy = new Player("Jimmy");
    Player John = new Player("John");
    ArrayList<Player> playerList = new ArrayList<>();
    ArrayList<Player> playerList2 = new ArrayList<>();
    ArrayList<AdventureCard> paramCard3 = new ArrayList<>();
    ArrayList<AdventureCard> paramCard4 = new ArrayList<>();

    @Test
    public void doIParticipateInTournament() {
        jay.setShields(4);
        tom.setShields(1);
        Jimmy.setShields(3);
        John.setShields(1);

        playerList.add(John);
        playerList.add(tom);
        playerList.add(Jimmy);
        playerList.add(jay);


        DoIParticipateInTournamentAI tournament =new DoIParticipateInTournamentStrategy1();

        assertEquals(true, tournament.doIParticipateInTournament(playerList, 2));

        jay.setShields(1);
        tom.setShields(1);
        Jimmy.setShields(0);
        John.setShields(1);

        assertEquals(false, tournament.doIParticipateInTournament(playerList, 2));
    }

    @Test
    public void whatIPlay() {

        SirGalahad sirGalahad = new SirGalahad();
        BattleAx battleAx = new BattleAx();
        BattleAx battleAx2 = new BattleAx();
        Dagger dagger = new Dagger();

        jay.addCardToHand(sirGalahad);
        jay.addCardToHand(battleAx);
        jay.addCardToHand(battleAx2);
        jay.addCardToHand(dagger);

        DoIParticipateInTournamentAI tournament =new DoIParticipateInTournamentStrategy1();

        jay.addCardToHand(sirGalahad);
        jay.addCardToHand(battleAx);
        jay.addCardToHand(battleAx2);
        jay.addCardToHand(dagger);


        paramCard3.add(battleAx);
        paramCard3.add(battleAx2);
        paramCard3.add(dagger);

        jay.setShields(4);
        tom.setShields(1);
        Jimmy.setShields(3);
        John.setShields(1);


        playerList.add(John);
        playerList.add(tom);
        playerList.add(Jimmy);

        assertEquals(paramCard3, tournament.whatIPlay(jay.getCardsInHand(), playerList,4));

        Jimmy.setShields(2);
        John.setShields(1);
        jay.setShields(1);
        paramCard3.add(sirGalahad);

        playerList2.add(John);
        playerList2.add(jay);
        playerList2.add(Jimmy);

        tom.addCardToHand(sirGalahad);
        tom.addCardToHand(battleAx);
        tom.addCardToHand(battleAx2);
        tom.addCardToHand(dagger);

        paramCard4.add(sirGalahad);
        paramCard4.add(battleAx);
        paramCard4.add(battleAx2);
        paramCard4.add(dagger);

        //assertEquals(false, tournament.doIParticipateInTournament( playerList2,4));
        assertEquals(paramCard4, tournament.whatIPlay(tom.getCardsInHand(), playerList2,1));
    }


}