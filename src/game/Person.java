package game;

public class Person {

    private int healthPoints;
    private int roomId;




    private Ship boardedShip;

    private boolean repairing;
    private boolean isMoving;


    private static int damageFromShots = 30;
    private static int suffocationRate = 6;
    private static double repairRate = 0.05;

    public Person() {
        this.healthPoints = 100;
        this.roomId = 0;
    }


    public static int getSuffocationRate() {
        return suffocationRate;
    }
    public static int getDamageFromShots() {
        return damageFromShots;
    }
    public static double getRepairRate() {
        return repairRate;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setBoardedShip(Ship boardedShip) {
        this.boardedShip = boardedShip;
    }
    public Ship getBoardedShip() {
        return boardedShip;
    }

    public void breathe(int oxygenLevel) {
        if(oxygenLevel < 20)
            healthPoints -= suffocationRate;
    }

    public void receiveDamage(int damage)
    {
        healthPoints -= damage * damageFromShots;
    }


    public void setRoomId(int roomId) {
        if(roomId >= boardedShip.getRoomCount()) {
            throw new RuntimeException("There is no room with this id");
        }
        this.roomId = roomId;
    }
    public int getRoomId() {
        if(isMoving)
            return 0;
        return roomId;
    }

    public boolean isRepairing() {return repairing; }

    public void repair() {repairing = true; }

    public void stopRepairing() {
        repairing = false;
    }


    public boolean isMoving() {
        return isMoving;
    }

    public void moveTo(int target) {
        if(target >= boardedShip.getRoomCount() || target < 0) {
            throw new IllegalArgumentException("Invalid roomId");
        }
        isMoving = true;
        roomId = target;

    }


    public void finalizeMovement() {
        isMoving = false;
    }
}
