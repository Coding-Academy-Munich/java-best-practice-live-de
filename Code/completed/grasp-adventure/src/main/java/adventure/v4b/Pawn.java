package adventure.v4b;

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

    public void perform(Action action, String direction) {
        switch (action) {
            case MOVE -> location = location.getConnectedLocation(direction);
            case SKIP_TURN -> {}
        }
    }

    public void performIfPossible(Action action, String direction) {
        try {
            perform(action, direction);
        } catch (IllegalArgumentException e) {
            // Ignore the exception
        }
    }

    private final String name;
    private Location location;
}
