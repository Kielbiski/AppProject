package quest;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.*;

public class AllyTest extends TestCase {

    Ally maybeSirGalahad = new Ally ("Sir Galahad", "", 0);



    public void testSetSpecialBehaviour() {

        SpecialBehaviourSirGalahad sir = new SpecialBehaviourSirGalahad();
        maybeSirGalahad.setSpecialBehaviour(sir);
        maybeSirGalahad.applySpecialBehaviour();
        assertEquals(15, maybeSirGalahad.getBattlePoints());
        assertEquals(0, maybeSirGalahad.getBids());

    }

    public void testSetSpecialBehavivour2 () {

        SpecialBehaviourQueenGuinevere queen = new SpecialBehaviourQueenGuinevere();
        maybeSirGalahad.setSpecialBehaviour(queen);
        maybeSirGalahad.applySpecialBehaviour();
        assertEquals(0, maybeSirGalahad.getBattlePoints());
        assertEquals(4, maybeSirGalahad.getBids());

    }

    public void testGetBattlePoints() {

        assertEquals(0,  maybeSirGalahad.getBattlePoints());

    }

    public void testSetBattlePoints() {

        maybeSirGalahad.setBattlePoints(20);
        assertEquals(20, maybeSirGalahad.getBattlePoints());

    }

    public void testGetBids() {

        assertEquals(0, maybeSirGalahad.getBids());
    }

    public void testSetBids() {

        maybeSirGalahad.setBids(3);
        assertEquals(3, maybeSirGalahad.getBids());

    }

}