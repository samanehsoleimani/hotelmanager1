package manager;

import common.Constants;
import common.Utils;

import java.util.Scanner;

public class RegistrationManager {
    private final CourseManager courseManager;

    public RegistrationManager(CourseManager courseManager) {
        this.courseManager = courseManager;
    }

    public void register(Scanner scanner) {
        courseManager.showAllCourses();

        System.out.print("\nنام و نام خانوادگی: ");
        String fullName = scanner.nextLine();

        System.out.print("شماره تماس: ");
        String phone = scanner.nextLine();

        System.out.print("شماره دوره (1-12): ");
        int courseId = scanner.nextInt();
        scanner.nextLine();

        if (courseId < 1 || courseId > 12) {
            System.out.println("⚠ شماره دوره نامعتبر است!");
            return;
        }

        int group = (courseId-1)/4;
        int index = (courseId-1)%4;
        String courseName = Constants.COURSES[group][index][0];
        String coursePrice = Utils.formatPrice(Integer.parseInt(Constants.COURSES[group][index][1]));

        courseManager.addRegistration(new String[]{fullName, phone, courseName, coursePrice});

        System.out.println("\n┌──────────────────────────────┐");
        System.out.println("│ 🏨 **رسید ثبت‌نام**           │");
        System.out.println("├──────────────────────────────┤");
        System.out.println("│ نام: " + Utils.padRight(fullName, 25) + "│");
        System.out.println("│ تماس: " + Utils.padRight(phone, 23) + "│");
        System.out.println("│ دوره: " + Utils.padRight(courseName, 23) + "│");
        System.out.println("│ هزینه: " + Utils.padRight(coursePrice, 22) + "│");
        System.out.println("└──────────────────────────────┘");
        System.out.println("\n✅ ثبت‌نام با موفقیت انجام شد!");
    }
}