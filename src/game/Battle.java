package game;

import java.util.Random;

public class Battle {

    private final Ship ship1;
    private final Ship ship2;
    private final int endTime;
    private int time = 0;
    private Random rng = new Random(1);

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


    public int fight() {
        while (time < endTime) {


            ship1.calculateState();
            ship2.calculateState();

            ship1.calculateDecision(ship2);
            ship2.calculateDecision(ship1);

            ship1.dealDamage(ship2, ship1.getBestTarget(ship2), rng);
            ship2.dealDamage(ship1, ship2.getBestTarget(ship1), rng);

            int winningBonus = 10;
            if(ship1.isDead()) {
                return ship1.getHull() - ship2.getHull() + winningBonus;
            }
            if(ship2.isDead()) {
                return ship1.getHull() - ship2.getHull() - winningBonus;
            }

            time++;
        }
//        System.out.println(ship2.getHull());
        return ship1.getHull() - ship2.getHull();
    }



}
