import java.util.ArrayList;
import java.util.List;

class TestNotifier implements StateNotifier {
    private final List<State> stateChanges = new ArrayList<>();

    @Override
    public void notify(State state) {
        stateChanges.add(state);
    }

    public List<State> getStateChanges() {
        return stateChanges;
    }
}