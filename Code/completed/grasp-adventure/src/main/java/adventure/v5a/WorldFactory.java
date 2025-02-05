package adventure.v5a;

import adventure.data.JsonLoader;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WorldFactory {
    public static World fromJsonFile(String fileName) {
        List<Map<String, Object>> locationData = JsonLoader.loadData(fileName);
        return fromLocationData(locationData);
    }

    public static World fromLocationData(
            List<Map<String, Object>> locationData) {
        Map<String, Location> locations = locationData.stream().map(Location::fromData).collect(
                Collectors.toMap(Location::name, location -> location));
        String initialLocationName = (String) locationData.get(0).get("name");
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
                fromLocation.setConnectedLocation(direction, toLocation);
            }
        }
        return new World(locations, initialLocationName);
    }
}
