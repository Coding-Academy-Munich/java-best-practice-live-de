package adventure.v5a.actions;

import adventure.v5a.Action;
import adventure.v5a.Player;

public class MoveAction implements Action {
    public MoveAction(String direction) {
        this.direction = direction;
    }

    @Override
    public String getDescription() {
        return String.format("Move the player in direction %s.", direction);
    }

    @Override
    public void perform(Player instigator) {
        instigator.getPawn().moveToLocation(instigator.getLocation().getConnectedLocation(direction));
    }

    private final String direction;
}
