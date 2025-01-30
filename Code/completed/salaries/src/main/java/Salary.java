public class Salary {
    Salary(double salary) {
        grossSalary = salary;
        taxRate = computeTaxRate(grossSalary);
        taxes = grossSalary * taxRate;
        netSalary = grossSalary - taxes;
    }

    public static double computeTaxRate(double salary) {
        if (salary <= 500.0) {
            return 0.0;
        } else if (salary <= 1000.0) {
            return 0.05;
        } else if (salary <= 2000.0) {
            return 0.1;
        } else {
            return 0.15;
        }
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public double getTaxes() {
        return taxes;
    }

    public double getNetSalary() {
        return netSalary;
    }

    private final double grossSalary;
    private final double taxRate;
    private final double taxes;
    private final double netSalary;
}
