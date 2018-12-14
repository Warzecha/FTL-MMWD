package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {

    Ship ship;


    @BeforeEach
    void setup()
    {
        ship = new Ship();
    }


    @Test
    void calculateEvasion() {




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
    void receiveDamageEnginesOperatedAndShieldsOn() {

        Random rng = new Random(10);
        ship.addCrewmember(new Person(Ship.getEngineId()));

        ArrayList shots = new ArrayList<>(Arrays.asList(1, 1));

        assertEquals(0, ship.receiveDamage(Ship.getWeaponId(), shots, rng));
        assertEquals(0, ship.receiveDamage(Ship.getWeaponId(), shots, rng));
    }

    @Test
    void receiveDamageEnginesOperatedAndShieldsOff() {

        Random rng = new Random(10);
        ship.addCrewmember(new Person(Ship.getEngineId()));
        ship.setShield(0);

        ArrayList shots = new ArrayList<>(Arrays.asList(1, 1));

        assertEquals(1, ship.receiveDamage(Ship.getWeaponId(), shots, rng));
        assertEquals(0, ship.receiveDamage(Ship.getWeaponId(), shots, rng));
    }

    @Test
    void receiveDamageEnginesNotOperatedAndShieldsOn() {

        Random rng = new Random(10);

        ArrayList shots = new ArrayList<>(Arrays.asList(1, 1));

        assertEquals(1, ship.receiveDamage(Ship.getWeaponId(), shots, rng));
        assertEquals(1, ship.receiveDamage(Ship.getWeaponId(), shots, rng));
    }

    @Test
    void receiveDamageEnginesNotOperatedAndShieldsOff() {

        Random rng = new Random(10);
        ship.setShield(0);

        ArrayList shots = new ArrayList<>(Arrays.asList(1, 1));

        assertEquals(2, ship.receiveDamage(Ship.getWeaponId(), shots, rng));
        assertEquals(1, ship.receiveDamage(Ship.getWeaponId(), shots, rng));
    }

    @Test
    void canShoot() {


        ship.setWeapones(0);
        assertFalse(ship.canShoot());

        ship.setWeapones(0.5);
        assertFalse(ship.canShoot());

        ship.setWeapones(1);
        assertTrue(ship.canShoot());
    }

    @Test
    void rechargeWeaponesNotOperated() {

        ship.rechargeWeapones();
        assertEquals(0.02, ship.getWeapones(), 0.0005);


        ship.setWeapones(0.99);
        ship.rechargeWeapones();
        assertEquals(1, ship.getWeapones(), 0.0005);

    }


    @Test
    void rechargeWeaponesOperated() {
        ship.addCrewmember(new Person(Ship.getWeaponId()));

        ship.rechargeWeapones();
        assertEquals(0.03, ship.getWeapones(), 0.0005);


        ship.setWeapones(0.975);
        ship.rechargeWeapones();
        assertEquals(1, ship.getWeapones(), 0.0005);

    }



    @Test
    void calculateState() {
    }

    @Test
    void addCrewmember() {



        assertEquals(0, ship.getCrewCount());

        ship.addCrewmember(new Person(3));

        assertEquals(1, ship.getCrewCount());
        ship.addCrewmember(new Person(4));
        ship.addCrewmember(new Person(1));
        assertEquals(3, ship.getCrewCount());

    }


    @Test
    void isOperated() {
        assertFalse(ship.isOperated(1));
        Person p = new Person(1);
        ship.addCrewmember(p);
        assertTrue(ship.isOperated(1));
        p.startRepairing();
        assertFalse(ship.isOperated(1));

    }


}