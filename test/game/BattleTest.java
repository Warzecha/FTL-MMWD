package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleTest {

    Ship ship1;
    Ship ship2;
    Battle battle;


    @BeforeEach
    void setup() {
        ship1 = new Ship();
        ship2 = new Ship();
        battle = new Battle(ship1, ship2, 100);

    }


    @Test
    void nextMomentIncrementsTime() {
        battle.nextMoment();
        assertEquals(1, battle.getTime());

    }


    @Test
    void fightEndsAtEndTime() {
        battle.fight();
        assertEquals(100, battle.getTime());
    }


    @Test
    void fightReturnsPositiveValueIfShip1Wins() {
        ship2.setHull(-1);
        assertTrue(battle.fight() > 0);
    }

    @Test
    void fightReturnsNegativeValueIfShip2Wins() {
        ship1.setHull(-1);
        assertTrue(battle.fight() < 0);
    }


}