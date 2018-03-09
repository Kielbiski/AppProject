package quest;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DoIParticipateInTournamentStrategy2Test {
    Player jay = new Player("Jay");
    Player tom = new Player("Tom");
    Player Jimmy = new Player("Jimmy");
    Player John = new Player("John");

    Player Mike = new Player("Mike");
    Player Mike2 = new Player("Mike2");
    Player Mike3 = new Player("Mike3");
    Player Mike4 = new Player("Mike4");
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

        DoIParticipateInTournamentAI tournament =new DoIParticipateInTournamentStrategy2();

        assertEquals(true, tournament.doIParticipateInTournament(playerList, 2));


    }




    @Test
    public void whatIPlay() {


        BattleAx battleAx = new BattleAx();
        SirGalahad sirGalahad = new SirGalahad();
        SirGawain sirGawain = new SirGawain();
        Horse horse = new Horse();
        Dagger dagger = new Dagger();
        Boar boar = new Boar();


        jay.addCardToHand(battleAx);
        jay.addCardToHand(sirGalahad);
        jay.addCardToHand(sirGawain);
        jay.addCardToHand(horse);
        jay.addCardToHand(dagger);
        jay.addCardToHand(boar);

        playerList.add(John);
        playerList.add(tom);
        playerList.add(Jimmy);

        paramCard3.add(battleAx);
        paramCard3.add(sirGalahad);
        paramCard3.add(sirGawain);
        paramCard3.add(horse);

        DoIParticipateInTournamentAI tournament =new DoIParticipateInTournamentStrategy2();
        //assertEquals(paramCard3, tournament.whatIPlay(jay.getCardsInHand(), playerList,4));

    }




}