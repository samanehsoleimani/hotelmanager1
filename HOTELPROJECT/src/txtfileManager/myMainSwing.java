package txtFileManager;

import Manager.FoodManager;
import Manager.RoomRateManager;
import Manager.ConnectionInfoManager;
import Manager.EmployeeManager;
import Manager.EntertainmentManager;

import javax.swing.*;
import java.awt.*;

public class myMainSwing {

    public static void main(String[] args) {
        JFrame frame = new JFrame("🏨 Hotel Services");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // ساختن پنل‌ها برای هر بخش
        JPanel foodPanel = new FoodManager().getPanel(); // باید متد getPanel() اضافه کنیم
        JPanel roomPanel = new RoomRateManager().getPanel();
        JPanel connectionPanel = new ConnectionInfoManager().getPanel();
        JPanel employeePanel = new EmployeeManager().getPanel();
        JPanel entertainmentPanel = new EntertainmentManager().getPanel();

        // افزودن تب‌ها
        tabbedPane.addTab("🍽️ Food", foodPanel);
        tabbedPane.addTab("🛏️ Room Booking", roomPanel);
        tabbedPane.addTab("📝 Info", connectionPanel);
        tabbedPane.addTab("👥 Employees", employeePanel);
        tabbedPane.addTab("🎉 Fun", entertainmentPanel);

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
