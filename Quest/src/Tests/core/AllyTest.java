package quest;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.*;

public class AllyTest{

    Ally sirLancelot = new Ally("Sir Lancelot", "", 15,0,25,0);
    Ally kingPellinor = new Ally("King Pellinor", "", 10,0,0,4);



    @Test
    public void getName()
    {

        assertEquals("Sir Lancelot", sirLancelot.getName());

        assertEquals("King Pellinor", kingPellinor.getName());

    }



    @Test
    public void getImageFilename()
    {

        assertEquals("", sirLancelot.getImageFilename());

        assertEquals("", kingPellinor.getImageFilename());

    }



    @Test
    public void getBattlePoints()
    {

        assertEquals(15, sirLancelot.getBattlePoints());

        assertEquals(10, kingPellinor.getBattlePoints());

    }



    @Test
    public void getBids()
    {

        assertEquals(0, sirLancelot.getBids());

        assertEquals(0, kingPellinor.getBids());

    }



    @Test
    public void getBonusBattlePoints()
    {

        assertEquals(0, kingPellinor.getBonusBattlePoints());

        assertEquals(25, sirLancelot.getBonusBattlePoints());

    }



    @Test
    public void getBonusBids()
    {

        assertEquals(0, sirLancelot.getBids());

        assertEquals(0, sirLancelot.getBonusBids());

        assertEquals(0, kingPellinor.getBids());

        assertEquals(4, kingPellinor.getBonusBids());


    }




}
