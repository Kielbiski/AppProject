package quest;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.*;

public class FoeTest{

    Foe Saxons = new Foe("Saxon", "", 10, 25);
    Foe Boar = new Foe("Boar", "", 5, 15);


    @Test
    public void getName() {

        assertEquals("Saxon", Saxons.getName());

        assertEquals("Boar", Boar.getName());

    }



    @Test
    public void getImageFilename() {

        assertEquals("", Saxons.getImageFilename());

        assertEquals("", Boar.getImageFilename());

    }



    @Test
    public void getBattlePoints() {

        assertEquals(10, Saxons.getBattlePoints());

        assertEquals(5, Boar.getBattlePoints());

    }



    @Test
    public void getBonusBattlePoints() {

        assertEquals(25, Saxons.getBonusBattlePoints());

        assertEquals(15, Boar.getBonusBattlePoints());

    }





}