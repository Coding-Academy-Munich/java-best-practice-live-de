package adventure.v5c.actions;

import adventure.v5c.Action;
import adventure.v5c.ActionTag;
import adventure.v5c.Player;

import java.util.EnumSet;

public class MoveAction implements Action {
    public MoveAction(String direction) {
        this.direction = direction;
    }

    @Override
    public String getDescription() {
        return String.format("Move the player in direction %s.", direction);
    }

    @Override
    public EnumSet<ActionTag> getTags() {
        return EnumSet.of(ActionTag.MOVE);
    }

    @Override
    public void perform(Player instigator) {
        instigator.moveToLocation(instigator.getLocation().getConnectedLocation(direction));
    }

    private final String direction;
}
