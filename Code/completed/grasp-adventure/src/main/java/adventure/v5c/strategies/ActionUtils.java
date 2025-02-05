package adventure.v5c.strategies;

import adventure.v5c.Action;
import adventure.v5c.ActionTag;
import adventure.v5c.actions.SkipTurnAction;

import java.util.List;

public class ActionUtils {
    public static Action selectActionWithTag(ActionTag tag, List<Action> actions) {
        if (actions.isEmpty()) {
            return new SkipTurnAction();
        }
        for (Action action : actions) {
            if (action.getTags().contains(tag)) {
                return action;
            }
        }
        return actions.get(0);
    }
}
