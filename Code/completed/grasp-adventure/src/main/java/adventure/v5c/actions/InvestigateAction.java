package adventure.v5c.actions;

import adventure.v5c.Action;
import adventure.v5c.ActionTag;
import adventure.v5c.Player;

import java.util.EnumSet;

public class InvestigateAction implements Action {
    @Override
    public String getDescription() {
        return "Investigate the current location.";
    }

    @Override
    public EnumSet<ActionTag> getTags() {
        return EnumSet.of(ActionTag.INVESTIGATE, ActionTag.AGGRESSIVE);
    }

    @Override
    public void perform(Player instigator) {
        // Investigate the room
    }
}
