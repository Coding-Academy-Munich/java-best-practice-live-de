package adventure.v3b;

import adventure.data.JsonLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WorldV3bTest {
    private List<Map<String, Object>> locationData;
    private World unit;

    @BeforeEach
    void setUp() {
        locationData = List.of(
                Map.of("name", "Room 1", "description", "This is a room", "connections", Map.of("north", "Room 2")),
                Map.of("name", "Room 2", "description", "This is another room", "connections",
                        Map.of("south", "Room 1")));
        unit = World.fromLocationData(locationData);
    }

    @Test
    void fromLocationData() {
        World unit = World.fromLocationData(locationData);

        assertEquals(2, unit.locations().size());
        assertEquals("Room 1", unit.initialLocationName());
    }

    @Test
    void fromLocationData_forComplexLocation() {
        var data = JsonLoader.loadDataFromResource("/json/dungeon-locations.json");
        World unit = World.fromLocationData(data);

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

    @Test
    void getLocationByName() {
        assertEquals("Room 1", unit.getLocationByName("Room 1").name());
        assertEquals("This is a room", unit.getLocationByName("Room 1").description());
        assertEquals("Room 2", unit.getLocationByName("Room 2").name());
        assertEquals("This is another room", unit.getLocationByName("Room 2").description());
    }

    @Test
    void locations() {
        assertEquals(2, unit.locations().size());
        assertTrue(unit.locations().containsKey("Room 1"));
        assertTrue(unit.locations().containsKey("Room 2"));
    }

    @Test
    void initialLocationName() {
        assertEquals("Room 1", unit.initialLocationName());
    }

    @Test
    void worldSetsUpConnections() {
        Location room1 = unit.getLocationByName("Room 1");
        Location toLoc = room1.getConnectedLocation("north");

        assertEquals(unit.getLocationByName("Room 2"), toLoc);
    }

    @Test
    void testToString() {
        assertTrue(unit.toString().contains("Room 1"));
        assertTrue(unit.toString().contains("This is a room"));
        assertTrue(unit.toString().contains("Room 2"));
        assertTrue(unit.toString().contains("This is another room"));
    }
}