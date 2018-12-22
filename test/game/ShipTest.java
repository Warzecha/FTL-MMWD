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
    }

    @Test
    void calculateEvasionIsGreatedWhenOerated() {
        ship.addCrewmember(new Person(), Ship.getEngineId());
        assertEquals(0.1 * ship.getEngines() + 0.1, ship.calculateEvasion(), 0.05);
    }

    @Test
    void calculateEvasionIsNotAffectedWhenOperatedByMultiplePeople() {
        ship.addCrewmember(new Person(), Ship.getEngineId());
        ship.addCrewmember(new Person(), Ship.getEngineId());
        assertEquals(0.1 * ship.getEngines() + 0.1, ship.calculateEvasion(), 0.05);
    }

    @Test
    void calculateEvasionReturnsZeroWhenEnginesAreDamaged() {
        ship.setSystems(Ship.getEngineId(), 0);
        assertEquals(0, ship.calculateEvasion(), 0.05);
    }

    @Test
    void calculateEvasionReturnsZeroWhenSteeringIsDamaged() {
        ship.setSystems(Ship.getSteeringId(), 0);
        assertEquals(0, ship.calculateEvasion(), 0.05);
    }


    @Test
    void dealDamageDischargesWeapones() {
        Ship enemy = new Ship();
        Random rng = new Random(10);
        ship.setWeapones(1);

        ship.dealDamage(enemy, 0, rng);
        assertEquals(0, ship.getWeapones());

    }

    @Test
    void receiveDamageWhenEnginesAreOperatedAndShieldsAreOn() {

        Random rng = new Random(10);
        ship.addCrewmember(new Person(), Ship.getEngineId());

        ArrayList shots = new ArrayList<>(Arrays.asList(1, 1));

        assertEquals(0, ship.receiveDamage(Ship.getWeaponId(), shots, rng));
        assertEquals(0, ship.receiveDamage(Ship.getWeaponId(), shots, rng));
    }

    @Test
    void receiveDamageWhenEnginesAreOperatedAndShieldsAreOff() {

        Random rng = new Random(10);
        ship.addCrewmember(new Person(), Ship.getEngineId());
        ship.setShield(0);

        ArrayList shots = new ArrayList<>(Arrays.asList(1, 1));

        assertEquals(1, ship.receiveDamage(Ship.getWeaponId(), shots, rng));
        assertEquals(0, ship.receiveDamage(Ship.getWeaponId(), shots, rng));
    }

    @Test
    void receiveDamageWhenEnginesAreNotOperatedAndShieldsAreOn() {

        Random rng = new Random(10);

        ArrayList shots = new ArrayList<>(Arrays.asList(1, 1));

        assertEquals(1, ship.receiveDamage(Ship.getWeaponId(), shots, rng));
        assertEquals(1, ship.receiveDamage(Ship.getWeaponId(), shots, rng));
    }

    @Test
    void receiveDamageWhenEnginesAreNotOperatedAndShieldsAreOff() {

        Random rng = new Random(10);
        ship.setShield(0);

        ArrayList shots = new ArrayList<>(Arrays.asList(1, 1));

        assertEquals(2, ship.receiveDamage(Ship.getWeaponId(), shots, rng));
        assertEquals(1, ship.receiveDamage(Ship.getWeaponId(), shots, rng));
    }


    @Test
    void receiveDamageAffectsCrewInTargetRoom() {

        Random rng = new Random(10);
        Person p = new Person();
        ship.addCrewmember(p, 1);
        ship.setShield(0);
        ArrayList shots = new ArrayList<>(Arrays.asList(1, 1));
        ship.receiveDamage(1, shots, rng);

        assertTrue(p.getHealthPoints() < 100);
    }


    @Test

    void receiveDamageAimedAtCentralRoomShouldAffectMovingCrew() {

        Random rng = new Random(10);
        Person p = new Person();
        ship.addCrewmember(p, 1);
        ship.setShield(0);
        ship.setSystems(Ship.getEngineId(), 0);
        ArrayList shots = new ArrayList<>(Arrays.asList(1, 1));

        p.moveTo(3);
        ship.receiveDamage(0, shots, rng);

        assertTrue(p.getHealthPoints() < 100);
    }


    @Test
    void canShootWithWeaponesCharged() {
        ship.setWeapones(1);
        assertTrue(ship.canShoot());
    }

    @Test
    void cannotShootWithWeaponesHalfCharged() {
        ship.setWeapones(0.5);
        assertFalse(ship.canShoot());
    }

    @Test
    void cannotShootWithWeaponesNotCharged() {
        ship.setWeapones(0);
        assertFalse(ship.canShoot());
    }

    @Test
    void rechargeWeapones_notOperated() {

        ship.rechargeWeapones();
        assertEquals(0.02, ship.getWeapones(), 0.0005);


        ship.setWeapones(0.99);
        ship.rechargeWeapones();
        assertEquals(1, ship.getWeapones(), 0.0005);

    }


    @Test
    void rechargeWeapones_operated() {
        ship.addCrewmember(new Person(), Ship.getWeaponId());

        ship.rechargeWeapones();
        assertEquals(0.03, ship.getWeapones(), 0.0005);


        ship.setWeapones(0.975);
        ship.rechargeWeapones();
        assertEquals(1, ship.getWeapones(), 0.0005);

    }

    @Test
    void calculateStateIncresesOxygenLevel() {
        ship.setOxygenLevel(0);
        ship.calculateState();
        assertTrue(ship.getOxygenLevel() > 0);
    }

    @Test
    void calculateStateDoesntOverflowOxygenTank() {
        ship.setOxygenLevel(99);
        ship.calculateState();
        assertEquals(100, ship.getOxygenLevel());
        ship.calculateState();
        assertEquals(100, ship.getOxygenLevel());
    }

    @Test
    void calculateStateRechargesShield() {
        ship.setShield(0);
        ship.calculateState();
        assertTrue(ship.getShield() > 0);
    }

    @Test
    void calculateStateDoesntOverflowShieldLevel() {
        ship.setShield(0.99);
        ship.calculateState();
        assertEquals(1, ship.getShield());
        ship.calculateState();
        assertEquals(1, ship.getShield());
    }


    @Test
    void rechargeSpeedIncresesWhenShieldAreOperated()
    {
        ship.setShield(0.14);
        ship.calculateState();
        double notOperated = ship.getShield();


        ship.addCrewmember(new Person(), Ship.getShieldId());

        ship.setShield(0.14);
        ship.calculateState();
        double operated = ship.getShield();

        assertTrue(operated > notOperated);
    }

    @Test
    void calculateStateSuffocatatesCrewWhenOxygenIsLow()
    {
        Person p = new Person();
        ship.addCrewmember(p);
        ship.setOxygenLevel(0);
        ship.calculateState();

        assertTrue(p.getHealthPoints() < 100);
    }

    @Test
    void addCrewmember() {
        ship.addCrewmember(new Person(), 3);
        assertEquals(1, ship.getCrewCount());

        ship.addCrewmember(new Person(), 4);
        ship.addCrewmember(new Person(), 1);
        assertEquals(3, ship.getCrewCount());
    }


    @Test
    void addCrewmember_boardedShip() {
        Person p = new Person();
        ship.addCrewmember(p);
        assertSame(ship, p.getBoardedShip());
    }


    @Test
    void newShipIsNotDamaged() {
        for(int i = 0; i <= 4; i++)
        {
            assertFalse(ship.isDamaged(i));
        }

        assertEquals(30, ship.getHull());
    }

    @Test
    void isDamaged() {
        ship.setSystems(1, ship.getMaxSystems(1));
        assertFalse(ship.isDamaged(1));

        ship.setSystems(1, 0.9);
        assertTrue(ship.isDamaged(1));
    }


    @Test
    void isOperated() {
        assertFalse(ship.isOperated(1));
        Person p = new Person();
        ship.addCrewmember(p, 1);
        assertTrue(ship.isOperated(1));
    }

    @Test

    void systemsUnderRepairAreNotOperated() {
        Person p = new Person();
        ship.addCrewmember(p, 1);
        p.repair();
        assertFalse(ship.isOperated(1));
    }

    @Test
    void repairBrokenSystems()
    {
        Person p = new Person();
        ship.addCrewmember(p, 1);
        ship.setSystems(1, 0);

        ship.repairBrokenSystems();

        assertTrue(p.isRepairing());
        assertEquals(Person.getRepairRate(), ship.getSystems(1), 0.01);
    }

    @Test
    void repairBrokenSystemsIsFasterWhenMultiplePeopleAreRepairing() {
        Person p1 = new Person();
        Person p2 = new Person();
        ship.addCrewmember(p1, 1);
        ship.addCrewmember(p2, 1);

        ship.setSystems(1, 0);
        ship.repairBrokenSystems();

        assertTrue(p1.isRepairing());
        assertTrue(p2.isRepairing());
        assertEquals(2 * Person.getRepairRate(), ship.getSystems(1), 0.01);
    }

    @Test
    void repairBrokenSystemsStopsRepairingWhenfinished()
    {
        Person p = new Person();
        ship.addCrewmember(p, 1);
        ship.setSystems(1, 0.99);

        ship.repairBrokenSystems();

        assertEquals(1, ship.getSystems(1), 0.001);
        assertFalse(p.isRepairing());
    }

    @Test
    void getRoomCount() {
        assertEquals(6, ship.getRoomCount());
    }



}