package adventure.v5c.strategies;

import adventure.v5c.Action;
import adventure.v5c.Player;
import adventure.v5c.SelectActionStrategy;

import java.util.List;

public class SelectRandomActionStrategy implements SelectActionStrategy {
    @Override
    public Action selectAction(Player player, List<Action> actions) {
        return actions.get((int) (Math.random() * actions.size()));
    }

    @Override
    public boolean isInteractive() {
        return false;
    }
}
