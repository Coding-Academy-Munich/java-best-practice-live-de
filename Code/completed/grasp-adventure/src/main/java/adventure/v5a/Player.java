package adventure.v5a;

import adventure.v5a.actions.MoveAction;
import adventure.v5a.actions.SkipTurnAction;

import java.util.stream.Collectors;
import java.util.List;

public class Player {
    public Player(String name, Location location) {
        this(new Pawn(name, location));
    }

    public Player(Pawn pawn) {
        this.pawn = pawn;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }

    public void takeTurn() {
        List<Action> possibleActions = getPossibleActions();
        Action action = selectAction(possibleActions);
        performIfPossible(action);
    }

    public List<Action> getPossibleActions() {
        return getLocation().getConnectedDirections().stream().map(MoveAction::new).collect(Collectors.toList());
    }

    public Action selectAction(List<Action> actions) {
        if (actions.isEmpty()) {
            return new SkipTurnAction();
        }
        return actions.get(0);
    }

    public void perform(Action action) {
        action.perform(this);
    }

    public void performIfPossible(Action action) {
        action.performIfPossible(this);
    }

    public String getName() {
        return pawn.getName();
    }

    public Location getLocation() {
        return pawn.getLocation();
    }

    @Override
    public String toString() {
        return String.format("Player{name='%s', location='%s'}", getName(), getLocation());
    }

    private Pawn pawn;
}
