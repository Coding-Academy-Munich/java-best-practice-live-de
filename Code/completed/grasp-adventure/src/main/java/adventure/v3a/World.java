package adventure.v3a;

import adventure.data.JsonLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record World(Map<String, Location> locations, String initialLocationName, List<Connection> connections) {
    public Location getLocationByName(String name) {
        return locations.get(name);
    }

    public static World fromJsonFile(String fileName) {
        List<Map<String, Object>> locationData = JsonLoader.loadData(fileName);
        return fromLocationData(locationData);
    }

    public static World fromLocationData(
            List<Map<String, Object>> locationData) {
        Map<String, Location> locations = locationData.stream().map(Location::fromData).collect(
                Collectors.toMap(Location::name, location -> location));
        String initialLocationName = (String) locationData.get(0).get("name");
        List<Connection> connections = new ArrayList<>();
        for (Map<String, Object> fromLocationData : locationData) {
            String fromName = (String) fromLocationData.get("name");
            Object targets = fromLocationData.get("connections");
            if (!(targets instanceof Map))
                throw new IllegalArgumentException(
                        String.format("Invalid type for connections: %s", targets.getClass()));
            //noinspection unchecked
            var targetsMap = (Map<String, Object>) targets;

            Location fromLocation = locations.get(fromName);
            for (Map.Entry<String, Object> toData : targetsMap.entrySet()) {
                String direction = toData.getKey();
                String toName = (String)toData.getValue();
                Location toLocation = locations.get(toName);
                connections.add(new Connection(fromLocation, direction, toLocation));
            }
        }
        return new World(locations, initialLocationName, connections);
    }

    @Override
    public String toString() {
        String initialLocationLine = "  Initial location name: '" + initialLocationName + '\'' + "\n";
        String locationLines = locations.values().stream().map(location -> "    " + location.toString()).collect(
                Collectors.joining("\n"));
        return "World{\n" + initialLocationLine + "  Locations:\n" + locationLines + '}';
    }

    public Location getConnectedLocation(Location loc, String direction) {
        for (Connection c : connections) {
            if (c.from().equals(loc) && c.direction().equals(direction))
                return c.to();
        }
        throw new IllegalArgumentException(
                String.format("No connected location for %s in direction %s", loc, direction));
    }
}
