package adventure.v5b.actions;

import adventure.v5b.Action;
import adventure.v5b.ActionTag;
import adventure.v5b.Player;

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
