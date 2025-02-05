package adventure.v2;

import adventure.data.JsonLoader;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record World(Map<String, Location> locations, String initialLocationName) {
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
        return new World(locations, initialLocationName);
    }

    @Override
    public String toString() {
        String initialLocationLine = "  Initial location name: '" + initialLocationName + '\'' + "\n";
        String locationLines = locations.values().stream().map(location -> "    " + location.toString()).collect(
                Collectors.joining("\n"));
        return "World{\n" + initialLocationLine + "  Locations:\n" + locationLines + '}';
    }
}
