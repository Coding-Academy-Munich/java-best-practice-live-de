package adventure.v5c.actions;

import adventure.v5c.Action;
import adventure.v5c.ActionTag;
import adventure.v5c.Player;

import java.util.EnumSet;

public class QuitAction implements Action {
    @Override
    public String getDescription() {
        return "Exit the game.";
    }

    @Override
    public EnumSet<ActionTag> getTags() {
        return EnumSet.of(ActionTag.QUIT);
    }

    @Override
    public void perform(Player instigator) {

    }
}
