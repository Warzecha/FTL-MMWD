package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    Ship ship;
    Person p;

    @BeforeEach
    void setup()
    {
        ship = new Ship();
        p = new Person();
        ship.addCrewmember(p);
    }

    @Test
    void setShip() {
        p.setBoardedShip(ship);
        assertSame(ship, p.getBoardedShip());
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
        assertEquals(0, p.getRoomId());
    }

    @Test
    void getRoomId_whileMoving() {
        p.moveTo(3);
        assertEquals(0, p.getRoomId());
    }

    @Test
    void getRoomId_afterMoving() {
        p.moveTo(3);
        p.finalizeMovement();
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
        p.stopRepairing();
        assertFalse(p.isRepairing());
    }

    @Test
    void moveCrewmember() {
        p.moveTo(1);
        assertTrue(p.isMoving());
        assertEquals(0, p.getRoomId());
    }

    @Test
    void moveCrewmember_invalidRoomId() {


        Executable bigRoomId = () -> p.moveTo(1000);
        Executable negativeRoomId = () -> p.moveTo(-1);

        assertThrows(IllegalArgumentException.class, bigRoomId, "a message");
        assertThrows(IllegalArgumentException.class, negativeRoomId, "a message");

    }

    @Test
    void finalizeMovement() {
        p.moveTo(1);
        p.finalizeMovement();
        assertFalse(p.isMoving());
        assertEquals(1, p.getRoomId());
    }





}
