package game;

import game.exception.NoSuchRoomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    Ship ship;
    Person p;

    @BeforeEach
    void setup() {
        ship = new Ship(null);
        p = new Person();
        ship.addCrewmember(p);
    }

    @Test
    void setShip() {
        p.setBoardedShip(ship);
        assertSame(ship, p.getBoardedShip());
    }

    @Test
    void breatheDoesNotChaneHealthWhenOxygenIsSufficient() {
        int startHealth = 100;
        p.breathe(50);
        assertEquals(startHealth, p.getHealthPoints());
    }

    @Test
    void breatheDecresesHealthWhenOxygenTooLow() {
        int startHealth = 100;
        p.breathe(0);
        assertEquals(startHealth - Person.getSuffocationRate(), p.getHealthPoints());
    }

    @Test
    void receiveDamageDecreasesHealth() {
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
    void getRoomIdReturnsZeroWhenMoving() {
        try {
            p.moveTo(3);
        } catch (NoSuchRoomException e) {
            e.printStackTrace();
            fail();
        }
        assertEquals(0, p.getRoomId());
    }

    @Test
    void getRoomIdReturnsTargetRoomAfterMoving() throws NoSuchRoomException {
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
    void setRoomWithInvalidRoomIdThrowsException() {
        Executable bigRoomId = () -> p.setRoomId(1000);
        Executable negativeRoomId = () -> p.setRoomId(-1);

        assertThrows(NoSuchRoomException.class, bigRoomId);
        assertThrows(NoSuchRoomException.class, negativeRoomId);
    }

    @Test
    void moveCrewmember() throws NoSuchRoomException {
        p.moveTo(1);
        assertTrue(p.isMoving());
        assertEquals(0, p.getRoomId());
    }

    @Test
    void moveCrewmemberToNonexistentRoomIdThrowsException() {
        Executable bigRoomId = () -> p.moveTo(1000);
        assertThrows(NoSuchRoomException.class, bigRoomId);
    }

    @Test
    void moveCrewmemberToNegativeRoomIdThrowsException() {
        Executable negativeRoomId = () -> p.moveTo(-1);
        assertThrows(NoSuchRoomException.class, negativeRoomId);
    }

    @Test
    void finalizeMovement() throws NoSuchRoomException {
        p.moveTo(1);
        assertTrue(p.isMoving());
        p.finalizeMovement();
        assertFalse(p.isMoving());
        assertEquals(1, p.getRoomId());
    }





}
