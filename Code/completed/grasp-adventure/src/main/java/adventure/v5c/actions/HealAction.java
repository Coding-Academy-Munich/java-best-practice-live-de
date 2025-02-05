package adventure.v5c.actions;

import adventure.v5c.Action;
import adventure.v5c.ActionTag;
import adventure.v5c.Player;

import java.util.EnumSet;

public class HealAction implements Action {
    @Override
    public String getDescription() {
        return "Heal all characters.";
    }

    @Override
    public EnumSet<ActionTag> getTags() {
        return EnumSet.of(ActionTag.HELPFUL);
    }

    @Override
    public void perform(Player instigator) {
        // Heal everybody...
    }
}
