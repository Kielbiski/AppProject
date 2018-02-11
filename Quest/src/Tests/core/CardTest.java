package quest;

import junit.framework.TestCase;

import static org.junit.Assert.*;

public class CardTest extends TestCase {

    Card boar= new Card( "Boar", "");

    public void testGetName(){

        assertEquals("Boar", boar.getName());

    }

    public void testGetImageFilename(){

        assertEquals("", boar.getImageFilename());

    }




}