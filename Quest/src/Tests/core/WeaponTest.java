package quest;


import junit.framework.TestCase;
import quest.Weapon;

import static org.junit.Assert.*;

public class WeaponTest extends TestCase {

    Weapon dagger = new Weapon("dagger","", 5);

    public void testGetDamage(){

        assertEquals(5, dagger.getDamage());

    }

}