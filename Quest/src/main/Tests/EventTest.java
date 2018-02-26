package quest;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.*;

public class EventTest{

    Event plague = new Event("Plague", "");

    @Test
    public void getName()
    {

        assertEquals("Plague", plague.getName());

    }



    @Test
    public void getImageFilename()
    {

        assertEquals("", plague.getImageFilename());


    }



}