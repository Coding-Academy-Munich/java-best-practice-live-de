import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JUnitBasicsTest {
    @Test
    public void testAddition() {
        assertEquals(4, 2 + 2);
    }

    @SuppressWarnings("ConstantValue")
    @Test
    public void testTrueCondition() {
        assertTrue(5 > 3);
    }

    @Test
    public void testFalseCondition() {
        //noinspection ConstantValue
        assertFalse(2 > 5);
    }

    @Test
    public void testException() {
        assertThrows(ArithmeticException.class, () -> {
            @SuppressWarnings({"NumericOverflow", "unused", "divzero"}) int result = 1 / 0;
        });
    }
}