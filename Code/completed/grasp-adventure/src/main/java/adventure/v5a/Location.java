package adventure.v5a;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record Location(String name, String description, Map<String, Location> connectedLocations) {

    public static Location fromData(Map<String, Object> data) {
        String name = (String) data.get("name");
        String desc = (String) data.getOrDefault("description", "");
        return new Location(name, desc, new HashMap<>());
    }

    public Location getConnectedLocation(String name) {
        var result = connectedLocations.get(name);
        if (result == null) {
            throw new IllegalArgumentException("No such direction: " + name);
        }
        return result;
    }

    public void setConnectedLocation(String name, Location location) {
        connectedLocations.put(name, location);
    }

    public List<String> getConnectedDirections() {
        return connectedLocations.keySet().stream().toList();
    }

    @Override
    public String toString() {
        return String.format("Location{name='%s', description='%s', directions=[%s]}", name, description,
                String.join(", ", getConnectedDirections().stream().map(s -> "'" + s + "'").toList()));
    }
}
