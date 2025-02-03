package recipes_sk;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OneTest {

    @Test
    void thingDddIsMinusOne() {
        One recipe = new One("Test Recipe", List.of(), "");
        assertEquals(-1, recipe.getDdd());
    }
}
