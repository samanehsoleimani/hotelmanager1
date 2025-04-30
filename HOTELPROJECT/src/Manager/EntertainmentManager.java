package Manager;

import Common.Entertainment;
import txtFileManager.txtfilemanager;

import javax.swing.*;
import java.awt.event.*;

public class EntertainmentManager {
    private txtfilemanager fileManager;
    private Entertainment[] entertainments;

    public EntertainmentManager() {
        fileManager = new txtfilemanager("Entertainment.txt");
        entertainments = new Entertainment[]{
                new Entertainment("Water Park", 150000),
                new Entertainment("Cinema", 100000)
        };
    }

    public JPanel getPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel nameLabel = new JLabel("Enter your name:");
        nameLabel.setBounds(50, 30, 300, 25);

        JTextField nameField = new JTextField();
        nameField.setBounds(50, 60, 300, 30);

        JLabel chooseLabel = new JLabel("Select activities:");
        chooseLabel.setBounds(50, 100, 300, 25);

        JCheckBox[] checkBoxes = new JCheckBox[entertainments.length];
        for (int i = 0; i < entertainments.length; i++) {
            checkBoxes[i] = new JCheckBox(entertainments[i].getName() + " - " + entertainments[i].getPrice() + " Toman");
            checkBoxes[i].setBounds(50, 130 + (i * 40), 300, 30);
            panel.add(checkBoxes[i]);
        }

        JButton submitButton = new JButton("Reserve Activities");
        submitButton.setBounds(100, 250, 200, 40);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String guestName = nameField.getText().trim();
                if (guestName.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Please enter your name!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                StringBuilder selectedActivities = new StringBuilder();
                double totalPrice = 0;

                for (int i = 0; i < checkBoxes.length; i++) {
                    if (checkBoxes[i].isSelected()) {
                        selectedActivities.append(entertainments[i].getName()).append(", ");
                        totalPrice += entertainments[i].getPrice();
                    }
                }

                if (selectedActivities.length() == 0) {
                    JOptionPane.showMessageDialog(panel, "Please select at least one activity!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // حذف ", " اضافه آخر
                if (selectedActivities.length() > 2) {
                    selectedActivities.setLength(selectedActivities.length() - 2);
                }

                String ticketDetails = "Guest Name: " + guestName + "\n"
                        + "Selected Activities: " + selectedActivities.toString() + "\n"
                        + "Total Cost: " + totalPrice + " Toman\n";

                fileManager.AppendRow(ticketDetails);

                JOptionPane.showMessageDialog(panel, "✅ Your activities have been reserved!\nTotal Cost: " + totalPrice + " Toman", "Success", JOptionPane.INFORMATION_MESSAGE);

                // پاک‌سازی فرم
                nameField.setText("");
                for (JCheckBox cb : checkBoxes) cb.setSelected(false);
            }
        });

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(chooseLabel);
        panel.add(submitButton);

        return panel;
    }
}
