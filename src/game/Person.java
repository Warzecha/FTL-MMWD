package game;

public class Person {

    int healthPoints;
    boolean fighting;
    boolean working;
    int roomId;




    public void breathe(int oxygenLevel) {
        if(oxygenLevel < 0.20)
            healthPoints -= 6;
    }



}
