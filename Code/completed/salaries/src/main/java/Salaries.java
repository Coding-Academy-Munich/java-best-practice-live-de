import java.util.List;

public class Salaries {
    public static double printAndSaveSalaryReturnTaxes(
            int dayOfWeekIndex,
            double salaryPerDay,
            String employeeName,
            List<Double> salariesPaidSoFar) {
        Salary salary = computeSalary(dayOfWeekIndex, salaryPerDay);
        salariesPaidSoFar.add(salary.getNetSalary());
        printSalary(dayOfWeekIndex, employeeName, salary);
        return salary.getTaxes();
    }

    private static void printSalary(
            int dayOfWeekIndex, String employeeName, Salary salary) {
        DayOfWeek dayOfWeek = DayOfWeek.fromIndex(dayOfWeekIndex);
        System.out.println(
                employeeName + " worked till " + dayOfWeek + " and earned $"
                        + salary.getNetSalary() + " (net) this week.");
        System.out.println(
                "  Their gross salary was $" + salary.getGrossSalary() + " and taxes "
                        + "were $" + salary.getTaxes() + ".");
    }

    private static Salary computeSalary(int dayOfWeekIndex, double salaryPerDay) {
        int numberOfDaysWorked = dayOfWeekIndex - 1;
        return new Salary(salaryPerDay * numberOfDaysWorked);
    }
}
