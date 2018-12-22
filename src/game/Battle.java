package game;

public class Battle {

    private final Ship ship1;
    private final Ship ship2;
    private final int endTime;
    private int time = 0;

    public Battle(Ship ship1, Ship ship2, int endTime) {
        this.ship1 = ship1;
        this.ship2 = ship2;
        this.endTime = endTime;
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

    public int fight() {
        while (time < endTime) {



            nextMoment();
        }
        return 0;
    }



}
