package adventure.v5c.strategies;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SelectActionInteractivelyV5cTest {

    @Test
    void isInteractive() {
        var unit = new SelectActionInteractively();
        assertTrue(unit.isInteractive());
    }
}