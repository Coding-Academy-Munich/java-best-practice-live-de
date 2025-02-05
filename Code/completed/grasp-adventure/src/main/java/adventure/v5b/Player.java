package adventure.v5b;

import adventure.v5b.actions.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @SuppressWarnings("unused")
    public PlayerStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(PlayerStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean isInteractive() {
        return strategy == PlayerStrategy.INTERACTIVE;
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
        if (actions.isEmpty()) {
            return new SkipTurnAction();
        }
        return switch (strategy) {
            case INTERACTIVE -> selectActionInteractively(actions);
            case FIRST_ACTION -> actions.get(0);
            case RANDOM_ACTION -> actions.get((int) (Math.random() * actions.size()));
            case AGGRESSIVE -> selectActionWithTag(ActionTag.AGGRESSIVE, actions);
            case HELPFUL -> selectActionWithTag(ActionTag.HELPFUL, actions);
        };
    }

    private Action selectActionInteractively(List<Action> actions) {
        int selectedActionIndex;
        while (true) {
            System.out.println("Available actions:");
            for (int i = 0; i < actions.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, actions.get(i).getDescription());
            }
            System.out.print("Select action: ");
            try {
                selectedActionIndex = Integer.parseInt(System.console().readLine());
                if (selectedActionIndex < 1 || selectedActionIndex > actions.size()) {
                    System.out.println("Please enter a valid action number.");
                    continue;
                }
                return actions.get(selectedActionIndex - 1);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private Action selectActionWithTag(ActionTag tag, List<Action> actions) {
        for (Action action : actions) {
            if (action.getTags().contains(tag)) {
                return action;
            }
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

    private final Pawn pawn;
    private PlayerStrategy strategy = PlayerStrategy.FIRST_ACTION;
    private boolean isDebugModeActive = false;
}
