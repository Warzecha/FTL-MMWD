package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleTest {

    Ship ship1;
    Ship ship2;
    Battle battle;


    @BeforeEach
    void setup()
    {
        ship1 = new Ship();
        ship2 = new Ship();
        battle = new Battle(ship1, ship2);

    }


    @Test
    void constructor()
    {
        Ship s1 = new Ship();
        Ship s2 = new Ship();
        Battle b = new Battle(s1, s2);

        assertEquals(0, b.getTime());
        assertSame(b.getShip1(), s1);
        assertSame(b.getShip2(), s2);

    }


    @Test
    void nextMoment()
    {

        battle.nextMoment();
        assertEquals(1, battle.getTime());


    }

}