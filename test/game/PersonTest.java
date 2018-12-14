package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    Person p;

    @BeforeEach
    void setup()
    {
        p = new Person(3);
    }

    @Test
    void breathe() {
        int startHealth = 100;
        p.breathe(0);
        assertEquals(startHealth - Person.getSuffocationRate(), p.getHealthPoints());
    }

    @Test
    void receiveDamage() {
        int health = 100;

        p.receiveDamage(1);
        health -= Person.getDamageFromShots();
        assertEquals(health, p.getHealthPoints());

        p.receiveDamage(2);
        health -= 2 * Person.getDamageFromShots();
        assertEquals(health, p.getHealthPoints());
    }

    @Test
    void getRoomId() {
        assertEquals(3, p.getRoomId());
    }


    @Test
    void startRepairing() {
        p.repair();
        assertTrue(p.isRepairing());
    }

    @Test
    void stopRepairing() {
        p.repair();
        assertTrue(p.isRepairing());

        p.stopRepairing();
        assertFalse(p.isRepairing());
    }


}
