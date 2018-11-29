package game;


import java.util.Random;

public class Ship {

    float shield;
    int hull;


    Person crew[];

    Random r = new Random();

    double oxygenLevel; //value between 0 and 100


//    systems are represented by a float between 0 and maximum power.
//    0 - broken
//    1 - working condition
    float [] systems;






//    system power is represented by an int
    int [] power;



    double shieldChargeRate = 0.02;
    double oxygenUsageRate = 2;
    double oxygenProductionRate = 5;

    int shieldId = 0;
    int weaponId = 1;
    int steeringId = 2;
    int engineId = 3;
    int oxygenId = 4;
    int medicalId = 5;




    double calculateEvasion()
    {

        return Math.floor(Math.min(systems[1], power[1])) * (systems[shieldId] >= 1 ? 1 : 0) * 0.1;

//        TODO: include crew member effect


    }




    public int receiveDamage(int [] [] shots)
    {


        int totalDamage = 0;

        for(int [] shot : shots)
        {
            double chance = r.nextDouble();

            if(chance <= calculateEvasion())
            {
                totalDamage += 0;
            } else
            {
                if(shield >= 1)
                {
                    totalDamage += 0;
                    shield--;
                }
                else
                {
                    totalDamage += shot[0];
                    hull -= shot[0];
                    systems[shot[1]] = Math.max(systems[shot[1]] - shot[0], 0);


                }
            }
        }

        return totalDamage;
    }




    public double calculateOxygenLevels()
    {
        double newOxygenLevel = oxygenLevel - oxygenUsageRate + Math.min(systems[oxygenId], power[oxygenId]);
        oxygenLevel = newOxygenLevel;
        return newOxygenLevel;
    }


    double rechargeShields()
    {
        shield += shieldChargeRate;
        return shield;
    }







}
