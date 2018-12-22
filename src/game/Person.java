package game;

public class Person {

    private int healthPoints;
    private boolean repairing;


    private int roomId;


    private static int damageFromShots = 30;
    private static int suffocationRate = 6;
    private static double repairRate = 0.05;


    public Person(int roomId) {
        this.healthPoints = 100;
        this.roomId = roomId;
    }

    public static int getSuffocationRate() {
        return suffocationRate;
    }
    public static int getDamageFromShots() {
        return damageFromShots;
    }
    public static double getRepairRate() {
        return repairRate;
    }


    public void breathe(int oxygenLevel) {
        if(oxygenLevel < 20)
            healthPoints -= suffocationRate;
    }

    public void receiveDamage(int damage)
    {
        healthPoints -= damage * damageFromShots;
    }



    public int getRoomId() {return roomId; }

    public boolean isRepairing() {return repairing; }

    public void repair() {repairing = true; }

    public void stopRepairing() {
        repairing = false;
    }


    public int getHealthPoints() {
        return healthPoints;
    }
}
