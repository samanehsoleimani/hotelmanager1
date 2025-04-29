package manager;

import common.Constants;
import common.Utils;

public class CourseManager {
    public void showCourses() {
        System.out.println("\n--- دوره‌های آموزشی هتل ---");

        for (int i = 0; i < Constants.COURSES.length; i++) {
            System.out.println("\n" + Constants.CATEGORIES[i] + ":");
            for (int j = 0; j < Constants.COURSES[i].length; j++) {
                String[] course = Constants.COURSES[i][j];
                System.out.printf("%2d. %s | زمان: %s | تاریخ: %s | هزینه: %s\n",
                        (i*4)+j+1,
                        Utils.alignText(course[0], 25),
                        course[2],
                        course[3],
                        Utils.formatPrice(Integer.parseInt(course[1]))
                );
            }
        }
    }
}
