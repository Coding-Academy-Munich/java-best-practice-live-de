package adventure.v5a;

public interface Action {
    String getDescription();

    void perform(Player instigator);

    default void performIfPossible(Player instigator) {
        try {
            perform(instigator);
        } catch (IllegalArgumentException e) {
            // Ignore the exception
        }
    }
}
