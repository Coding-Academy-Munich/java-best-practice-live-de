package adventure.v1;

import adventure.data.JsonLoader;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Map<String, Object>> simpleLocationsData = JsonLoader.loadData("dungeon-locations.json");
        Map<String, Location> locationMap = simpleLocationsData.stream().map(Location::fromData).collect(
                Collectors.toMap(Location::name, location -> location));

        World myWorld = new World(locationMap, "Room 1");
        // System.out.println(myWorld);
        System.out.println("World with starting location " + myWorld.initialLocationName());
        System.out.println();

        // Calculate the maximum length of the room names
        int maxRoomNameLength = myWorld.locations().keySet().stream().mapToInt(String::length).max().orElse(0);

        // Print the rooms in a tabular manner
        for (var location : myWorld.locations().values()) {
            String roomName = String.format("%-" + maxRoomNameLength + "s", location.name());
            String description = location.description();
            System.out.println(roomName + " | " + description);
        }
    }
}
