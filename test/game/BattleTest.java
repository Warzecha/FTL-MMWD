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
        battle = new Battle(ship1, ship2);

    }


    @Test
    void nextMomentIncrementsTime() {
        battle.nextMoment();
        assertEquals(1, battle.getTime());

    }

}