package game;

public class Person {

    int healthPoints;
    boolean repairing;


    int roomId;


    static int damageFromShots = 30;

    static int suffocationRate = 6;

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


    public void breathe(double oxygenLevel) {
        if(oxygenLevel < 0.20)
            healthPoints -= suffocationRate;
    }

    public void receiveDamage(int damage)
    {
        healthPoints -= damage * damageFromShots;
    }



    public int getRoomId() {return roomId; }

    public boolean isRepairing() {return repairing; }

    public void startRepairing() {repairing = true; }


    public int getHealthPoints() {
        return healthPoints;
    }
}
