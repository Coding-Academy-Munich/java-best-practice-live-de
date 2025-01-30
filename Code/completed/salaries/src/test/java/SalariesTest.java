import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class SalariesTest {

    @Test
    void testPrintAndSaveSalaryReturnTaxesForLowTaxBracket() {
        List<Double> salaries = new ArrayList<>();
        double tax = Salaries.printAndSaveSalaryReturnTaxes(
                3, 200.0, "John Doe", salaries);

        assertEquals(0.0, tax, 0.001);
        assertEquals(1, salaries.size());
        assertEquals(400.0, salaries.get(0), 0.001);
    }

    @Test
    void testPrintAndSaveSalaryReturnTaxesForLowAndSaveSalaryAndMediumTaxBrackets() {
        List<Double> salaries = new ArrayList<>();

        double tax0 = Salaries.printAndSaveSalaryReturnTaxes(
                2, 300.0, "Jane Doe", salaries);
        double tax1 = Salaries.printAndSaveSalaryReturnTaxes(
                5, 300.0, "John Smith", salaries);

        assertEquals(0.0, tax0, 0.001);
        assertEquals(120.0, tax1, 0.001);
        assertEquals(2, salaries.size());
        assertEquals(300.0, salaries.get(0), 0.001);
        assertEquals(1080.0, salaries.get(1), 0.001);
    }

    @Test
    void testPrintAndSaveSalaryReturnDifferentTaxBrackets() {
        List<Double> salaries = new ArrayList<>();

        double tax1 = Salaries.printAndSaveSalaryReturnTaxes(
                2, 200.0, "Low Earner", salaries);
        double tax2 = Salaries.printAndSaveSalaryReturnTaxes(
                3, 300.0, "Mid Earner", salaries);
        double tax3 = Salaries.printAndSaveSalaryReturnTaxes(
                4, 500.0, "High Earner", salaries);

        assertEquals(0.0, tax1, 0.001);
        assertEquals(30.0, tax2, 0.001);
        assertEquals(150.0, tax3, 0.001);

        assertEquals(3, salaries.size());
        assertEquals(200.0, salaries.get(0), 0.001);
        assertEquals(570.0, salaries.get(1), 0.001);
        assertEquals(1350.0, salaries.get(2), 0.001);
    }
}
