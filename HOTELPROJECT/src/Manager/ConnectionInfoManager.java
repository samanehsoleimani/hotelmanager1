package Manager;

import Common.Connection;
import txtFileManager.txtfilemanager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectionInfoManager {
    private JTextField nameField;
    private JTextField roomNumberField;
    private JTextField emailField;
    private JTextField mobileField;
    private txtfilemanager fileManager;

    public ConnectionInfoManager() {
        fileManager = new txtfilemanager("LIST.txt");
    }

    public JPanel getPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 30, 80, 25);
        panel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(120, 30, 180, 25);
        panel.add(nameField);

        JLabel roomLabel = new JLabel("Room No:");
        roomLabel.setBounds(30, 70, 80, 25);
        panel.add(roomLabel);

        roomNumberField = new JTextField();
        roomNumberField.setBounds(120, 70, 180, 25);
        panel.add(roomNumberField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 110, 80, 25);
        panel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(120, 110, 180, 25);
        panel.add(emailField);

        JLabel mobileLabel = new JLabel("Mobile:");
        mobileLabel.setBounds(30, 150, 80, 25);
        panel.add(mobileLabel);

        mobileField = new JTextField();
        mobileField.setBounds(120, 150, 180, 25);
        panel.add(mobileField);

        JButton saveButton = new JButton("✅ Save");
        saveButton.setBounds(120, 200, 100, 30);
        panel.add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveConnectionInfo();
            }
        });

        return panel;
    }

    private void saveConnectionInfo() {
        String name = nameField.getText().trim();
        String roomText = roomNumberField.getText().trim();
        String email = emailField.getText().trim();
        String mobile = mobileField.getText().trim();

        if (name.isEmpty() || roomText.isEmpty() || email.isEmpty() || mobile.isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠️ Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int roomNumber;
        try {
            roomNumber = Integer.parseInt(roomText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "⚠️ Room number must be a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection connection = new Connection(name, roomNumber, email, mobile);
        StringBuilder info = new StringBuilder();
        info.append("\n=== Connection Info ===\n");
        info.append(connection.toString()).append("\n");

        fileManager.AppendRow(info.toString());

        JOptionPane.showMessageDialog(null, "✅ Information registered successfully!");

        nameField.setText("");
        roomNumberField.setText("");
        emailField.setText("");
        mobileField.setText("");
    }
}
