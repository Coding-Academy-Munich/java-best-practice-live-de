package adventure.v5c;

import java.util.List;

public interface SelectActionStrategy {
    Action selectAction(Player player, List<Action> actions);
    boolean isInteractive();
}
