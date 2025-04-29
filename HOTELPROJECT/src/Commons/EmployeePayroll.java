package common;

import java.text.NumberFormat;
import java.util.Locale;

public class EmployeePayroll {
    public String employeeName;
    public String jobTitle;
    public String date;
    public double baseSalary;
    protected double overtime;
    protected double bonus;
    protected double deductions;
    protected String paymentMethod;
    protected String cardNumber;

    public EmployeePayroll(String name, String job, String date, double salary) {
        this.employeeName = name;
        this.jobTitle = job;
        this.date = date;
        this.baseSalary = salary;
    }

    public void calculateSalary(int hoursWorked) {
        double hourlyRate = baseSalary / 160;
        this.overtime = (hoursWorked > 160) ? (hoursWorked - 160) * hourlyRate * 1.5 : 0;
    }

    public void setBonus(double amount) {
        this.bonus = amount;
    }

    public void setDeductions(double amount) {
        this.deductions = amount;
    }

    public void setPaymentMethod(String method, String card) {
        if (method.equals("1") || method.equals("2")) {
            this.paymentMethod = method;
            this.cardNumber = card;
        } else {
            throw new IllegalArgumentException("گزینه پرداخت نامعتبر است!");
        }
    }

    public double calculateNetSalary() {
        return baseSalary + overtime + bonus - deductions;
    }

    public void printPaySlip() {
        NumberFormat nf = NumberFormat.getInstance(new Locale("fa", "IR"));

        System.out.println("نام کارمند: " + employeeName);
        System.out.println("شغل: " + jobTitle);
        System.out.println("تاریخ: " + date);
        System.out.println("\nحقوق پایه: " + nf.format(baseSalary) + " ریال");
        System.out.println("اضافه کار: " + nf.format(overtime) + " ریال");
        System.out.println("پاداش: " + nf.format(bonus) + " ریال");
        System.out.println("کسورات: " + nf.format(deductions) + " ریال");
        System.out.println("-------------------------------");
        System.out.println("حقوق خالص: " + nf.format(calculateNetSalary()) + " ریال");
        System.out.println("\nروش پرداخت:");
        if (paymentMethod.equals("1")) {
            System.out.println("واریز به حساب بانکی");
            System.out.println("شماره کارت: " + cardNumber.replaceAll("(\\d{4})(?=\\d)", "$1-"));
        } else {
            System.out.println("پرداخت نقدی");
        }
    }
}