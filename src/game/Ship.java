package game;

import game.exception.NoSuchRoomException;

import java.util.*;


public class Ship {



    private int hull = 30;
    private double shield;
    private double weapones = 0;

    private List<Person> crew = new ArrayList<Person>(0);

    //    systems are represented by a double between 0 and maximum power.
    private ArrayList<Double> systems;
    private final ArrayList<Double> maxSystems;

    private ArrayList<Integer> shots;

    private int oxygenLevel = 100; //value between 0 and 100
    public final ShipParameters parameters = new ShipParameters();



    public Ship() {

        maxSystems = new ArrayList<>(Arrays.asList(0.0, 1.0, 1.0, 1.0, 2.0, 1.0));
        systems = new ArrayList<>(maxSystems);
        shots = new ArrayList<>(Arrays.asList(1, 1));
        shield = systems.get(Room.SHIELD.getId());

    }

    void addCrewmember(Person p, int roomId) throws NoSuchRoomException {
        p.setBoardedShip(this);
        p.setRoomId(roomId);
        crew.add(p);
    }

    void addCrewmember(Person p) {
        p.setBoardedShip(this);
        crew.add(p);
    }



    public int getCrewCount(){ return crew.size(); }

    public double calculateEvasion() {
        double baseEvasion = Math.floor(systems.get(Room.ENGINE.getId())) * Math.floor(systems.get(Room.STEERING.getId())) * 0.1;
        double operatedEffect = 0;

        if(isOperated(Room.ENGINE.getId()))
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
        if(canShoot())
        {
            setWeapones(0);
            return enemy.receiveDamage(target, shots, rng);
        }
        else return 0;

    }

    public void calculateOxygenLevels() {
        oxygenLevel = (int) Math.min((oxygenLevel - parameters.OXYGEN_USAGE_RATE + Math.floor(systems.get(Room.OXYGEN.getId())) * parameters.OXYGEN_PRODUCTION_RATE), 100);
    }

    private void calculateShields() {
        double shieldRecharge = parameters.SHIELD_CHARGE_RATE;
        if(isOperated(Room.SHIELD.getId())) {
            shieldRecharge *= parameters.OPERATION_CHARGE_RATE_BONUS;
        }
        shield = Math.min(shield + shieldRecharge, 1);

    }

    public boolean canShoot() {return weapones == 1;}

    void rechargeWeapones() {
        double weaponesRecharge = parameters.WEAPONED_CHARGE_RATE;
        if(isOperated(Room.WEAPON.getId())) {
            weaponesRecharge *= parameters.OPERATION_CHARGE_RATE_BONUS;
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


    public int getHull() {
        return hull;
    }
    public void setHull(int value) {
        hull = value;
    }

    public boolean idDead() {
        return hull <= 0;
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
        return systems.get(Room.ENGINE.getId());
    }

    public int getOxygenLevel() {
        return oxygenLevel;
    }
    public void setOxygenLevel(int oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
    }

    public double getSystemById(int id) {
        return systems.get(id);
    }
    public void setSystemById(int id, double value) {
        if(value < 0)
        {
            throw new IllegalArgumentException("Invalid value");
        }

        if(id < 0 || id >= systems.size())
        {
            throw new IllegalArgumentException("Index out of range");
        }


        systems.set(id, Math.min(value, maxSystems.get(id)));

    }

    public double getMaxSystemById(int id) {
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
                setSystemById(roomId, systems.get(roomId) + Person.getRepairRate());
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
