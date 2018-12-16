package game;

public class Battle {

    Ship ship1;
    Ship ship2;
    int time = 0;

    public Battle(Ship ship1, Ship ship2) {
        this.ship1 = ship1;
        this.ship2 = ship2;
    }

    public Ship getShip1() {
        return ship1;
    }
    public Ship getShip2() {
        return ship2;
    }

    public int getTime() {
        return time;
    }

    public void nextMoment() {
        ship1.calculateState();
        ship2.calculateState();
        time++;
    }
}
