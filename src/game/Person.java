package game;

public class Person {

    int healthPoints;
    boolean repairing;


    int roomId;



    static int damageFromShots = 30;




    public void breathe(double oxygenLevel) {
        if(oxygenLevel < 0.20)
            healthPoints -= 6;
    }

    public void receiveDamage(int damage)
    {
        healthPoints -= damage * damageFromShots;
    }



    public int getRoomId() {return roomId; }

    public boolean isRepairing() {return repairing; }

    public void startRepairing() {repairing = true; }



}
