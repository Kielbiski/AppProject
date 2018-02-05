package quest;

import junit.framework.TestCase;

import static org.junit.Assert.*;

public class FoeTest extends TestCase{

    Foe Dragon = new Foe("Dragon", "", 50, 70, new Quest("Dragon", ""));

    public void testGetBattlePoints(){

        assertEquals(50, Dragon.getBattlePoints());

    }


    public void testApplyQuestEffect(){

        Quest slayDragon = new Quest("Dragon", "");
        Dragon.applyQuestEffect(slayDragon);
        assertEquals(70, Dragon.getBattlePoints());

    }

}