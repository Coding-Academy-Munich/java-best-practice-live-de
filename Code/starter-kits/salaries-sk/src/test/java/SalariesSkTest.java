import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class SalariesSkTest {

    @Test
    void testHandleMoneyStuff() {
        List<Double> salaries = new ArrayList<>();
        double tax = SalariesSk.handleMoneyStuff(3, 200.0, "John Doe", salaries);

        assertEquals(0.0, tax, 0.001);
        assertEquals(1, salaries.size());
        assertEquals(400.0, salaries.get(0), 0.001);
    }

    @Test
    void testHandleMoneyStuffMultipleCalls() {
        List<Double> salaries = new ArrayList<>();

        SalariesSk.handleMoneyStuff(2, 300.0, "Jane Doe", salaries);
        double tax = SalariesSk.handleMoneyStuff(5, 300.0, "John Smith", salaries);

        assertEquals(120.0, tax, 0.001);
        assertEquals(2, salaries.size());
        assertEquals(300.0, salaries.get(0), 0.001);
        assertEquals(1080.0, salaries.get(1), 0.001);
    }

    @Test
    void testHandleMoneyStuffDifferentTaxBrackets() {
        List<Double> salaries = new ArrayList<>();

        double tax1 = SalariesSk.handleMoneyStuff(2, 200.0, "Low Earner", salaries);
        double tax2 = SalariesSk.handleMoneyStuff(3, 300.0, "Mid Earner", salaries);
        double tax3 = SalariesSk.handleMoneyStuff(4, 500.0, "High Earner", salaries);

        assertEquals(0.0, tax1, 0.001);
        assertEquals(30.0, tax2, 0.001);
        assertEquals(150.0, tax3, 0.001);

        assertEquals(3, salaries.size());
        assertEquals(200.0, salaries.get(0), 0.001);
        assertEquals(570.0, salaries.get(1), 0.001);
        assertEquals(1350.0, salaries.get(2), 0.001);
    }
}
