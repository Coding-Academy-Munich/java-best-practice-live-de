package adventure.v4a;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PawnV4aTest {
    @SuppressWarnings("FieldCanBeLocal")
    private List<Map<String, Object>> locationData;
    // private World world;
    private Location room1;
    private Location room2;

    @BeforeEach
    void setUp() {
        locationData = List.of(
                Map.of("name", "Room 1", "description", "This is a room", "connections", Map.of("north", "Room 2")),
                Map.of("name", "Room 2", "description", "This is another room", "connections",
                        Map.of("south", "Room 1")));
        World world = WorldFactory.fromLocationData(locationData);
        room1 = world.getLocationByName("Room 1");
        room2 = world.getLocationByName("Room 2");
    }

    @Test
    void move_validMove() {
        var unit = new Pawn("Test Pawn", room1);
        unit.move("north");
        assertEquals(room2, unit.getLocation());
    }

    @Test
    void move_invalidMove() {
        var unit = new Pawn("Test Pawn", room1);
        assertThrows(IllegalArgumentException.class, () -> unit.move("east"));
    }

    @Test
    void moveIfPossible_validMove() {
        var unit = new Pawn("Test Pawn", room1);
        unit.moveIfPossible("north");
        assertEquals(room2, unit.getLocation());
    }

    @Test
    void moveIfPossible_invalidMove() {
        var unit = new Pawn("Test Pawn", room1);
        unit.moveIfPossible("east");
        assertEquals(room1, unit.getLocation());
    }
}
