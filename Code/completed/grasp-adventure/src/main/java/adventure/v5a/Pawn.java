package adventure.v5a;

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

    public void moveToLocation(Location connectedLocation) {
        location = connectedLocation;
    }

    private final String name;
    private Location location;
}
