package adventure.v5c.strategies;

import adventure.v5c.Action;
import adventure.v5c.Player;
import adventure.v5c.SelectActionStrategy;
import adventure.v5c.actions.SkipTurnAction;

import java.util.List;

public class SelectFirstActionStrategy implements SelectActionStrategy {
    @Override
    public Action selectAction(Player player, List<Action> actions) {
        if (actions.isEmpty()) {
            return new SkipTurnAction();
        }
        return actions.get(0);
    }

    @Override
    public boolean isInteractive() {
        return false;
    }
}
