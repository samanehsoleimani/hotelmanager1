import manager.CourseManager;
import manager.RegistrationManager;

import java.util.Scanner;

public class Mainmaryam {
    public static void main(String[] args) {
        CourseManager courseManager = new CourseManager();
        RegistrationManager registrationManager = new RegistrationManager(courseManager);
        Scanner scanner = new Scanner(System.in);

        try {
            courseManager.loadFromFile();
        } catch (Exception e) {
            System.out.println("⚠ خطا در بارگذاری اطلاعات: " + e.getMessage());
        }

        while (true) {
            System.out.println("\n--- سیستم جامع آموزش هتل ---");
            System.out.println("1. نمایش همه دوره‌ها");
            System.out.println("2. ثبت‌نام در دوره");
            System.out.println("3. نمایش کارمندان ثبت‌نام‌شده");
            System.out.println("4. حذف ثبت‌نام");
            System.out.println("0. خروج");
            System.out.print("انتخاب کنید: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> courseManager.showAllCourses();
                case 2 -> registrationManager.register(scanner);
                case 3 -> courseManager.showRegistrations();
                case 4 -> {
                    courseManager.showRegistrations();
                    System.out.print("\nشماره ردیف ثبت‌نام برای حذف: ");
                    int index = scanner.nextInt() - 1;
                    scanner.nextLine();
                    courseManager.deleteRegistration(index);
                    System.out.println("✅ ثبت‌نام حذف شد.");
                }
                case 0 -> {
                    try {
                        courseManager.saveToFile();
                        System.out.println("با تشکر! خروج از برنامه...");
                        System.exit(0);
                    } catch (Exception e) {
                        System.out.println("⚠ خطا در ذخیره اطلاعات: " + e.getMessage());
                    }
                }
                default -> System.out.println("⚠ انتخاب نامعتبر!");
            }
        }
    }
}