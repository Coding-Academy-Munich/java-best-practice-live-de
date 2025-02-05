package adventure.v5c;

import java.util.EnumSet;

public interface Action {
    String getDescription();
    EnumSet<ActionTag> getTags();

    void perform(Player instigator);


    default void performIfPossible(Player instigator) {
        try {
            perform(instigator);
        } catch (IllegalArgumentException e) {
            // Ignore the exception
        }
    }
}
