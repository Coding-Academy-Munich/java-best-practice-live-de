package adventure.v5a;

import adventure.data.JsonLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorldFactoryV5aTest {
    private List<Map<String, Object>> locationData;

    @BeforeEach
    void setUp() {
        locationData = List.of(
                Map.of("name", "Room 1", "description", "This is a room", "connections", Map.of("north", "Room 2")),
                Map.of("name", "Room 2", "description", "This is another room", "connections",
                        Map.of("south", "Room 1")));
    }

    @Test
    void fromLocationData() {
        World unit = WorldFactory.fromLocationData(locationData);

        assertEquals(2, unit.locations().size());
        assertEquals("Room 1", unit.initialLocationName());
    }

    @Test
    void fromLocationData_forComplexLocation() {
        var data = JsonLoader.loadDataFromResource("/json/dungeon-locations.json");
        World unit = WorldFactory.fromLocationData(data);

        assertEquals(5, unit.locations().size());
        var vestibule = unit.getLocationByName("Vestibule");
        var entranceHall = unit.getLocationByName("Entrance Hall");
        var darkCorridor = unit.getLocationByName("Dark Corridor");
        var brightlyLitCorridor = unit.getLocationByName("Brightly Lit Corridor");

        assertEquals(List.of("north"), vestibule.getConnectedDirections());
        assertEquals(Set.of("west", "east", "south"), Set.copyOf(entranceHall.getConnectedDirections()));
        assertEquals(entranceHall, vestibule.getConnectedLocation("north"));
        assertEquals(darkCorridor, entranceHall.getConnectedLocation("west"));
        assertEquals(brightlyLitCorridor, entranceHall.getConnectedLocation("east"));
        assertEquals(vestibule, entranceHall.getConnectedLocation("south"));
        assertEquals(entranceHall, darkCorridor.getConnectedLocation("east"));
        assertEquals(entranceHall, brightlyLitCorridor.getConnectedLocation("west"));
    }
}
