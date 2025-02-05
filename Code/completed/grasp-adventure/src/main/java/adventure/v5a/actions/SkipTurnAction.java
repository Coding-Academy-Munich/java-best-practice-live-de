package adventure.v5a.actions;

import adventure.v5a.Action;
import adventure.v5a.Player;

public class SkipTurnAction implements Action {
    @Override
    public String getDescription() {
        return "Skip a turn";
    }

    @Override
    public void perform(Player instigator) {
        // Do nothing.
    }
}
