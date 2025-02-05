package adventure.v5c.strategies;

import adventure.v5c.Action;
import adventure.v5c.Player;
import adventure.v5c.SelectActionStrategy;

import java.util.List;

public class SelectActionInteractively implements SelectActionStrategy {
    @Override
    public Action selectAction(Player player, List<Action> actions) {
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

    @Override
    public boolean isInteractive() {
        return true;
    }
}
