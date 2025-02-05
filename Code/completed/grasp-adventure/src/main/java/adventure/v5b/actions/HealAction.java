package adventure.v5b.actions;

import adventure.v5b.Action;
import adventure.v5b.ActionTag;
import adventure.v5b.Player;

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
