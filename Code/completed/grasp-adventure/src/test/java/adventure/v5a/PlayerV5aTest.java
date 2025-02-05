package adventure.v5a;

import adventure.v5a.actions.MoveAction;
import adventure.v5a.actions.SkipTurnAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlayerV5aTest {
    @SuppressWarnings("FieldCanBeLocal")
    private List<Map<String, Object>> locationData;
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
    void perform_skipTurn() {
        var unit = new Player("Test Player", room1);
        unit.perform(new SkipTurnAction());
        assertEquals(room1, unit.getLocation());
    }

    @Test
    void perform_validMove() {
        var unit = new Player("Test Player", room1);
        unit.perform(new MoveAction("north"));
        assertEquals(room2, unit.getLocation());
    }

    @Test
    void perform_invalidMove() {
        var unit = new Player("Test Player", room1);
        assertThrows(IllegalArgumentException.class, () -> unit.perform(new MoveAction("nowhere")));
    }

    @Test
    void performIfPossible_validMove() {
        var unit = new Player("Test Player", room1);
        unit.performIfPossible(new MoveAction("north"));
        assertEquals(room2, unit.getLocation());
    }

    @Test
    void performIfPossible_invalidMove() {
        var unit = new Player("Test Player", room1);
        unit.performIfPossible(new MoveAction("nowhere"));
        assertEquals(room1, unit.getLocation());
    }

    @Test
    void selectAction_actionsAvailable() {
        List<Action> actions = List.of(new MoveAction("north"), new SkipTurnAction());
        var unit = new Player("Test Player", room1);

        Action action = unit.selectAction(actions);

        assertEquals(MoveAction.class, action.getClass());
        assertTrue(action.getDescription().contains("north"));
    }

    @Test
    void selectAction_noActionsAvailable() {
        var unit = new Player("Test Player", room1);
        Action action = unit.selectAction(List.of());
        assertEquals(SkipTurnAction.class, action.getClass());
    }

    @Test
    void takeTurn_singleTurn() {
        var unit = new Player("Test Player", room1);
        unit.takeTurn();
        assertEquals(room2, unit.getLocation());
    }
}
