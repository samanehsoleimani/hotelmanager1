package Manager;

import Common.EmployeeList;
import txtFileManager.txtfilemanager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeManager {
    private final EmployeeList[] employees;
    private final String ADMIN_PASSWORD = "admin123"; // رمز ورود مدیر
    private final txtfilemanager fileManager;

    private JPasswordField passwordField;
    private JTextArea employeeTextArea;
    private JButton loginButton;

    public EmployeeManager() {
        fileManager = new txtfilemanager("EMP.txt");

        employees = new EmployeeList[]{
                new EmployeeList("Mr. Amir Rezaei", "Manager", "Full Day"),
                new EmployeeList("Mr. Ali Hosseini", "Receptionist", "8am - 2pm"),
                new EmployeeList("Ms. Sara Ahmadi", "Receptionist", "2pm - 8pm"),
                new EmployeeList("Mr. Nima Karimi", "Receptionist", "8am - 2pm"),
                new EmployeeList("Ms. Fatemeh Rajabi", "Receptionist", "2pm - 8pm"),
                new EmployeeList("Mr. Mohsen Ghaffari", "Guard", "Night"),
                new EmployeeList("Mr. Hamid Nazari", "Guard", "Day"),
                new EmployeeList("Ms. Maryam Ebrahimi", "Cleaner", "8am - 2pm"),
                new EmployeeList("Mr. Hossein Fallahi", "Cleaner", "8am - 2pm"),
                new EmployeeList("Ms. Leila Shariati", "Cleaner", "2pm - 8pm"),
                new EmployeeList("Ms. Simin Mostafavi", "Cleaner", "2pm - 8pm"),
                new EmployeeList("Mr. Hassan Tavakoli", "Cleaner", "Night"),
                new EmployeeList("Ms. Zahra Moradi", "Cleaner", "Night")
        };
    }

    public JPanel getPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel passwordLabel = new JLabel("🔐 Admin Password:");
        passwordLabel.setBounds(30, 30, 150, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(180, 30, 150, 25);
        panel.add(passwordField);

        loginButton = new JButton("Login ✅");
        loginButton.setBounds(140, 70, 100, 30);
        panel.add(loginButton);

        employeeTextArea = new JTextArea();
        employeeTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(employeeTextArea);
        scrollPane.setBounds(30, 120, 320, 200);
        panel.add(scrollPane);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkPasswordAndShowEmployees();
            }
        });

        return panel;
    }

    private void checkPasswordAndShowEmployees() {
        String inputPassword = new String(passwordField.getPassword()).trim();

        if (inputPassword.equals(ADMIN_PASSWORD)) {
            StringBuilder list = new StringBuilder();
            list.append("✅ Employee List:\n\n");
            for (EmployeeList employee : employees) {
                list.append(employee.toString()).append("\n");
            }
            employeeTextArea.setText(list.toString());
        } else {
            JOptionPane.showMessageDialog(null, "❌ Incorrect password. Access denied.", "Access Denied", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
            employeeTextArea.setText("");
        }
    }
}
