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
    void calculateEvasion_notOperated() {
        assertEquals(0.1 * ship.getEngines(), ship.calculateEvasion(), 0.05);
    }

    @Test
    void calculateEvasion_operated() {
        ship.addCrewmember(new Person(Ship.getEngineId()));
        assertEquals(0.1 * ship.getEngines() + 0.1, ship.calculateEvasion(), 0.05);
    }

    @Test
    void calculateEvasion_operatedByMultiplePeople_effectsDoNotSumUp() {
        ship.addCrewmember(new Person(Ship.getEngineId()));
        ship.addCrewmember(new Person(Ship.getEngineId()));
        assertEquals(0.1 * ship.getEngines() + 0.1, ship.calculateEvasion(), 0.05);
    }

    @Test
    void calculateEvasion_enginesDamaged() {
        ship.setSystems(Ship.getEngineId(), 0);
        assertEquals(0, ship.calculateEvasion(), 0.05);
    }

    @Test
    void calculateEvasion_steeringDamaged() {
        ship.setSystems(Ship.getSteeringId(), 0);
        assertEquals(0, ship.calculateEvasion(), 0.05);
    }


    @Test
    void dealDamage() {
//        TODO: Implement this test

    }

    @Test
    void receiveDamage_enginesOperatedAndShieldsOn() {

        Random rng = new Random(10);
        ship.addCrewmember(new Person(Ship.getEngineId()));

        ArrayList shots = new ArrayList<>(Arrays.asList(1, 1));

        assertEquals(0, ship.receiveDamage(Ship.getWeaponId(), shots, rng));
        assertEquals(0, ship.receiveDamage(Ship.getWeaponId(), shots, rng));
    }

    @Test
    void receiveDamage_enginesOperatedAndShieldsOff() {

        Random rng = new Random(10);
        ship.addCrewmember(new Person(Ship.getEngineId()));
        ship.setShield(0);

        ArrayList shots = new ArrayList<>(Arrays.asList(1, 1));

        assertEquals(1, ship.receiveDamage(Ship.getWeaponId(), shots, rng));
        assertEquals(0, ship.receiveDamage(Ship.getWeaponId(), shots, rng));
    }

    @Test
    void receiveDamage_enginesNotOperatedAndShieldsOn() {

        Random rng = new Random(10);

        ArrayList shots = new ArrayList<>(Arrays.asList(1, 1));

        assertEquals(1, ship.receiveDamage(Ship.getWeaponId(), shots, rng));
        assertEquals(1, ship.receiveDamage(Ship.getWeaponId(), shots, rng));
    }

    @Test
    void receiveDamage_enginesNotOperatedAndShieldsOff() {

        Random rng = new Random(10);
        ship.setShield(0);

        ArrayList shots = new ArrayList<>(Arrays.asList(1, 1));

        assertEquals(2, ship.receiveDamage(Ship.getWeaponId(), shots, rng));
        assertEquals(1, ship.receiveDamage(Ship.getWeaponId(), shots, rng));
    }


    @Test
    void receiveDamage_crewInTargetRoom_shouldReveiveDamage() {

        Random rng = new Random(10);
        Person p = new Person(1);
        ship.addCrewmember(p);
        ship.setShield(0);
        ArrayList shots = new ArrayList<>(Arrays.asList(1, 1));
        ship.receiveDamage(1, shots, rng);

        assertTrue(p.getHealthPoints() < 100);
    }


    @Test
    void canShoot_weaponesCharged_True() {
        ship.setWeapones(1);
        assertTrue(ship.canShoot());
    }

    @Test
    void canShoot_weaponesHalfCharged_False() {
        ship.setWeapones(0.5);
        assertFalse(ship.canShoot());
    }

    @Test
    void canShoot_weaponesNotCharged_False() {
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
        ship.addCrewmember(new Person(Ship.getWeaponId()));

        ship.rechargeWeapones();
        assertEquals(0.03, ship.getWeapones(), 0.0005);


        ship.setWeapones(0.975);
        ship.rechargeWeapones();
        assertEquals(1, ship.getWeapones(), 0.0005);

    }

    @Test
    void calculateOxygenState_emptyTank() {
        ship.setOxygenLevel(0);
        ship.calculateState();
        assertTrue(ship.getOxygenLevel() > 0);
    }

    @Test
    void calculateOxygenState_almostFullTank() {
        ship.setOxygenLevel(99);
        ship.calculateState();
        assertEquals(100, ship.getOxygenLevel());
    }

    @Test
    void calculateOxygenState_fullTank() {
        ship.calculateState();
        assertEquals(100, ship.getOxygenLevel());

    }


    @Test
    void calculateShieldState_emptyShield() {
        ship.setShield(0);
        ship.calculateState();
        assertTrue(ship.getShield() > 0);
    }

    @Test
    void calculateShieldState_almostFullShield() {
        ship.setShield(0.99);
        ship.calculateState();
        assertEquals(1, ship.getShield());
    }

    @Test
    void calculateShieldState_fullShield() {
        ship.calculateState();
        assertEquals(1, ship.getShield());
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
    void calculateCrewState_crewSuffocating()
    {
        Person p = new Person(1);
        ship.addCrewmember(p);
        ship.setOxygenLevel(0);
        ship.calculateState();

        assertTrue(p.getHealthPoints() < 100);
    }

    @Test
    void addCrewmember_addSinglePerson() {
        ship.addCrewmember(new Person(3));

        assertEquals(1, ship.getCrewCount());
        ship.addCrewmember(new Person(4));
        ship.addCrewmember(new Person(1));
        assertEquals(3, ship.getCrewCount());
    }

    @Test
    void addCrewmember_addMultiplePeople() {
        ship.addCrewmember(new Person(3));
        ship.addCrewmember(new Person(4));
        ship.addCrewmember(new Person(1));
        assertEquals(3, ship.getCrewCount());
    }

    @Test
    void isDamaged_newShip_systemsAreNotDamaged() {
        for(int i = 0; i <= 4; i++)
        {
            assertFalse(ship.isDamaged(i));
        }

        assertEquals(30, ship.getHull());
    }

    @Test
    void isDamaged_systemIsDamaged() {
        ship.setSystems(1, 0.9);
        assertTrue(ship.isDamaged(1));
    }

    @Test
    void isOperated_notOperated_False() {
        assertFalse(ship.isOperated(1));
    }

    @Test
    void isOperated_operated_True() {
        Person p = new Person(1);
        ship.addCrewmember(p);
        assertTrue(ship.isOperated(1));
    }

    @Test
    void isOperated_underRepair_False() {
        Person p = new Person(1);
        ship.addCrewmember(p);
        p.repair();
        assertFalse(ship.isOperated(1));
    }


    @Test
    void repairBrokenSystems_singlePresonRepairing()
    {
        Person p = new Person(1);
        ship.addCrewmember(p);
        ship.setSystems(1, 0);

        ship.repairBrokenSystems();

        assertTrue(p.isRepairing());
        assertEquals(Person.getRepairRate(), ship.getSystems(1), 0.01);
    }

    @Test
    void repairBrokenSystems_multiplePeopleRepairing() {
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
    void repairBrokenSystems_finishingRepairs_stopRepairing()
    {
        Person p = new Person(1);
        ship.addCrewmember(p);
        ship.setSystems(1, 0.99);

        ship.repairBrokenSystems();

        assertEquals(1, ship.getSystems(1), 0.001);
        assertFalse(p.isRepairing());
    }




}