import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class StateMachineTest {

    @Test
    void testStateMachineToDeliveryState() {
        StateMachine machine = new StateMachine();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        machine.insertCoin();
        machine.selectDrink();
        machine.takeDrink();

        assertEquals("Enjoy your drink!", outContent.toString().trim());
    }
}