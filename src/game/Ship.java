package game;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ship {

    private int hull = 30;
    private double shield = 2;
    private double weapones = 0;


//    private Person[] crew = new Person[0];

    private List<Person> crew = new ArrayList<Person>(0);






    //    systems are represented by a float between 0 and maximum power.
    private float [] systems;
    float [] maxSystems;

    private Random r = new Random();






    private double shieldChargeRate = 0.02;
    private double weaponesChargeRate = 0.02;
    private double oxygenUsageRate = 2;
    private double oxygenProductionRate = 5;

    private static int shieldId = 0;
    static int weaponId = 1;
    private static int steeringId = 2;
    private static int engineId = 3;
    private static int oxygenId = 4;
    static int medicalId = 5;


    private int[] shots = {1, 1};
    private double oxygenLevel; //value between 0 and 100

    public Ship() {
        maxSystems = new float[]{2, 2, 1, 3, 1};
        systems = maxSystems.clone();


    }



    public int getCrewCount(){ return crew.size(); }


    public double calculateEvasion()
    {

        double baseEvasion = Math.floor(systems[engineId]) * Math.floor(systems[steeringId]) * 0.1;

        double operatedEffect = 0;
        boolean oparated = false;
        for(Person p : crew)
        {
            if(p.getRoomId() == engineId && !p.isRepairing())
            {
                oparated = true;
            }

        }

        if(oparated)
        {
            operatedEffect = 0.1;
        }

        return baseEvasion + operatedEffect;
    }



    public int dealDamage(Ship enemy, int target)
    {
        return enemy.receiveDamage(target, shots);
    }




    public int receiveDamage(int target, int [] shots)
    {


        int totalDamage = 0;

        for(int shot : shots)
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
                    totalDamage += shot;
                    hull -= shot;
                    systems[target] = Math.max(systems[target] - shot, 0);

                    for(Person p : crew)
                    {
                        if(p.getRoomId() == target)
                        {
                            p.receiveDamage(shot);
                        }
                    }


                }
            }
        }

        return totalDamage;
    }




    private void calculateOxygenLevels()
    {
        oxygenLevel = oxygenLevel - oxygenUsageRate + Math.floor(systems[oxygenId]) * oxygenProductionRate;
    }


    private void calculateShields()
    {
        double newShieldLevel = shield + shieldChargeRate;
        shield = Math.max(newShieldLevel, Math.floor(systems[shieldId]));

    }



    boolean canShoot() {return weapones == 1;}

    void rechargeWeapones()
    {
        weapones = Math.max(weapones + weaponesChargeRate, 1);
    }





    public void calculateState()
    {
        calculateOxygenLevels();
        calculateShields();


        for(Person p : crew)
        {
            p.breathe(oxygenLevel);
        }
    }


    void addCrewmember(Person p)
    {
        crew.add(p);
    }






    public static int getShieldId() {
        return shieldId;
    }

    public static int getWeaponId() {
        return weaponId;
    }

    public static int getSteeringId() {
        return steeringId;
    }

    public static int getEngineId() {
        return engineId;
    }

    public static int getOxygenId() {
        return oxygenId;
    }


    public double getShield() {
        return shield;
    }

    void setShield(double shield) {
        this.shield = shield;
    }



    public double getEngines()
    {
        return systems[engineId];
    }

    public void setSystems(int id, float value)
    {
        if(value < 0)
        {
            throw new RuntimeException("Invalid value");
        }

        if(id < 0 || id >= systems.length)
        {
            throw new RuntimeException("Index out of range");
        }


        systems[id] = Math.min(value, maxSystems[id]);

    }



}
