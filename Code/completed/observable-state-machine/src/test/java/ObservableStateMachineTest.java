import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ObservableStateMachineTest {

    @Test
    void testObservableStateMachineTransitions() {
        TestNotifier notifier = new TestNotifier();
        ObservableStateMachine machine = new ObservableStateMachine(notifier);

        machine.insertCoin();
        machine.selectDrink();
        machine.takeDrink();

        List<State> expectedStates = List.of(
                State.CoinInserted,
                State.DrinkDelivered,
                State.Initial
        );

        assertEquals(expectedStates, notifier.getStateChanges());
    }

    @Test
    void testObservableStateMachineOutput() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ObservableStateMachine machine = new ObservableStateMachine();

        machine.insertCoin();
        machine.selectDrink();
        machine.takeDrink();

        assertEquals("Enjoy your drink!", outContent.toString().trim());
    }
}
