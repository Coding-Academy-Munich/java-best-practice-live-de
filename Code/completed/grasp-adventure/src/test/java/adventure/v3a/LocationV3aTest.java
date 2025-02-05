package adventure.v3a;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LocationV3aTest {
    private Map<String, Object> locationData;

    @BeforeEach
    void setUp() {
        locationData = Map.of("name", "Room 1", "description", "This is a room");
    }

    @Test
    void fromData() {
        Location unit = Location.fromData(locationData);

        assertEquals("Room 1", unit.name());
        assertEquals("This is a room", unit.description());
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