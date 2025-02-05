package adventure.v4a;

public class Pawn {
    public Pawn(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public void move(String direction) {
        location = location.getConnectedLocation(direction);
    }

    public void moveIfPossible(String direction) {
        try {
            move(direction);
        } catch (IllegalArgumentException e) {
            // Ignore the exception
        }
    }

    private final String name;
    private Location location;
}
