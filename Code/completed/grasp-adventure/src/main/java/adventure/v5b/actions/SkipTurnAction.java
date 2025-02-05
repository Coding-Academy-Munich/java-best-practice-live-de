package adventure.v5b.actions;

import adventure.v5b.Action;
import adventure.v5b.ActionTag;
import adventure.v5b.Player;

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
