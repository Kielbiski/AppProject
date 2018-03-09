package quest;

import org.junit.Test;

import java.awt.event.AdjustmentEvent;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class FoeStageTest {

    private  Player jay = new Player("Jay");
    private Player tom = new Player("Tom");
    private Player jeremy = new Player("Jeremy");
    private Player robert = new Player("Robert");
    private ArrayList<Player> participatingPlayers = new ArrayList<>();
    private ArrayList<AdventureCard> adCard = new ArrayList<>();

    @Test
    public void getTotalBattlePoints()
    {
        participatingPlayers.add(jay);
        participatingPlayers.add(tom);
        participatingPlayers.add(jeremy);
        participatingPlayers.add(robert);


        BlackKnight KnightblackKnight =new BlackKnight();
        BlackKnight blackKnight2=new BlackKnight();
        EvilKnight evilKnight =new EvilKnight();
        Boar boar = new Boar();

        adCard.add(KnightblackKnight);
        adCard.add(blackKnight2);
        adCard.add(evilKnight);
        adCard.add(boar);

        FoeStage foeStage = new FoeStage (adCard, participatingPlayers) ;


        assertEquals(75, foeStage.getTotalBattlePoints());


    }


    @Test
    public void getParticipatingPlayers()
    {


        participatingPlayers.add(jay);
        participatingPlayers.add(tom);
        participatingPlayers.add(jeremy);
        participatingPlayers.add(robert);


        BlackKnight KnightblackKnight=new BlackKnight();
        BlackKnight blackKnight2=new BlackKnight();
        EvilKnight evilKnight =new EvilKnight();
        Boar boar = new Boar();

        ArrayList<AdventureCard> adCard = new ArrayList<>();
        adCard.add(KnightblackKnight);
        adCard.add(blackKnight2);
        adCard.add(evilKnight);
        adCard.add(boar);


        FoeStage foeStage = new FoeStage (adCard, participatingPlayers) ;


        assertEquals(participatingPlayers, foeStage.getParticipatingPlayers());

        participatingPlayers.remove(robert);

        foeStage.setParticipatingPlayers(participatingPlayers);

        assertEquals(participatingPlayers, foeStage.getParticipatingPlayers());


    }
}