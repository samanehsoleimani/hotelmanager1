package manager;

import common.Employee;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeManagementSystem {
    private List<Employee> employees = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private static final String DATA_FILE = "employees.dat";

    public EmployeeManagementSystem() {
        loadEmployees();
    }

    public void start() {
        while (true) {
            System.out.println("\n*** سیستم مدیریت کارمندان هتل ***");
            System.out.println("1. ثبت کارمند جدید");
            System.out.println("2. نمایش همه کارمندان");
            System.out.println("3. نمایش بر اساس سمت");
            System.out.println("4. خروج");
            System.out.print("لطفاً انتخاب کنید: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    displayAllEmployees();
                    break;
                case 3:
                    displayByPosition();
                    break;
                case 4:
                    System.out.println("خروج از سیستم...");
                    System.exit(0);
                default:
                    System.out.println("⚠ انتخاب نامعتبر!");
            }
        }
    }

    private void addEmployee() {
        System.out.println("\n--- ثبت کارمند جدید ---");
        System.out.print("نام: ");
        String firstName = scanner.nextLine();

        System.out.print("نام خانوادگی: ");
        String lastName = scanner.nextLine();

        System.out.print("کد ملی: ");
        String nationalId = scanner.nextLine();

        System.out.print("سابقه کار (سال): ");
        int experience = Integer.parseInt(scanner.nextLine());

        System.out.println("\nانتخاب سمت:");
        System.out.println("1. مدیر");
        System.out.println("2. مسئول رزرویشن");
        System.out.println("3. نگهبان");
        System.out.println("4. خدمتکار");
        System.out.print("انتخاب کنید (1-4): ");
        int positionChoice = Integer.parseInt(scanner.nextLine());

        String position = "";
        double baseSalary = 0;
        switch(positionChoice) {
            case 1:
                position = "مدیر";
                baseSalary = 1000000;
                break;
            case 2:
                position = "مسئول رزرویشن";
                baseSalary = 700000;
                break;
            case 3:
                position = "نگهبان";
                baseSalary = 500000;
                break;
            case 4:
                position = "خدمتکار";
                baseSalary = 300000;
                break;
            default:
                System.out.println("⚠ انتخاب نامعتبر! به عنوان خدمتکار ثبت می‌شود.");
                position = "خدمتکار";
                baseSalary = 300000;
        }

        Employee employee = new Employee(firstName, lastName, nationalId, position, baseSalary, experience);
        employees.add(employee);
        saveEmployees();
        System.out.println("\n✅ کارمند با موفقیت ثبت شد!");
    }

    private void displayAllEmployees() {
        if (employees.isEmpty()) {
            System.out.println("⛔ هیچ کارمندی ثبت نشده است.");
            return;
        }
        System.out.println("\n--- لیست تمام کارمندان ---");
        employees.forEach(emp -> {
            System.out.println(emp.getFullInfo());
            System.out.println("-------------------");
        });
    }

    private void displayByPosition() {
        System.out.print("\nسمت مورد نظر را وارد کنید (مدیر/مسئول رزرویشن/نگهبان/خدمتکار): ");
        String position = scanner.nextLine();

        boolean found = false;
        for (Employee emp : employees) {
            if (emp.getPosition().equalsIgnoreCase(position)) {
                System.out.println("\n" + emp.getFullInfo());
                System.out.println("-------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("⛔ هیچ کارمندی با این سمت یافت نشد.");
        }
    }

    private void saveEmployees() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(DATA_FILE))) {
            oos.writeObject(employees);
        } catch (IOException e) {
            System.out.println("⚠ خطا در ذخیره اطلاعات: " + e.getMessage());
        }
    }

    private void loadEmployees() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(DATA_FILE))) {
                employees = (List<Employee>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("⚠ خطا در بارگذاری اطلاعات: " + e.getMessage());
            }
        }
    }
}
