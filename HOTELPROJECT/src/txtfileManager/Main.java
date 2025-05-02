package txtFileManager;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import Manager.*;
import Common.*;

public class Main extends JFrame {

    private GuestsManager guestsManager;
    private RoomManager roomManager;
    private PaymentsManager paymentsManager;

    public Main() {
        guestsManager = new GuestsManager();
        roomManager = new RoomManager();
        paymentsManager = new PaymentsManager();

        RoomManager rm = new RoomManager();
        rm.initializeRooms(); // فقط یک بار اجرا بشه – مقداردهی اولیه

        
        setTitle("سیستم مدیریت هتل");
        setSize(700, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("مدیریت مهمان", createGuestPanel());
        tabs.addTab("مدیریت اتاق", createRoomPanel());
        tabs.addTab("مدیریت پرداخت", createPaymentPanel());
        tabs.addTab("نظر سنجی", creatsurvaypanel());


        add(tabs);
    }

    private JPanel createGuestPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("📘 ثبت و نمایش مهمان‌ها"));

        // فرم ورود اطلاعات
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        // فیلدهای ورودی
        JTextField name = new JTextField();
        JTextField melli = new JTextField();
        JTextField phone = new JTextField();
        JTextField room = new JTextField();

        // استایل فیلدها
        Dimension fieldSize = new Dimension(200, 30);
        Font font = new Font("SansSerif", Font.PLAIN, 14);

        JTextField[] fields = {name, melli, phone, room};
        for (JTextField f : fields) {
            f.setPreferredSize(fieldSize);
            f.setFont(font);
        }

        // افزودن فیلدها به فرم
        formPanel.add(new JLabel("👤 نام کامل:"));
        formPanel.add(name);
        formPanel.add(new JLabel("🆔 کد ملی:"));
        formPanel.add(melli);
        formPanel.add(new JLabel("📞 تلفن:"));
        formPanel.add(phone);
        formPanel.add(new JLabel("🚪 شماره اتاق:"));
        formPanel.add(room);

        // دکمه‌ها
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        JButton addGuest = new JButton("➕ ثبت مهمان");
        JButton listGuests = new JButton("📋 لیست مهمان‌ها");
        buttonPanel.add(addGuest);;
        buttonPanel.add(listGuests);

        // خروجی
        JTextArea output = new JTextArea(10, 30);
        output.setEditable(false);
        output.setFont(new Font("Monospaced", Font.PLAIN, 13));
        output.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(output);
        scrollPane.setBorder(BorderFactory.createTitledBorder("📄 لیست مهمان‌ها"));

