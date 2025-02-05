package adventure.v5c;

import adventure.v5c.actions.*;
import adventure.v5c.strategies.SelectFirstActionStrategy;

import java.util.List;
import java.util.stream.Collectors;

public class Player {
    private final String name;
    private Location location;
    private SelectActionStrategy strategy = new SelectFirstActionStrategy();
    private boolean isDebugModeActive = false;

    public Player(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public void moveToLocation(Location connectedLocation) {
        location = connectedLocation;
    }

    @SuppressWarnings("unused")
    public SelectActionStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(SelectActionStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean isInteractive() {
        return strategy.isInteractive();
    }

    public boolean isDebugModeActive() {
        return isDebugModeActive;
    }

    @SuppressWarnings("unused")
    public void setDebugModeActive(boolean debugModeActive) {
        isDebugModeActive = debugModeActive;
    }

    public void takeTurn() {
        List<Action> possibleActions = getPossibleActions();
        Action action = selectAction(possibleActions);
        performIfPossible(action);
    }

    public List<Action> getPossibleActions() {
        List<Action> actions = getLocation().getConnectedDirections().stream().map(MoveAction::new).collect(
                Collectors.toList());
        actions.add(new InvestigateAction());
        actions.add(new SkipTurnAction());
        if (isInteractive()) {
            actions.add(new QuitAction());
            if (isDebugModeActive()) {
                actions.add(new ErrorAction());
            }
        }
        return actions;
    }

    public Action selectAction(List<Action> actions) {
        return strategy.selectAction(this, actions);
    }

    public void perform(Action action) {
        action.perform(this);
    }

    public void performIfPossible(Action action) {
        action.performIfPossible(this);
    }

    @Override
    public String toString() {
        return String.format("Player{name='%s', location='%s'}", getName(), getLocation());
    }
}
