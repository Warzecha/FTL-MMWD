package game;

public enum Room {

    SHIELD(1),
    WEAPON(2),
    STEERING(3),
    ENGINE(4),
    OXYGEN(5),
    MEDICAL(6);


    private final int value;

    Room(int v) {
        this.value = v;
    }

    public int getId() {
        return value;
    }
}