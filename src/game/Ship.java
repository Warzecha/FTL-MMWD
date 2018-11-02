package game;


public class Ship {

    float shield;
    int healthPoints;


    Person crew[];

    int oxygenLevel;


//    systems are represented by a float between 0 and maximum power.
//    0 - broken
//    1 - working condition
    float oxygenSystem;
    float medicalSystem;
    float shieldSystem;
    float weaponSystems;
    float steeringSystem;
    float engineSystem;


//    TODO: have to discuss if sensory systems can be damaged
    float sensorSystem;






//    system power is represented by an int
    int oxygenPower;
    int medicalPower;
    int shieldPower;
    int weaponPower;
    int steeringPower;
    int enginePower;



}
