package adventure.v4c.actions;

import adventure.v4c.Action;
import adventure.v4c.Pawn;

public class SkipTurnAction implements Action {
    @Override
    public String getDescription() {
        return "Skip a turn";
    }

    @Override
    public void perform(Pawn instigator) {
        // Do nothing.
    }
}
