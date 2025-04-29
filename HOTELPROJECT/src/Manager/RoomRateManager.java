package Manager;

import Common.Room;
import txtFileManager.txtfilemanager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomRateManager {
    private Room[] rooms;
    private txtfilemanager fileManager;

    private JTextField nameField;
    private JComboBox<String> roomComboBox;
    private JTextField nightsField;
    private JButton reserveButton;
    private JTextArea outputArea;

    private JPanel mainPanel; // 🌟 اضافه شد

    public RoomRateManager() {
        fileManager = new txtfilemanager("ROOMRATE.txt");

        rooms = new Room[]{
                new Room(101, 1, 1000000),
                new Room(102, 1, 1000000),
                new Room(103, 1, 1000000),
                new Room(104, 1, 1000000),
                new Room(105, 1, 1000000),
                new Room(201, 2, 2000000),
                new Room(202, 2, 2000000),
                new Room(203, 2, 2000000),
                new Room(204, 2, 2000000),
                new Room(205, 2, 2000000),
                new Room(301, 3, 3000000),
                new Room(302, 3, 3000000),
                new Room(303, 3, 3000000),
                new Room(401, 4, 4000000),
                new Room(402, 4, 4000000),
                new Room(403, 4, 4000000),
                new Room(404, 4, 4000000),
                new Room(501, 5, 5000000),
                new Room(502, 5, 6000000),
                new Room(503, 5, 6000000)
        };

        initComponents(); // 🌟 فقط ایجاد کامپوننت‌ها
    }

    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout()); // 🌟 به‌جای JFrame

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        JLabel nameLabel = new JLabel("👤 Guest Name:");
        nameField = new JTextField();

        JLabel roomLabel = new JLabel("🏨 Select Room:");
        roomComboBox = new JComboBox<>();
        for (Room room : rooms) {
            roomComboBox.addItem("Room " + room.getRoomNumber() + " | Capacity: " + room.getCapacity() + " beds | " + room.getPricePerNight() + " Toman");
        }

        JLabel nightsLabel = new JLabel("🌙 Nights to stay:");
        nightsField = new JTextField();

        reserveButton = new JButton("✅ Reserve Now");

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(roomLabel);
        inputPanel.add(roomComboBox);
        inputPanel.add(nightsLabel);
        inputPanel.add(nightsField);
        inputPanel.add(new JLabel()); // خالی برای زیبایی
        inputPanel.add(reserveButton);

        mainPanel.add(inputPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        mainPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reserveRoom();
            }
        });
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    private void reserveRoom() {
        String guestName = nameField.getText().trim();
        if (guestName.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "⚠️ Please enter your name!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int selectedIndex = roomComboBox.getSelectedIndex();
        Room selectedRoom = rooms[selectedIndex];

        int nights;
        try {
            nights = Integer.parseInt(nightsField.getText().trim());
            if (nights <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainPanel, "⚠️ Please enter a valid number of nights!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double totalPrice = selectedRoom.getPricePerNight() * nights;

        StringBuilder reservationDetails = new StringBuilder();
        reservationDetails.append("\n[Room Reservation]\n");
        reservationDetails.append("Guest Name: ").append(guestName).append("\n");
        reservationDetails.append("Room Number: ").append(selectedRoom.getRoomNumber()).append("\n");
        reservationDetails.append("Room Capacity: ").append(selectedRoom.getCapacity()).append(" beds\n");
        reservationDetails.append("Price per Night: ").append(selectedRoom.getPricePerNight()).append(" Toman\n");
        reservationDetails.append("Nights: ").append(nights).append("\n");
        reservationDetails.append("Total Price: ").append(totalPrice).append(" Toman\n");

        fileManager.AppendRow(reservationDetails.toString());

        outputArea.setText("✅ Reservation Successful!\n\n" + reservationDetails.toString());
    }
}
