package adventure.v4c;

import java.util.Map;
import java.util.stream.Collectors;

public record World(Map<String, Location> locations, String initialLocationName) {
    public Location getLocationByName(String name) {
        return locations.get(name);
    }

    @Override
    public String toString() {
        String initialLocationLine = "  Initial location name: '" + initialLocationName + '\'' + "\n";
        String locationLines = locations.values().stream().map(location -> "    " + location.toString()).collect(
                Collectors.joining("\n"));
        return "World{\n" + initialLocationLine + "  Locations:\n" + locationLines + '}';
    }
}
