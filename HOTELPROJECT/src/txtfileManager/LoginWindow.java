package txtfileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginWindow extends JFrame {
    private JPasswordField passwordField;
    private JButton loginButton;
    private final String correctPassword = "1234"; // اینو بعدا می‌تونی از فایل هم بخونی

    public LoginWindow() {
        setTitle("ورود به سیستم");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("رمز عبور:", SwingConstants.CENTER);
        passwordField = new JPasswordField();
        loginButton = new JButton("ورود");

        JPanel inputPanel = new JPanel(new GridLayout(2, 1));
        inputPanel.add(label);
        inputPanel.add(passwordField);

        add(inputPanel, BorderLayout.CENTER);
        add(loginButton, BorderLayout.SOUTH);

        loginButton.addActionListener(e -> checkPassword());

        setVisible(true);
    }

    private void checkPassword() {
        String enteredPassword = new String(passwordField.getPassword());

        if (enteredPassword.equals(correctPassword)) {
            //JOptionPane.showMessageDialog(this, "✅ ورود موفق!");
           // dispose(); // فرم لاگین رو ببند
            new Main().setVisible(true); // حالا فرم اصلی خودتو باز کن
        } else {
            JOptionPane.showMessageDialog(this, "⛔ رمز اشتباهه!", "خطا", JOptionPane.ERROR_MESSAGE);
            passwordField.setText(""); // پاک کردن فیلد رمز
        }
    }

}
