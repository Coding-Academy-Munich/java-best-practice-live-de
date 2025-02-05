package adventure.v5b.actions;

import adventure.v5b.Action;
import adventure.v5b.ActionTag;
import adventure.v5b.Player;

import java.util.EnumSet;

public class ErrorAction implements Action {
    @Override
    public String getDescription() {
        return "Raise an error for testing purposes.";
    }

    @Override
    public EnumSet<ActionTag> getTags() {
        return EnumSet.of(ActionTag.ERROR, ActionTag.DEBUG_ONLY);
    }

    @Override
    public void perform(Player instigator) {
        throw new RuntimeException("Error raised by error action.");
    }
}
