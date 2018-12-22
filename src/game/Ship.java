package game;


import java.util.*;

public class Ship {



    private int hull = 30;
    private double shield;
    private double weapones = 0;


    private List<Person> crew = new ArrayList<Person>(0);

    //    systems are represented by a double between 0 and maximum power.
    private ArrayList<Double> systems;
    private ArrayList<Double> maxSystems;
    private ArrayList<Integer> shots;

    private int oxygenLevel = 100; //value between 0 and 100


    private static double shieldChargeRate = 0.02;

    private static double weaponesChargeRate = 0.02;
    private static double oxygenUsageRate = 2;
    private static double oxygenProductionRate = 5;
    private static double operatedChargeRateMultiplier = 1.5;


    private static int shieldId = 1;
    private static int weaponId = 2;
    private static int steeringId = 3;
    private static int engineId = 4;
    private static int oxygenId = 5;
    private static int medicalId = 6;



    public Ship() {

        maxSystems = new ArrayList<>(Arrays.asList(0.0, 1.0, 1.0, 1.0, 2.0, 1.0));
        systems = new ArrayList<>(maxSystems);
        shots = new ArrayList<>(Arrays.asList(1, 1));
        shield = systems.get(shieldId);

    }

    void addCrewmember(Person p, int roomId)
    {
        p.setBoardedShip(this);
        p.setRoomId(roomId);
        crew.add(p);
    }

    void addCrewmember(Person p)
    {
        p.setBoardedShip(this);
        crew.add(p);
    }



    public int getCrewCount(){ return crew.size(); }

    public double calculateEvasion() {
        double baseEvasion = Math.floor(systems.get(engineId)) * Math.floor(systems.get(steeringId)) * 0.1;
        double operatedEffect = 0;

        if(isOperated(engineId))
        {
            operatedEffect = 0.1;
        }
        return baseEvasion + operatedEffect;
    }
    public int receiveDamage(int target, ArrayList<Integer> shots, Random rng) {

        int totalDamage = 0;

        for(int shot : shots)
        {
            double chance = rng.nextDouble();

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
                    systems.set(target, Math.max(systems.get(target) - shot, 0));

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

    public int dealDamage(Ship enemy, int target, Random rng)
    {
        return enemy.receiveDamage(target, shots, rng);
    }

    public void calculateOxygenLevels() {
        oxygenLevel = (int) Math.min((oxygenLevel - oxygenUsageRate + Math.floor(systems.get(oxygenId)) * oxygenProductionRate), 100);
    }

    private void calculateShields() {
        double shieldRecharge = shieldChargeRate;
        if(isOperated(shieldId)) {
            shieldRecharge *= operatedChargeRateMultiplier;
        }
        shield = Math.min(shield + shieldRecharge, 1);

    }

    public boolean canShoot() {return weapones == 1;}

    void rechargeWeapones() {
        double weaponesRecharge = weaponesChargeRate;
        if(isOperated(weaponId)) {
            weaponesRecharge *= operatedChargeRateMultiplier;
        }
        weapones = Math.min(weapones + weaponesRecharge, 1);
    }

    public void calculateState() {
        calculateOxygenLevels();
        calculateShields();


        for(Person p : crew)
        {
            p.breathe(oxygenLevel);
        }
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

    public int getHull() {
        return hull;
    }

    public double getShield() {
        return shield;
    }
    public void setShield(double shield) {
        this.shield = shield;
    }

    public double getWeapones() {
        return weapones;
    }
    public void setWeapones(double weapones) {
        this.weapones = weapones;
    }

    public double getEngines()
    {
        return systems.get(engineId);
    }

    public int getOxygenLevel() {
        return oxygenLevel;
    }

    public void setOxygenLevel(int oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
    }

    public double getSystems(int id) {
        return systems.get(id);
    }
    public void setSystems(int id, double value) {
        if(value < 0)
        {
            throw new RuntimeException("Invalid value");
        }

        if(id < 0 || id >= systems.size())
        {
            throw new RuntimeException("Index out of range");
        }


        systems.set(id, Math.min(value, maxSystems.get(id)));

    }

    public double getMaxSystems(int id) {
        return maxSystems.get(id);
    }


    public boolean isOperated(int roomId) {
        boolean oparated = false;
        for(Person p : crew)
        {
            if(p.getRoomId() == roomId && !p.isRepairing())
            {
                oparated = true;
            }

        }

        return oparated;
    }
    public boolean isDamaged(int roomId) {
        return systems.get(roomId) < maxSystems.get(roomId);
    };



    public void repairBrokenSystems() {

        for(Person p : crew) {
            int roomId = p.getRoomId();
            if(isDamaged(roomId)) {
                p.repair();
                setSystems(roomId, systems.get(roomId) + Person.getRepairRate());
                if(systems.get(roomId).equals(maxSystems.get(roomId))) {
                    p.stopRepairing();
                }
            }
            else {
                p.stopRepairing();
            }
        }

    }


    public int getRoomCount() {
        return systems.size();
    }
}
