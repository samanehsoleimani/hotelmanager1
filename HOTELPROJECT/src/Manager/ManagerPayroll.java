package manager;

import common.EmployeePayroll;

import java.text.NumberFormat;
import java.util.Locale;

public class ManagerPayroll extends EmployeePayroll {
    private static final double MANAGEMENT_BONUS = 0.3; // 30% bonus for managers

    public ManagerPayroll(String name, String date) {
        super(name, "مدیر", date, 1000000);
    }

    @Override
    public double calculateNetSalary() {
        return super.calculateNetSalary() + (baseSalary * MANAGEMENT_BONUS);
    }

    @Override
    public void printPaySlip() {
        NumberFormat nf = NumberFormat.getInstance(new Locale("fa", "IR"));
        super.printPaySlip();
        System.out.println("پاداش مدیریت: " + nf.format(baseSalary * MANAGEMENT_BONUS) + " ریال");
    }
}