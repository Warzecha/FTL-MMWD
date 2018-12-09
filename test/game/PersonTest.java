package game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    @Test
    void breathe() {

        Person p = new Person(3);
        int startHealth = 100;

        p.breathe(0);

        assertEquals(startHealth - Person.getSuffocationRate(), p.getHealthPoints());


    }

    @Test
    void receiveDamage() {

        Person p = new Person(3);
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
        int room = 3;
        Person p = new Person(room);
        assertEquals(room, p.getRoomId());
    }

    @Test
    void isRepairing() {
    }

    @Test
    void startRepairing() {
    }


}
