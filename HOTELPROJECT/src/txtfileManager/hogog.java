import common.EmployeePayroll;
import manager.ManagerPayroll;
import java.io.*;
import java.text.*;
import java.util.*;
import java.time.*;

public class Main {
    private static final String FILE_NAME = "payroll_data.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, "UTF-8");

        while (true) {
            System.out.println("\n--- سیستم پرداخت حقوق ---");
            System.out.println("1. ثبت پرداخت جدید");
            System.out.println("2. نمایش لیست پرداخت‌ها");
            System.out.println("3. خروج");
            System.out.print("گزینه مورد نظر را انتخاب نمایید (1-3): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // مصرف خط جدید

            switch (choice) {
                case 1:
                    processNewPayment(scanner);
                    break;
                case 2:
                    displayPaymentList();
                    break;
                case 3:
                    System.out.println("خروج از برنامه...");
                    scanner.close();
                    return;
                default:
                    System.out.println("گزینه نامعتبر!");
            }
        }
    }

    private static void processNewPayment(Scanner scanner) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("fa", "IR"));

        System.out.print("\nنام کارمند را وارد نمایید: ");
        String employeeName = scanner.nextLine();

        System.out.println("\nانتخاب شغل:");
        System.out.println("1. مدیر (حقوق پایه: 1,000,000 ریال)");
        System.out.println("2. مسئول رزرویشن (حقوق پایه: 700,000 ریال)");
        System.out.println("3. نگهبان (حقوق پایه: 500,000 ریال)");
        System.out.println("4. خدمتکار (حقوق پایه: 300,000 ریال)");
        System.out.print("شغل را انتخاب نمایید (1-4): ");

        int jobChoice = scanner.nextInt();
        String date = LocalDate.now().toString();
        EmployeePayroll payroll;

        if (jobChoice == 1) {
            payroll = new ManagerPayroll(employeeName, date);
        } else {
            String jobTitle = "";
            double baseSalary = 0;

            switch (jobChoice) {
                case 2:
                    jobTitle = "مسئول رزرویشن";
                    baseSalary = 700000;
                    break;
                case 3:
                    jobTitle = "نگهبان";
                    baseSalary = 500000;
                    break;
                case 4:
                    jobTitle = "خدمتکار";
                    baseSalary = 300000;
                    break;
                default:
                    System.out.println("گزینه نامعتبر! به طور پیش‌فرض خدمتکار در نظر گرفته می‌شود.");
                    jobTitle = "خدمتکار";
                    baseSalary = 300000;
            }
            payroll = new EmployeePayroll(employeeName, jobTitle, date, baseSalary);
        }

        processEmployee(scanner, payroll);
        saveToFile(payroll);

        System.out.println("\nاطلاعات با موفقیت در فایل ذخیره شد.");
    }

    private static void displayPaymentList() {
        System.out.println("\n--- لیست پرداخت‌های ثبت شده ---");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(FILE_NAME), "UTF-8"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("هنوز هیچ پرداختی ثبت نشده است.");
        } catch (IOException e) {
            System.out.println("خطا در خواندن فایل: " + e.getMessage());
        }
    }

    private static void processEmployee(Scanner scanner, EmployeePayroll payroll) {
        System.out.print("\nتعداد ساعت کارکرد را وارد نمایید: ");
        int hoursWorked = scanner.nextInt();
        payroll.calculateSalary(hoursWorked);

        System.out.print("مبلغ پاداش را وارد نمایید: ");
        double bonus = scanner.nextDouble();
        payroll.setBonus(bonus);

        System.out.print("مبلغ کسورات را وارد نمایید: ");
        double deductions = scanner.nextDouble();
        payroll.setDeductions(deductions);

        scanner.nextLine();
        System.out.println("\nروش پرداخت:");
        System.out.println("1. واریز به حساب بانکی");
        System.out.println("2. پرداخت نقدی");
        System.out.print("گزینه مورد نظر را انتخاب نمایید (1 یا 2): ");
        String paymentMethod = scanner.nextLine();

        String cardNumber = "";
        if (paymentMethod.equals("1")) {
            System.out.print("شماره کارت بانکی را وارد نمایید (16 رقم): ");
            cardNumber = scanner.nextLine();

            while (cardNumber.length() != 16 || !cardNumber.matches("\\d+")) {
                System.out.print("شماره کارت نامعتبر! لطفاً 16 رقم وارد نمایید: ");
                cardNumber = scanner.nextLine();
            }
        }

        payroll.setPaymentMethod(paymentMethod, cardNumber);

        System.out.println("\n\n----- فیش حقوقی -----");
        payroll.printPaySlip();
    }

    private static void saveToFile(EmployeePayroll payroll) {
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(FILE_NAME, true), "UTF-8"))) {

            writer.println("--- رکورد جدید ---");
            writer.println("تاریخ: " + payroll.date);
            writer.println("نام: " + payroll.employeeName);
            writer.println("شغل: " + payroll.jobTitle);
            writer.println("حقوق پایه: " + payroll.baseSalary);

            if (payroll instanceof ManagerPayroll) {
                writer.println("نوع: مدیر (با پاداش ویژه)");
            } else {
                writer.println("نوع: کارمند عادی");
            }

            writer.println("حقوق خالص: " + payroll.calculateNetSalary());
            writer.println("------------------");
        } catch (IOException e) {
            System.out.println("خطا در ذخیره فایل: " + e.getMessage());
        }
    }
}