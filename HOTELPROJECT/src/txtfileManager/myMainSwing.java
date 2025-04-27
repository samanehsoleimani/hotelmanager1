package txtFileManager;

import Manager.FoodManager;
import Manager.RoomRateManager;
import javax.swing.*;
import java.awt.event.*;

public class myMainSwing {

    public static void main(String[] args) {
        JFrame frame = new JFrame("🏨 Hotel Services");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLayout(null);

        JButton foodButton = new JButton("🍽️ Use Food Services");
        foodButton.setBounds(50, 50, 200, 50);

        JButton roomButton = new JButton("🛏️ Book a Room");
        roomButton.setBounds(50, 120, 200, 50);

        FoodManager foodManager = new FoodManager();
        RoomRateManager roomRateManager = new RoomRateManager();

        foodButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                foodManager.startFoodService();
            }
        });

        roomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                roomRateManager.setVisible(true);
            }
        });

        frame.add(foodButton);
        frame.add(roomButton);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
