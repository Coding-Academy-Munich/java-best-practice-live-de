package adventure.v1;

import java.util.Map;

public record World(Map<String, Location> locations, String initialLocationName) {
    @Override
    public String toString() {
        return "World{" + "locations=" + locations + ", initialLocationName='" + initialLocationName + '\'' + '}';
    }
}
