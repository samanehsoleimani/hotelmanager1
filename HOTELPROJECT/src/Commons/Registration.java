package common;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Registration implements Serializable {
    private static final long serialVersionUID = 1L;

    private String fullName;
    private String nationalId;
    private String phone;
    private String courseName;
    private int coursePrice;
    private String courseSchedule;
    private String courseDate;
    private String registrationDate;

    public Registration(String fullName, String nationalId, String phone,
                        String courseName, int coursePrice,
                        String courseSchedule, String courseDate) {
        this.fullName = fullName;
        this.nationalId = nationalId;
        this.phone = phone;
        this.courseName = courseName;
        this.coursePrice = coursePrice;
        this.courseSchedule = courseSchedule;
        this.courseDate = courseDate;
        this.registrationDate = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date());
    }

    public String getRegistrationInfo() {
        return "نام و نام خانوادگی: " + fullName +
                "\nکد ملی: " + nationalId +
                "\nشماره تماس: " + phone +
                "\nدوره: " + courseName +
                "\nزمان برگزاری: " + courseSchedule +
                "\nتاریخ برگزاری: " + courseDate +
                "\nهزینه: " + Utils.formatPrice(coursePrice) +
                "\nتاریخ ثبت‌نام: " + registrationDate;
    }
}
//
