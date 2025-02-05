package adventure.v4c;

public interface Action {
    String getDescription();

    void perform(Pawn instigator);

    default void performIfPossible(Pawn instigator) {
        try {
            perform(instigator);
        } catch (IllegalArgumentException e) {
            // Ignore the exception
        }
    }
}
