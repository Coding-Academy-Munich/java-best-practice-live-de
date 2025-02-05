package adventure.v5c.strategies;

import adventure.v5c.Action;
import adventure.v5c.ActionTag;
import adventure.v5c.actions.HealAction;
import adventure.v5c.actions.MoveAction;
import adventure.v5c.actions.SkipTurnAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActionUtilsV5cTest {
    List<Action> actions;

    @BeforeEach
    void setUp() {
        actions = List.of(new MoveAction("north"), new HealAction());
    }

    @Test
    void selectActionByTag_tagPresent() {
        Action action = ActionUtils.selectActionWithTag(ActionTag.HELPFUL, actions);
        assertEquals(HealAction.class, action.getClass());
    }

    @Test
    void selectActionByTag_tagNotPresent() {
        Action action = ActionUtils.selectActionWithTag(ActionTag.AGGRESSIVE, actions);
        assertEquals(MoveAction.class, action.getClass());
    }

    @Test
    void selectActionByTag_noActionPresent() {
        Action action = ActionUtils.selectActionWithTag(ActionTag.AGGRESSIVE, List.of());
        assertEquals(SkipTurnAction.class, action.getClass());
    }
}