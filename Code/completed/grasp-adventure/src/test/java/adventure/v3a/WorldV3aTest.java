package adventure.v3a;

import adventure.data.JsonLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WorldV3aTest {
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
        assertEquals(8, unit.connections().size());
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
    void getConnectedLocation_ifTargetExists() {
        Location fromLoc = unit.getLocationByName("Room 1");
        Location toLoc = unit.getConnectedLocation(fromLoc, "north");

        assertEquals(unit.getLocationByName("Room 2"), toLoc);
    }

    @Test
    void getConnectedLocation_ifTargetDoesNotExist() {
        Location fromLoc = unit.getLocationByName("Room 1");

        assertThrows(IllegalArgumentException.class, () -> unit.getConnectedLocation(fromLoc, "nowhere"));
    }

    @Test
    void getConnections_forSimpleWorld() {
        List<Connection> connections = unit.connections();
        assertEquals(2, connections.size());

        var room1 = unit.getLocationByName("Room 1");
        var room2 = unit.getLocationByName("Room 2");
        assertTrue(connections.contains(new Connection(room1, "north", room2)));
        assertTrue(connections.contains(new Connection(room2, "south", room1)));
    }

    @Test
    void testToString() {
        assertTrue(unit.toString().contains("Room 1"));
        assertTrue(unit.toString().contains("This is a room"));
        assertTrue(unit.toString().contains("Room 2"));
        assertTrue(unit.toString().contains("This is another room"));
    }
}