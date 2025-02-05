package adventure.v5c.strategies;

import adventure.v5c.Action;
import adventure.v5c.Player;
import adventure.v5c.SelectActionStrategy;
import adventure.v5c.actions.InvestigateAction;
import adventure.v5c.actions.MoveAction;
import adventure.v5c.actions.SkipTurnAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SelectRandomActionStrategyTest {
    private Player player;
    private List<Action> actions;
    private SelectActionStrategy unit;

    @BeforeEach
    void setUp() {
        player = new Player("A Player", null);
        actions = List.of(new MoveAction("north"), new InvestigateAction(), new SkipTurnAction());
        unit = new SelectFirstActionStrategy();
    }

    @Test
    void selectAction_ifActionPresent() {
        Action action = unit.selectAction(player, actions);
        assertTrue(actions.contains(action));
    }


    @Test
    void selectAction_ifNoActionPresent() {
        Action action = unit.selectAction(player, List.of());
        assertNotNull(action);
        assertEquals(SkipTurnAction.class, action.getClass());
    }

    @Test
    void isInteraktive() {
        assertFalse(unit.isInteractive());
    }
}