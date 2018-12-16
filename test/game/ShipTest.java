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
//        TODO: Implement this test

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
    void calculateOxygenState() {

        assertEquals(ship.getOxygenLevel(), 100);
        ship.calculateState();
        assertEquals(ship.getOxygenLevel(), 100);

        ship.setOxygenLevel(0);
        ship.calculateState();
        assertTrue(ship.getOxygenLevel() > 0);


        ship.setOxygenLevel(99);
        ship.calculateState();
        assertEquals(ship.getOxygenLevel(), 100);
    }


    @Test
    void calculateShieldState() {

        assertEquals(ship.getShield(), 1);
        ship.calculateState();
        assertEquals(ship.getShield(), 1);

        ship.setShield(0);
        ship.calculateState();
        assertTrue(ship.getShield() > 0);

    }

    @Test
    void calculateShieldState_operatedBonus()
    {
        ship.setShield(0.14);
        ship.calculateState();
        double notOperated = ship.getShield();


        ship.addCrewmember(new Person(Ship.getShieldId()));

        ship.setShield(0.14);
        ship.calculateState();
        double operated = ship.getShield();


        assertTrue(operated > notOperated);
    }

    @Test
    void calculateShieldState_maxValue() {


        ship.calculateState();
        assertEquals(ship.getShield(), 1);


        ship.setShield(0.99);
        ship.calculateState();
        assertEquals(ship.getShield(), 1);
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
    void newShipIsNotDamaged() {
        for(int i = 0; i <= 4; i++)
        {
            assertFalse(ship.isDamaged(i));
        }
    }

    @Test
    void ShipIsDamaged() {
        ship.setSystems(1, 0.9);
        assertTrue(ship.isDamaged(1));
    }


    @Test
    void isOperated() {
        assertFalse(ship.isOperated(1));
        Person p = new Person(1);
        ship.addCrewmember(p);
        assertTrue(ship.isOperated(1));
        p.repair();
        assertFalse(ship.isOperated(1));

    }


    @Test
    void singlePresonRepairing()
    {
        Person p = new Person(1);
        ship.addCrewmember(p);
        ship.setSystems(1, 0);

        ship.repairBrokenSystems();

        assertTrue(p.isRepairing());
        assertEquals(Person.getRepairRate(), ship.getSystems(1), 0.01);
    }

    @Test
    void multiplePeopleRepairing() {
        Person p1 = new Person(1);
        Person p2 = new Person(1);
        ship.addCrewmember(p1);
        ship.addCrewmember(p2);
        ship.setSystems(1, 0);

        ship.repairBrokenSystems();

        assertTrue(p1.isRepairing());
        assertTrue(p2.isRepairing());
        assertEquals(2 * Person.getRepairRate(), ship.getSystems(1), 0.01);
    }

    @Test
    void stopRepairingIfCompleted()
    {
        Person p = new Person(1);
        ship.addCrewmember(p);
        ship.setSystems(1, 0.99);

        ship.repairBrokenSystems();

        assertEquals(1, ship.getSystems(1), 0.001);
        assertFalse(p.isRepairing());
    }






}