package game;

import java.util.Random;

public class Battle {

    private final Ship ship1;
    private final Ship ship2;
    private final int endTime;
    private int time = 0;
    private Random rng = new Random();

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


        time++;
    }

    public int fight() {
        while (time < endTime) {
            System.out.println(ship1.getCrew().get(0).getHealthPoints());


            ship1.calculateState();
            ship2.calculateState();


            ship1.dealDamage(ship2, ship1.getBestTarget(ship2), rng);
            ship2.dealDamage(ship1, ship2.getBestTarget(ship1), rng);




            int winningBonus = 10;
            if(ship1.idDead()) {
                return ship1.getHull() - ship2.getHull() + winningBonus;
            }
            if(ship2.idDead()) {
                return ship1.getHull() - ship2.getHull() - winningBonus;
            }

            nextMoment();
        }
        return ship1.getHull() - ship2.getHull();
    }



}
