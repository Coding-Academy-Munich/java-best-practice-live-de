package adventure.v4c.actions;

import adventure.v4c.Action;
import adventure.v4c.Pawn;

public class MoveAction implements Action {
    public MoveAction(String direction) {
        this.direction = direction;
    }

    @Override
    public String getDescription() {
        return String.format("Move the player in direction %s.", direction);
    }

    @Override
    public void perform(Pawn instigator) {
        instigator.moveToLocation(instigator.getLocation().getConnectedLocation(direction));
    }

    private final String direction;
}
