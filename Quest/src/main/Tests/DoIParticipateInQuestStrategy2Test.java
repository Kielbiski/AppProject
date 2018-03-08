package quest;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DoIParticipateInQuestStrategy2Test {

    private ArrayList<AdventureCard> paramCard = new ArrayList<>();
    private ArrayList<AdventureCard> paramCard2 = new ArrayList<>();
    private ArrayList<AdventureCard> paramCard3 = new ArrayList<>();

    @Test
    public void doIParticipateInQuest() {

        SirGalahad sirGalahad = new SirGalahad();
        SirGalahad sirGalahad2 = new SirGalahad();
        SirGalahad sirGalahad3 = new SirGalahad();
        SirGalahad sirGalahad4 = new SirGalahad();

        Boar board = new Boar();
        Boar board2 = new Boar();

        paramCard.add(sirGalahad);
        paramCard.add(sirGalahad2);
        paramCard.add(sirGalahad3);
        paramCard.add(sirGalahad4);
        paramCard.add(board);


        DoIParticipateInQuest quest1 = new DoIParticipateInQuestStrategy2();

        assertEquals(false, quest1.doIParticipateInQuest(paramCard, 3));

        paramCard.add(board2);

        assertEquals(true, quest1.doIParticipateInQuest(paramCard, 3));

        paramCard.remove(sirGalahad3);
        paramCard.remove(sirGalahad4);

        assertEquals(false, quest1.doIParticipateInQuest(paramCard, 3));

        assertEquals(true, quest1.doIParticipateInQuest(paramCard, 2));

    }

    @Test
    public void playStage() {

        SirGalahad sirGalahad = new SirGalahad();
        SirGalahad sirGalahad2 = new SirGalahad();
        BattleAx battleAx = new BattleAx();
        BattleAx battleAx2 = new BattleAx();
        Dagger dagger = new Dagger();
        Amour amour = new Amour("Amour","");




        paramCard.add(sirGalahad);
        paramCard.add(dagger);
        paramCard.add(battleAx);
        paramCard.add(battleAx2);


        paramCard2.add(sirGalahad);


        DoIParticipateInQuest quest1 = new DoIParticipateInQuestStrategy2();

        assertEquals(paramCard2,quest1.playStage(paramCard) );

        paramCard2.add(dagger);
        paramCard2.add(battleAx);

        assertEquals(paramCard2,quest1.playStage(paramCard) );

        paramCard.add(amour);


        paramCard3.add(amour);
        paramCard3.add(sirGalahad);
        paramCard3.add(dagger);
        paramCard3.add(battleAx);
        assertEquals(paramCard3,quest1.playStage(paramCard) );



    }
}