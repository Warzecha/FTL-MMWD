package game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {

    @Test
    void calculateEvasion() {

        Ship ship = new Ship();


        assertEquals(0.1 * ship.getEngines(), ship.calculateEvasion(), 0.05);


        ship.addCrewmember(new Person(Ship.getEngineId()));
        assertEquals(0.1 * ship.getEngines() + 0.1, ship.calculateEvasion(), 0.05);


        ship.addCrewmember(new Person(Ship.getEngineId()));
        assertEquals(0.1 * ship.getEngines() + 0.1, ship.calculateEvasion(), 0.05);


        ship = new Ship();
        ship.setSystems(Ship.getEngineId(), 0);
        assertEquals(0, ship.calculateEvasion(), 0.05);


        ship = new Ship();
        ship.setSystems(Ship.getSteeringId(), 0);
        assertEquals(0, ship.calculateEvasion(), 0.05);

    }

    @Test
    void dealDamage() {
    }

    @Test
    void receiveDamage() {
    }

    @Test
    void canShoot() {
    }

    @Test
    void rechargeWeapones() {
    }

    @Test
    void calculateState() {
    }

    @Test
    void addCrewmember() {

        Ship ship = new Ship();

        assertEquals(0, ship.getCrewCount());

        ship.addCrewmember(new Person(3));

        assertEquals(1, ship.getCrewCount());

    }
}