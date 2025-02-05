package adventure.v4c;

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

    public void perform(Action action) {
        action.perform(this);
    }

    public void performIfPossible(Action action) {
        action.performIfPossible(this);
    }

    public void moveToLocation(Location connectedLocation) {
        location = connectedLocation;
    }

    private final String name;
    private Location location;
}
