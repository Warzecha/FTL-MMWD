package game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleTest {


    Battle battle;

    @Test
    void constructor()
    {
        Ship ship1 = new Ship();
        Ship ship2 = new Ship();

        Battle b = new Battle(ship1, ship2);

        assertEquals(0, b.getTime());

        assertTrue(b.getFirstShip() == ship1);
        assertTrue(b.getSecondShip() == ship2);

    }



}