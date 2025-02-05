package adventure.v3a;

public class Main {
    public static void main(String[] args) {
        World myWorld = World.fromJsonFile("dungeon-locations.json");
        // System.out.println(myWorld);
        System.out.println(
                "World with starting location " + myWorld.initialLocationName());
        System.out.println();

        // Calculate the maximum length of the room names
        int maxRoomNameLength = myWorld.locations().keySet().stream()
                .mapToInt(String::length)
                .max()
                .orElse(0);

        // Print the rooms in a tabular manner
        for (var location : myWorld.locations().values()) {
            String roomName = String.format(
                    "%-" + maxRoomNameLength + "s", location.name());
            String description = location.description();
            System.out.println(roomName + " | " + description);
        }
    }
}
