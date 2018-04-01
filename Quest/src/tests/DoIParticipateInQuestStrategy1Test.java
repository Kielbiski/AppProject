package quest.server;

import org.junit.Test;
import quest.server.DoIParticipateInQuest;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DoIParticipateInQuestStrategy1Test {
    private ArrayList<AdventureCard> paramCard = new ArrayList<>();
    private ArrayList<AdventureCard> paramCard2 = new ArrayList<>();

    @Test
    public void doIParticipateInQuest() {

        SirGalahad sirGalahad = new SirGalahad();
        SirGalahad sirGalahad2 = new SirGalahad();
        SirGalahad sirGalahad3 = new SirGalahad();
        SirGalahad sirGalahad4 = new SirGalahad();
        SirGalahad sirGalahad5 = new SirGalahad();
        SirGalahad sirGalahad6 = new SirGalahad();

        BattleAx battleAx = new BattleAx();
        BattleAx battleAx2 = new BattleAx();
        BattleAx battleAx3 = new BattleAx();
        BattleAx battleAx4 = new BattleAx();
        BattleAx battleAx5 = new BattleAx();
        BattleAx battleAx6 = new BattleAx();
        Boar board = new Boar();
        Boar board2 = new Boar();

        paramCard.add(sirGalahad);
        paramCard.add(sirGalahad2);
        paramCard.add(sirGalahad3);
        paramCard.add(sirGalahad4);
        paramCard.add(battleAx);
        paramCard.add(battleAx2);
        paramCard.add(board);

        DoIParticipateInQuest quest1 = new DoIParticipateInQuestStrategy1();

        paramCard.add(sirGalahad5);
        paramCard.add(sirGalahad6);

        assertEquals(false, quest1.doIParticipateInQuest(paramCard, 3));

        paramCard.add(board2);

        assertEquals(true, quest1.doIParticipateInQuest(paramCard, 3));

        paramCard.remove(sirGalahad5);
        paramCard.remove(sirGalahad6);
        paramCard.add(battleAx3);

        assertEquals(true, quest1.doIParticipateInQuest(paramCard, 3));


        paramCard.add(battleAx6);
        paramCard.add(battleAx4);
        paramCard.add(battleAx5);

        assertEquals(true, quest1.doIParticipateInQuest(paramCard, 3));

        paramCard.remove(battleAx5);
        paramCard.remove(battleAx4);
        paramCard.remove(battleAx6);

        assertEquals(true, quest1.doIParticipateInQuest(paramCard, 3));

        paramCard.remove(sirGalahad4);
        paramCard.remove(sirGalahad3);

        assertEquals(false, quest1.doIParticipateInQuest(paramCard, 3));

    }

    @Test
    public void playStage() {

        SirGalahad sirGalahad = new SirGalahad();
        SirGalahad sirGalahad2 = new SirGalahad();
        BattleAx battleAx = new BattleAx();
        BattleAx battleAx2 = new BattleAx();
        Dagger dagger = new Dagger();


        paramCard.add(sirGalahad);
        paramCard.add(dagger);
        paramCard.add(battleAx);
        paramCard.add(battleAx2);


        paramCard2.add(sirGalahad);
        paramCard2.add(dagger);


        DoIParticipateInQuest quest1 = new DoIParticipateInQuestStrategy1();

        assertEquals(paramCard2,quest1.playStage(paramCard) );

        paramCard.add(sirGalahad2);
        paramCard2.add(sirGalahad2);
        paramCard2.remove(dagger);

        assertEquals(paramCard2,quest1.playStage(paramCard) );

        paramCard2.remove(sirGalahad2);
        paramCard.remove(sirGalahad2);
        paramCard.remove(battleAx);
        paramCard.remove(battleAx2);

        assertEquals(paramCard2,quest1.playStage(paramCard) );

    }

    @Test
    public void lastStage(){

        SirGalahad sirGalahad = new SirGalahad();
        BattleAx battleAx = new BattleAx();
        BattleAx battleAx2 = new BattleAx();
        Dagger dagger = new Dagger();


        paramCard.add(sirGalahad);
        paramCard.add(dagger);
        paramCard.add(battleAx);
        paramCard.add(battleAx2);

        paramCard2.add(sirGalahad);
        paramCard2.add(dagger);
        paramCard.add(battleAx);
        paramCard.add(battleAx2);


        DoIParticipateInQuest quest1 = new DoIParticipateInQuestStrategy1();

        assertEquals(paramCard2,quest1.playStage(paramCard) );

    }


}
