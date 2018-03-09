package quest;

import junit.framework.TestCase;

import static org.junit.Assert.*;

public class WeaponTest extends TestCase{


    public void testWeaponTest() {

        Horse horse = new Horse();
        Lance lance = new Lance();
        Sword Sword = new Sword();
        Excalibur Excalibur = new Excalibur();
        Dagger Dagger = new Dagger();
        BattleAx BattleAx = new BattleAx();

        assertEquals(horse.getName(),"Horse");

        assertEquals(lance.getName(),"Lance");

        assertEquals(Sword.getName(),"Sword");

        assertEquals(Excalibur.getName(),"Excalibur");

        assertEquals(Dagger.getName(), "Dagger");

        assertEquals(BattleAx.getName(),"Battle-ax");

    }
}