package adventure.v5a;

public class Main {
    public static void main(String[] args) {
        World myWorld = WorldFactory.fromJsonFile("dungeon-locations.json");
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

        Player player = new Player("Computer Player", myWorld.getLocationByName(myWorld.initialLocationName()));

        System.out.println();
        System.out.printf("%s taking a few turns:%n", player.getName());
        System.out.println(player.getLocation().name());
        System.out.print("    ");
        System.out.println(player.getLocation().description());

        for (int i = 0; i < 3; i++) {
            player.takeTurn();
            System.out.println(player.getLocation().name());
            System.out.print("    ");
            System.out.println(player.getLocation().description());
        }
    }
}
