import common.Constants;
import common.Utils;
import manager.CourseManager;
import manager.RegistrationManager;
import java.util.Scanner;

public class MainMaryam {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nذخیره داده‌ها...");
            RegistrationManager.saveRegistrations();
        }));

        while (true) {
            System.out.println("\n--- سیستم مدیریت آموزش هتل ---");
            System.out.println("1. نمایش دوره‌ها");
            System.out.println("2. ثبت‌نام جدید");
            System.out.println("3. نمایش ثبت‌نام‌ها");
            System.out.println("4. محاسبه تخفیف گروهی");
            System.out.println("0. خروج");
            System.out.print("انتخاب: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> new CourseManager().showCourses();
                case 2 -> RegistrationManager.register(scanner);
                case 3 -> RegistrationManager.showAllRegistrations();
                case 4 -> calculateGroupDiscount(scanner);
                case 0 -> {
                    System.out.println("خروج از سیستم...");
                    System.exit(0);
                }
                default -> System.out.println("⚠ انتخاب نامعتبر!");
            }
        }
    }

    private static void calculateGroupDiscount(Scanner scanner) {
        System.out.println("\n--- محاسبه تخفیف گروهی ---");
        System.out.print("تعداد افراد: ");
        int count = scanner.nextInt();
        System.out.print("شماره دوره: ");
        int courseId = scanner.nextInt();

        if (courseId < 1 || courseId > Constants.COURSES.length * 4) {
            System.out.println("⚠ شماره دوره نامعتبر!");
            return;
        }

        int group = (courseId-1)/4;
        int index = (courseId-1)%4;
        int price = Integer.parseInt(Constants.COURSES[group][index][1]);

        int discount = 0;
        if (count >= 5) discount = 15;
        else if (count >= 3) discount = 10;

        int total = price * count;
        int discountAmount = total * discount / 100;
        int finalPrice = total - discountAmount;

        System.out.println("\n💵 فاکتور نهایی:");
        System.out.println("├─ دوره: " + Constants.COURSES[group][index][0]);
        System.out.println("├─ قیمت پایه: " + Utils.formatPrice(price));
        System.out.println("├─ تعداد: " + count);
        System.out.println("├─ تخفیف: " + discount + "%");
        System.out.println("└─ مبلغ قابل پرداخت: " + Utils.formatPrice(finalPrice));
    }
}
//
