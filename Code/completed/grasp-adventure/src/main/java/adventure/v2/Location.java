package adventure.v2;

import java.util.Map;

public record Location(String name, String description) {

    public static Location fromData(Map<String, Object> data) {
        String name = (String) data.get("name");
        String desc = (String) data.getOrDefault("description", "");
        return new Location(name, desc);
    }

    @Override
    public String toString() {
        return "Location{" + "name='" + name + '\'' + ", description='" + description + '\'' + '}';
    }
}