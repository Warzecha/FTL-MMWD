package game;

public class Person {

    int healthPoints;
    boolean fighting;
    boolean working;
    int roomId;




    public void breathe(double oxygenLevel) {
        if(oxygenLevel < 0.20)
            healthPoints -= 6;
    }



}
