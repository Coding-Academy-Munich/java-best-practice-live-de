package adventure.v5c.strategies;

import adventure.v5c.Action;
import adventure.v5c.ActionTag;
import adventure.v5c.Player;
import adventure.v5c.SelectActionStrategy;

import java.util.List;

public class SelectHelpfulActionStrategy implements SelectActionStrategy {
    @Override
    public Action selectAction(Player player, List<Action> actions) {
        return ActionUtils.selectActionWithTag(ActionTag.HELPFUL, actions);
    }

    @Override
    public boolean isInteractive() {
        return false;
    }
}