        // رویداد ثبت
        addGuest.addActionListener(e -> {
            try {
                Guests g = new Guests();
                g.setFullName(name.getText().trim());
                g.setMellicode(Integer.parseInt(melli.getText().trim()));
                g.setPhoneNumber(phone.getText().trim());
                g.setRoomNumber(Integer.parseInt(room.getText().trim()));

                guestsManager.insert(g);
                JOptionPane.showMessageDialog(panel, "✅ مهمان با موفقیت ثبت شد");

                // پاکسازی فیلدها
                name.setText(""); melli.setText(""); phone.setText(""); room.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "⚠ لطفاً اطلاعات را صحیح وارد کنید", "خطا", JOptionPane.ERROR_MESSAGE);
            }
        });

        // رویداد لیست مهمان‌ها
        listGuests.addActionListener(e -> {
            output.setText("");
            String[] data = guestsManager.fn.getArrayFromFile();
            for (String row : data) {
                output.append(row + "\n");
            }
        });

        // چیدمان نهایی
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createRoomPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createTitledBorder("🏨 مدیریت اتاق‌ها"));

        // فرم اطلاعات اتاق
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        // فیلدها
        JTextField roomNumber = new JTextField();
        JTextField bed = new JTextField();
        JTextField guestName = new JTextField();
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"Available", "Occupied"});

        // تنظیم اندازه و فونت
        Dimension fieldSize = new Dimension(200, 30);
        Font font = new Font("SansSerif", Font.PLAIN, 14);

        JTextField[] fields = {roomNumber, bed, guestName};
        for (JTextField field : fields) {
            field.setPreferredSize(fieldSize);
            field.setFont(font);
        }
        statusBox.setPreferredSize(fieldSize);
        statusBox.setFont(font);

        // اضافه کردن به فرم
        formPanel.add(new JLabel("🔢 شماره اتاق:"));
        formPanel.add(roomNumber);
        formPanel.add(new JLabel("🛏️ تعداد تخت:"));
        formPanel.add(bed);
        formPanel.add(new JLabel("👤 نام مهمان:"));
        formPanel.add(guestName);
        formPanel.add(new JLabel("📌 وضعیت:"));
        formPanel.add(statusBox);

        // دکمه‌ها
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        JButton addRoom = new JButton("➕ ثبت اتاق");
        JButton showRooms = new JButton("📋 نمایش اتاق‌ها");
        buttonPanel.add(addRoom);
        buttonPanel.add(showRooms);

        // خروجی
        JTextArea output = new JTextArea(10, 30);
        output.setEditable(false);
        output.setFont(new Font("Monospaced", Font.PLAIN, 13));
        output.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(output);
        scrollPane.setBorder(BorderFactory.createTitledBorder("📄 وضعیت اتاق‌ها"));

        // رویداد ثبت اتاق
        addRoom.addActionListener(e -> {
            try {
                Room r = new Room();
                r.setRoomNumber(Integer.parseInt(roomNumber.getText().trim()));
                r.setBed(bed.getText().trim());
                r.setGuestsName(guestName.getText().trim());
                r.setStatus((String) statusBox.getSelectedItem());

                roomManager.saveOrUpdateRoom(r);
                JOptionPane.showMessageDialog(mainPanel, "✅ اتاق ثبت شد");

                // پاک‌سازی
                roomNumber.setText("");
                bed.setText("");
                guestName.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainPanel, "❗ لطفاً شماره اتاق را به درستی وارد کنید");
            }
        });

        // رویداد نمایش اتاق‌ها
        showRooms.addActionListener(e -> {
            output.setText(roomManager.showAllRoomsStatus());
        });

        // اضافه کردن بخش‌ها به پنل اصلی
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        return mainPanel;
    }

    private JPanel createPaymentPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 🔹 فرم ورودی اطلاعات پرداخت
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("📋 اطلاعات پرداخت"));

        JTextField roomNum = new JTextField(10);
        JTextField guest = new JTextField(10);
        JTextField nights = new JTextField(10);
        JTextField bed = new JTextField(10);
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"paid", "unpaid"});

        formPanel.add(new JLabel("شماره اتاق:"));
        formPanel.add(roomNum);
        formPanel.add(new JLabel("نام مهمان:"));
        formPanel.add(guest);
        formPanel.add(new JLabel("تعداد شب:"));
        formPanel.add(nights);
        formPanel.add(new JLabel("نوع تخت:"));
        formPanel.add(bed);
        formPanel.add(new JLabel("وضعیت پرداخت:"));
        formPanel.add(statusBox);

        // 🔹 دکمه‌های اصلی
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton addPay = new JButton("💰 ثبت پرداخت");
        JButton showPays = new JButton("📄 نمایش پرداخت‌ها");
        JButton calcTotal = new JButton("💲 جمع کل پرداخت‌ها");
        buttonPanel.add(addPay);
        buttonPanel.add(showPays);
        buttonPanel.add(calcTotal);

        // 🔹 بخش آپدیت وضعیت پرداخت
        JPanel updatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        updatePanel.setBorder(BorderFactory.createTitledBorder("🛠️ به‌روزرسانی وضعیت پرداخت"));

        JTextField updateRoomNum = new JTextField(10);
        JComboBox<String> updateStatusBox = new JComboBox<>(new String[]{"paid", "unpaid"});
        JButton updateStatusButton = new JButton("به‌روزرسانی");

        updatePanel.add(new JLabel("شماره اتاق:"));
        updatePanel.add(updateRoomNum);
        updatePanel.add(new JLabel("وضعیت جدید:"));
        updatePanel.add(updateStatusBox);
        updatePanel.add(updateStatusButton);

        // 🔹 خروجی
        JTextArea output = new JTextArea(10, 40);
        output.setFont(new Font("Monospaced", Font.PLAIN, 13));
        output.setEditable(false);
        output.setMargin(new Insets(8, 8, 8, 8));
        JScrollPane scrollPane = new JScrollPane(output);
        scrollPane.setBorder(BorderFactory.createTitledBorder("📑 خروجی"));

        // 🔹 چیدمان کلی
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(formPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(updatePanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        // 🎯 رویدادها
        addPay.addActionListener(e -> {
            try {
                Room r = new Room();
                r.setRoomNumber(Integer.parseInt(roomNum.getText()));
                r.setBed(bed.getText());

                Payment p = new Payment();
                p.setGuest(guest.getText());
                p.setRoomNumber(Integer.parseInt(roomNum.getText()));
                p.setStatus((String) statusBox.getSelectedItem());

                paymentsManager.insert(p, r, Integer.parseInt(nights.getText()));
                JOptionPane.showMessageDialog(mainPanel, "✅ پرداخت ثبت شد");

                // پاک‌سازی
                roomNum.setText("");
                guest.setText("");
                nights.setText("");
                bed.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainPanel, "❌ لطفاً اطلاعات عددی را به درستی وارد کنید.");
            }
        });

        showPays.addActionListener(e -> {
            output.setText("");
            String[] rows = paymentsManager.fn.getArrayFromFile();
            if (rows.length == 0) {
                output.append("هیچ پرداختی ثبت نشده است.\n");
            } else {
                for (String row : rows) {
                    output.append(row + "\n");
                }
            }
        });

        calcTotal.addActionListener(e -> {
            double total = paymentsManager.calculateTotalPayments();
            output.setText("💰 جمع کل پرداخت‌ها: " + total + " تومان");
        });

        updateStatusButton.addActionListener(e -> {
            try {
                int roomNumber = Integer.parseInt(updateRoomNum.getText());
                String newStatus = (String) updateStatusBox.getSelectedItem();
                paymentsManager.updatePaymentStatus(roomNumber, newStatus);
                JOptionPane.showMessageDialog(mainPanel, "✅ وضعیت پرداخت آپدیت شد");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainPanel, "❌ شماره اتاق معتبر نیست.");
            }
        });
        

        return mainPanel;
    }

    private JPanel creatsurvaypanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("📋 نظرسنجی"));

        // Form for entering survey data
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        JTextField customerName = new JTextField();
        JTextField roomNumber = new JTextField();
        JTextField rating = new JTextField();
        JTextField comment = new JTextField();

        // Add fields to the form
        formPanel.add(new JLabel("👤 نام مشتری:"));
        formPanel.add(customerName);
        formPanel.add(new JLabel("🔢 شماره اتاق:"));
        formPanel.add(roomNumber);
        formPanel.add(new JLabel("⭐️ امتیاز:"));
        formPanel.add(rating);
        formPanel.add(new JLabel("💬 نظر:"));
        formPanel.add(comment);

        // Buttons for submitting and viewing surveys
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        JButton submitSurvey = new JButton("➕ ثبت نظر");
        JButton showSurveys = new JButton("📄 نمایش نظرات");
        JButton searchSurvey = new JButton("🔍 جستجو");

        buttonPanel.add(submitSurvey);
        buttonPanel.add(showSurveys);
        buttonPanel.add(searchSurvey);

        // TextArea for displaying surveys
        JTextArea output = new JTextArea(10, 30);
        output.setEditable(false);
        output.setFont(new Font("Monospaced", Font.PLAIN, 13));
        output.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(output);
        scrollPane.setBorder(BorderFactory.createTitledBorder("📑 خروجی"));

        // Event listener for adding survey
        submitSurvey.addActionListener(e -> {
            try {
                Survey survey = new Survey();
                survey.setCustomerName(customerName.getText().trim());
                survey.setRoomNumber(Integer.parseInt(roomNumber.getText().trim()));
                survey.setRating(Integer.parseInt(rating.getText().trim()));
                survey.setComment(comment.getText().trim());

                SurveyManager surveyManager = new SurveyManager();
                surveyManager.saveSurvey(survey);

                JOptionPane.showMessageDialog(panel, "✅ نظر با موفقیت ثبت شد");

                // Clear fields
                customerName.setText("");
                roomNumber.setText("");
                rating.setText("");
                comment.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "⚠ لطفاً اطلاعات را صحیح وارد کنید", "خطا", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Event listener for showing all surveys
        showSurveys.addActionListener(e -> {
            // Use SwingUtilities.invokeLater to ensure updates happen on the Event Dispatch Thread
            SwingUtilities.invokeLater(() -> {
                output.setText(""); // Clear existing output
                SurveyManager surveyManager = new SurveyManager();
                List<Survey> surveys = surveyManager.getAllSurveys();  // Get all surveys
                if (surveys.isEmpty()) {
                    output.append("هیچ نظرسنجی یافت نشد.\n");
                } else {
                    for (Survey survey : surveys) {
                        if (survey != null) {  // Ensure that survey is not null
                            output.append("نام: " + survey.getCustomerName() + "\n");
                            output.append("شماره اتاق: " + survey.getRoomNumber() + "\n");
                            output.append("امتیاز: " + survey.getRating() + "\n");
                            output.append("نظر: " + survey.getComment() + "\n\n");
                        }
                    }
                }
            });
        });

        // Event listener for searching a specific survey
        searchSurvey.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(panel, "نام مشتری را وارد کنید:");
            if (name != null && !name.trim().isEmpty()) {
                SurveyManager surveyManager = new SurveyManager();
                String surveyInfo = surveyManager.getSurveyInfo(name.trim());
                output.setText(surveyInfo);
            }
        });

        // Adding components to the main panel
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH);

        return panel;
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	 new LoginWindow(); // اول فرم لاگین بیاد
            //new Main().setVisible(true);
        });
    }
}
