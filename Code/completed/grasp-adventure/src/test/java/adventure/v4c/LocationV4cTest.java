package adventure.v4c;

import adventure.v4b.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LocationV4cTest {
    private Map<String, Object> locationData;

    @BeforeEach
    void setUp() {
        locationData = Map.of(
                "name", "Room 1", "description", "This is a room", "connections", Map.of("north", "Room 2"));
    }

    @Test
    void fromData() {
        adventure.v4b.Location unit = adventure.v4b.Location.fromData(locationData);

        assertEquals("Room 1", unit.name());
        assertEquals("This is a room", unit.description());

        // The location factory method does not fill in the connected locations, since it has no way of referencing
        // other locations by name. This is later backfilled by the World constructor
        assertTrue(unit.connectedLocations().isEmpty());
        assertTrue(unit.getConnectedDirections().isEmpty());
    }

    @Test
    void getConnectedLocation_raisesErrorForBadDirection() {
        adventure.v4b.Location unit = adventure.v4b.Location.fromData(locationData);
        assertThrows(IllegalArgumentException.class, () -> unit.getConnectedLocation("nowhere"));
    }

    @Test
    void connectedDirections() {
        adventure.v4b.Location room2 = adventure.v4b.Location.fromData(Map.of("name", "Room 2"));
        adventure.v4b.Location unit = adventure.v4b.Location.fromData(locationData);
        unit.setConnectedLocation("north", room2);

        assertEquals(List.of("north"), unit.getConnectedDirections());
        assertEquals(room2, unit.getConnectedLocation("north"));
    }

    @Test
    void testToString() {
        adventure.v4b.Location unit = adventure.v4b.Location.fromData(locationData);

        assertTrue(unit.toString().contains("Room 1"));
        assertTrue(unit.toString().contains("This is a room"));
    }

    @Test
    void testEquals() {
        adventure.v4b.Location unit = adventure.v4b.Location.fromData(locationData);
        adventure.v4b.Location unit2 = Location.fromData(locationData);

        assertNotSame(unit, unit2);
        assertEquals(unit, unit2);
        assertEquals(unit.hashCode(), unit2.hashCode());
    }
}