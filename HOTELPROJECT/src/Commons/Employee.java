package common;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String firstName;
    protected String lastName;
    protected String nationalId;
    protected String position;
    protected double baseSalary;
    protected int workExperience;

    public Employee(String firstName, String lastName,
                    String nationalId, String position,
                    double baseSalary, int workExperience) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalId = nationalId;
        this.position = position;
        this.baseSalary = baseSalary;
        this.workExperience = workExperience;
    }

    public String getFullInfo() {
        NumberFormat nf = NumberFormat.getInstance(new Locale("fa", "IR"));
        return String.format(
                "نام کامل: %s %s\nکد ملی: %s\nسمت: %s\nحقوق پایه: %s ریال\nسابقه کار: %d سال\nحقوق نهایی: %s ریال",
                firstName, lastName, nationalId, position,
                nf.format(baseSalary), workExperience,
                nf.format(calculateSalary())
        );
    }

    public double calculateSalary() {
        // حقوق پایه + (هر سال سابقه 100,000 ریال افزایش)
        return baseSalary + (workExperience * 100000);
    }

    // Getter methods
    public String getPosition() { return position; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
}
