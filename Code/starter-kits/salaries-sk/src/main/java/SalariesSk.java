import java.util.List;

public class SalariesSk {
    // Get days of week (iDow), the salary per day (dSpd), the name of the
    // employee (pcN) and an input/output list of salaries paid so far. The
    // new salary is appended to this list. Returns the tax.
    public static double handleMoneyStuff(
            int iDow, double dSpd, String pcN, List<Double> dvSlrs
    ) {
        String[] saDns = {"Mon", "Tue", "Wed", "Thu", "Fri"};
        // We count Sunday as 1, Monday as 2, etc. but the work week starts on Monday
        String srD = saDns[iDow - 2];
        // Compute the salary so far, based on the day of week and the salary per day
        double dSsf = (iDow - 1) * dSpd;
        // Compute the tax
        double dT = 0.0;
        if (dSsf > 500.0 && dSsf <= 1000.0) {
            dT = dSsf * 0.05;
        } else if (dSsf > 500.0 && dSsf <= 2000.0) {
            dT = dSsf * 0.1;
        } else if (dSsf > 500.0) {
            dT = dSsf * 0.15;
        }
        // Update the salary based on the tax to pay
        dSsf = dSsf - dT;
        // Add the salary to the list of all salaries paid
        dvSlrs.add(dSsf);
        System.out.println(pcN + " worked till " + srD + " and earned $" + dSsf + " this week.");
        System.out.println("  Their taxes were $" + dT + ".");
        return dT;
    }
}
