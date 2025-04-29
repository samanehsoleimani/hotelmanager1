package Common;

public class EmployeeList {
    private String name;
    private String role;
    private String shift;

    public EmployeeList(String name, String role, String shift) {
        this.name = name;
        this.role = role;
        this.shift = shift;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getShift() {
        return shift;
    }

    @Override
    public String toString() {
        return "👤 " + name + " | Role: " + role + " | Shift: " + shift;
    }
}
