package manager;

import common.Constants;
import common.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CourseManager {
    private List<String[]> registrations = new ArrayList<>();

    public void showAllCourses() {
        System.out.println("\n--- لیست کامل دوره‌های آموزشی ---");

        for (int i = 0; i < Constants.COURSES.length; i++) {
            System.out.println("\n" + Constants.CATEGORIES[i] + ":");
            for (int j = 0; j < Constants.COURSES[i].length; j++) {
                int courseNum = (i * 4) + j + 1;
                System.out.println(courseNum + ". " + Constants.COURSES[i][j][0] +
                        " | هزینه: " + Utils.formatPrice(Integer.parseInt(Constants.COURSES[i][j][1])));
            }
        }
    }

    public void addRegistration(String[] registration) {
        registrations.add(registration);
    }

    public void showRegistrations() {
        if (registrations.isEmpty()) {
            System.out.println("⛔ هیچ کارمندی ثبت‌نام نکرده است.");
            return;
        }

        System.out.println("\n--- کارمندان ثبت‌نام‌شده ---");
        System.out.println("┌──────┬──────────────────────┬──────────────────────┬──────────────────────┬──────────────────────┐");
        System.out.println("│ ردیف │ نام و نام خانوادگی   │ شماره تماس          │ دوره آموزشی         │ هزینه پرداختی       │");
        System.out.println("├──────┼──────────────────────┼──────────────────────┼──────────────────────┼──────────────────────┤");

        for (int i = 0; i < registrations.size(); i++) {
            String[] emp = registrations.get(i);
            System.out.printf("│ %-4d │ %-20s │ %-20s │ %-20s │ %-20s │\n",
                    i+1, emp[0], emp[1], emp[2], emp[3]);
        }
        System.out.println("└──────┴──────────────────────┴──────────────────────┴──────────────────────┴──────────────────────┘");
    }

    public void deleteRegistration(int index) {
        if (index >= 0 && index < registrations.size()) {
            registrations.remove(index);
        }
    }

    public void saveToFile() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Constants.DATA_FILE))) {
            oos.writeObject(registrations);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile() throws IOException, ClassNotFoundException {
        File file = new File(Constants.DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Constants.DATA_FILE))) {
                registrations = (List<String[]>) ois.readObject();
            }
        }
    }
}