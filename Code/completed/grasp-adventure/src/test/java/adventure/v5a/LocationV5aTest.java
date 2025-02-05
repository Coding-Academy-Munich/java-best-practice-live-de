package adventure.v5a;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LocationV5aTest {
    private Map<String, Object> locationData;

    @BeforeEach
    void setUp() {
        locationData = Map.of(
                "name", "Room 1", "description", "This is a room", "connections", Map.of("north", "Room 2"));
    }

    @Test
    void fromData() {
        Location unit = Location.fromData(locationData);

        assertEquals("Room 1", unit.name());
        assertEquals("This is a room", unit.description());

        // The location factory method does not fill in the connected locations, since it has no way of referencing
        // other locations by name. This is later backfilled by the World constructor
        assertTrue(unit.connectedLocations().isEmpty());
        assertTrue(unit.getConnectedDirections().isEmpty());
    }

    @Test
    void getConnectedLocation_raisesErrorForBadDirection() {
        Location unit = Location.fromData(locationData);
        assertThrows(IllegalArgumentException.class, () -> unit.getConnectedLocation("nowhere"));
    }

    @Test
    void connectedDirections() {
        Location room2 = Location.fromData(Map.of("name", "Room 2"));
        Location unit = Location.fromData(locationData);
        unit.setConnectedLocation("north", room2);

        assertEquals(List.of("north"), unit.getConnectedDirections());
        assertEquals(room2, unit.getConnectedLocation("north"));
    }

    @Test
    void testToString() {
        Location unit = Location.fromData(locationData);

        assertTrue(unit.toString().contains("Room 1"));
        assertTrue(unit.toString().contains("This is a room"));
    }

    @Test
    void testEquals() {
        Location unit = Location.fromData(locationData);
        Location unit2 = Location.fromData(locationData);

        assertNotSame(unit, unit2);
        assertEquals(unit, unit2);
        assertEquals(unit.hashCode(), unit2.hashCode());
    }
}