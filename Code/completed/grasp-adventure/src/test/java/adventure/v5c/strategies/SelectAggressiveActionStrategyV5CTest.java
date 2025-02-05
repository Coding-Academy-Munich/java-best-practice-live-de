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

class SelectAggressiveActionStrategyV5CTest {
    private Player player;
    private List<Action> actions;
    private SelectActionStrategy unit;

    @BeforeEach
    void setUp() {
        player = new Player("A Player", null);
        actions = List.of(new MoveAction("north"), new SkipTurnAction());
        unit = new SelectAggressiveActionStrategy();
    }

    @Test
    void selectAction_ifAggressiveActionPresent() {
        actions = List.of(new MoveAction("north"), new InvestigateAction(), new SkipTurnAction());
        Action action = unit.selectAction(player, actions);
        assertEquals(InvestigateAction.class, action.getClass());
    }

    @Test
    void selectAction_ifAggressiveActionNotPresent() {
        Action action = unit.selectAction(player, actions);
        assertEquals(MoveAction.class, action.getClass());
    }

    @Test
    void selectAction_ifNoActionPresent() {
        Action action = unit.selectAction(player, List.of());
        assertEquals(SkipTurnAction.class, action.getClass());
    }

    @Test
    void isInteraktive() {
        assertFalse(unit.isInteractive());
    }
}