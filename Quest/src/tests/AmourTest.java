package quest;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.*;

public class AmourTest{

    Amour amour = new Amour("Amour", "");

    @Test
    public void getName()
    {

        assertEquals("Amour", amour.getName());

    }



    @Test
    public void getImageFilename()
    {

        assertEquals("", amour.getImageFilename());


    }



    @Test
    public void getBattlePoints()
    {

        assertEquals(10, amour.getBattlePoints());


    }



    @Test
    public void getBids()
    {

        assertEquals(1, amour.getBids());


    }

}