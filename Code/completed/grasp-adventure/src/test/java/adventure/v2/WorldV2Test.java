package adventure.v2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WorldV2Test {
    private List<Map<String, Object>> locationData;
    private World unit;

    @BeforeEach
    void setUp() {
        locationData = List.of(
                Map.of("name", "Room 1", "description", "This is a room"),
                Map.of("name", "Room 2", "description", "This is another room"));
        unit = World.fromLocationData(locationData);
    }

    @Test
    void fromLocationData() {
        World unit = World.fromLocationData(locationData);

        assertEquals(2, unit.locations().size());
        assertEquals("Room 1", unit.initialLocationName());
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
    void testToString() {
        assertTrue(unit.toString().contains("Room 1"));
        assertTrue(unit.toString().contains("This is a room"));
        assertTrue(unit.toString().contains("Room 2"));
        assertTrue(unit.toString().contains("This is another room"));
    }
}