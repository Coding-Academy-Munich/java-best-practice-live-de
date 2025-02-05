package adventure.v5c.actions;

import adventure.v5c.Action;
import adventure.v5c.ActionTag;
import adventure.v5c.Player;

import java.util.EnumSet;

public class SkipTurnAction implements Action {
    @Override
    public String getDescription() {
        return "Skip a turn";
    }

    @Override
    public EnumSet<ActionTag> getTags() {
        return EnumSet.of(ActionTag.REST);
    }

    @Override
    public void perform(Player instigator) {
        // Do nothing.
    }
}
