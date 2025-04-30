package manager;

import common.Constants;
import common.Registration;
import common.Utils;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RegistrationManager {
    private static final String DATA_FILE = "registrations.dat";
    private static List<Registration> registrations = loadRegistrations();

    private static List<Registration> loadRegistrations() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            return (List<Registration>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("خطا در بارگذاری ثبت‌نام‌ها: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void saveRegistrations() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(registrations);
        } catch (IOException e) {
            System.out.println("خطا در ذخیره ثبت‌نام‌ها: " + e.getMessage());
        }
    }

    public static void showAllRegistrations() {
        if (registrations.isEmpty()) {
            System.out.println("\nهنوز هیچ ثبت‌نامی انجام نشده است.");
            return;
        }

        System.out.println("\n--- لیست ثبت‌نام‌ها (" + registrations.size() + " مورد) ---");
        for (int i = 0; i < registrations.size(); i++) {
            System.out.println("\nثبت‌نام #" + (i+1));
            System.out.println(registrations.get(i).getRegistrationInfo());
            System.out.println("------------------");
        }
    }

    public static void register(Scanner scanner) {
        CourseManager courseManager = new CourseManager();
        courseManager.showCourses();

        System.out.print("\nشماره دوره: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();

        if (courseId < 1 || courseId > Constants.COURSES.length * 4) {
            System.out.println("⚠ شماره دوره نامعتبر!");
            return;
        }

        int group = (courseId-1)/4;
        int index = (courseId-1)%4;
        String[] course = Constants.COURSES[group][index];

        System.out.print("نام و نام خانوادگی: ");
        String fullName = scanner.nextLine();

        System.out.print("کد ملی (10 رقم): ");
        String nationalId = scanner.nextLine();

        System.out.print("شماره تماس: ");
        String phone = scanner.nextLine();

        if (!isValidNationalId(nationalId)) {
            System.out.println("⚠ کد ملی نامعتبر است!");
            return;
        }

        Registration newReg = new Registration(
                fullName, nationalId, phone,
                course[0], Integer.parseInt(course[1]),
                course[2], course[3]
        );
        registrations.add(newReg);
        saveRegistrations();

        System.out.println("\n✅ ثبت‌نام با موفقیت انجام شد:");
        System.out.println(newReg.getRegistrationInfo());
    }

    private static boolean isValidNationalId(String nationalId) {
        return nationalId != null && nationalId.matches("\\d{10}");
    }
}
//
