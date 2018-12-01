package game;

public class Person {

    int healthPoints;
    boolean fighting;
    boolean working;
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



    public int getRoomId()
    {
        return roomId;
    }



}
