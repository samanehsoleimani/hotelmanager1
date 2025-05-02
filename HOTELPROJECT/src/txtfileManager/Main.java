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
    private static JPanel createRoomBooking() {
          return  new RoomRateManager().getPanel();
          }
   
   private static JPanel entertainmentPanel() {
          return  new EntertainmentManager().getPanel();
          }
   private static JPanel connectionPanel() {
          return  new ConnectionInfoManager().getPanel();
          }
   
   private static JPanel createEmployees() {
          return  new EmployeeManager().getPanel();
          }
   private static JPanel createFoodPanel() {
          return new FoodManager().getPanel(); // متد getPanel() باید در FoodManager پیاده‌سازی شود
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
    
private JPanel createEmployeePanel() {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBorder(BorderFactory.createTitledBorder("👨‍💼 مدیریت کارمندان"));

    JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
    formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

    JTextField firstName = new JTextField();
    JTextField lastName = new JTextField();
    JTextField nationalId = new JTextField();
    JTextField position = new JTextField();
    JTextField baseSalary = new JTextField();
    JTextField experience = new JTextField();

    formPanel.add(new JLabel("👤 نام:"));
    formPanel.add(firstName);
    formPanel.add(new JLabel("👥 نام خانوادگی:"));
    formPanel.add(lastName);
    formPanel.add(new JLabel("🆔 کد ملی:"));
    formPanel.add(nationalId);
    formPanel.add(new JLabel("🏢 سمت:"));
    formPanel.add(position);
    formPanel.add(new JLabel("💵 حقوق پایه:"));
    formPanel.add(baseSalary);
    formPanel.add(new JLabel("📅 سابقه (سال):"));
    formPanel.add(experience);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
    JButton addEmployee = new JButton("➕ ثبت کارمند");
    JButton showEmployees = new JButton("📄 نمایش کارمندان");
    buttonPanel.add(addEmployee);
    buttonPanel.add(showEmployees);

    JTextArea output = new JTextArea(10, 40);
    output.setEditable(false);
    output.setFont(new Font("Monospaced", Font.PLAIN, 13));
    output.setMargin(new Insets(10, 10, 10, 10));
    JScrollPane scrollPane = new JScrollPane(output);
    scrollPane.setBorder(BorderFactory.createTitledBorder("📋 اطلاعات کارمندان"));

    // لیست کارمندان محلی
    java.util.List<Employee> employeeList = new java.util.ArrayList<>();

    addEmployee.addActionListener(e -> {
      try {
        Employee emp = new Employee(firstName.getText().trim(), lastName.getText().trim(),
            nationalId.getText().trim(), position.getText().trim(),
            Double.parseDouble(baseSalary.getText().trim()), Integer.parseInt(experience.getText().trim()));
        employeeList.add(emp);
        JOptionPane.showMessageDialog(panel, "✅ کارمند با موفقیت ثبت شد");

        // پاک کردن فیلدها
        firstName.setText("");
        lastName.setText("");
        nationalId.setText("");
        position.setText("");
        baseSalary.setText("");
        experience.setText("");
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(panel, "⚠️ لطفاً اطلاعات عددی را صحیح وارد کنید", "خطا",
            JOptionPane.ERROR_MESSAGE);
      }
    });

    showEmployees.addActionListener(e -> {
      output.setText("");
      for (Employee emp : employeeList) {
        output.append(emp.getFullInfo());
        output.append("\n\n");
      }
    });

    panel.add(formPanel, BorderLayout.NORTH);
    panel.add(buttonPanel, BorderLayout.CENTER);
    panel.add(scrollPane, BorderLayout.SOUTH);

    return panel;
  }
  private JPanel createProductPanel() {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBorder(BorderFactory.createTitledBorder("📦 مدیریت کالاها"));

    JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
    formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

    JTextField nameField = new JTextField();
    JTextField featuresField = new JTextField();
    JTextField monthsField = new JTextField();
    JTextField quantityField = new JTextField();

    formPanel.add(new JLabel("🛍 نام کالا:"));
    formPanel.add(nameField);
    formPanel.add(new JLabel("📑 ویژگی‌ها:"));
    formPanel.add(featuresField);
    formPanel.add(new JLabel("📆 مدت موجودی (ماه):"));
    formPanel.add(monthsField);
    formPanel.add(new JLabel("🔢 تعداد:"));
    formPanel.add(quantityField);

JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
    JButton addProduct = new JButton("➕ افزودن کالا");
    JButton showProducts = new JButton("📄 نمایش کالاها");
    JButton updateQuantity = new JButton("🔄 به‌روزرسانی تعداد");
    buttonPanel.add(addProduct);
    buttonPanel.add(showProducts);
    buttonPanel.add(updateQuantity);

    JTextArea output = new JTextArea(10, 40);
    output.setEditable(false);
    output.setFont(new Font("Monospaced", Font.PLAIN, 13));
    output.setMargin(new Insets(10, 10, 10, 10));
    JScrollPane scrollPane = new JScrollPane(output);
    scrollPane.setBorder(BorderFactory.createTitledBorder("📋 اطلاعات کالاها"));

    // لیست محلی برای محصولات
    List<Product> productList = new ArrayList<>(InventoryManager.getProductList());

    addProduct.addActionListener(e -> {
      try {
        String name = nameField.getText().trim();
        String features = featuresField.getText().trim();
        int months = Integer.parseInt(monthsField.getText().trim());
        int quantity = Integer.parseInt(quantityField.getText().trim());

        Product product = new Product(name, features, months, quantity);
        productList.add(product);
        InventoryManager.addProductToList(product);
        JOptionPane.showMessageDialog(panel, "✅ کالا با موفقیت ثبت شد");

        nameField.setText("");
        featuresField.setText("");
        monthsField.setText("");
        quantityField.setText("");
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(panel, "⚠️ لطفاً اعداد را به درستی وارد کنید", "خطا",
            JOptionPane.ERROR_MESSAGE);
      } catch (IllegalArgumentException ex) {
        JOptionPane.showMessageDialog(panel, "⚠️ " + ex.getMessage(), "خطا", JOptionPane.ERROR_MESSAGE);
      }
    });

    showProducts.addActionListener(e -> {
      productList.clear();
      productList.addAll(InventoryManager.getProductList());
      output.setText("");
      for (Product p : productList) {
        output.append(p.getProductInfo());
        output.append("\n\n");
      }
    });

    updateQuantity.addActionListener(e -> {
      String name = JOptionPane.showInputDialog(panel, "نام کالا برای به‌روزرسانی:");
      if (name == null || name.trim().isEmpty()) {
        JOptionPane.showMessageDialog(panel, "⚠️ نام کالا نمی‌تواند خالی باشد");
        return;
      }

      Product found = null;
      for (Product p : InventoryManager.getProductList()) {
        if (p.getName().equalsIgnoreCase(name.trim())) {
          found = p;
          break;
        }
      }

      if (found == null) {
        JOptionPane.showMessageDialog(panel, "⛔️ کالایی با این نام یافت نشد.");
        return;
      }

      String quantityStr = JOptionPane.showInputDialog(panel, "تعداد جدید:");
      try {
        int newQuantity = Integer.parseInt(quantityStr.trim());
        if (newQuantity < 0) throw new NumberFormatException();
        found.setQuantity(newQuantity);
        InventoryManager.saveProducts();
        JOptionPane.showMessageDialog(panel, "✅ تعداد کالا به‌روزرسانی شد.");
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(panel, "⚠️ لطفاً یک عدد معتبر وارد کنید", "خطا", JOptionPane.ERROR_MESSAGE);
      }
    });

    panel.add(formPanel, BorderLayout.NORTH);
    panel.add(buttonPanel, BorderLayout.CENTER);
    panel.add(scrollPane, BorderLayout.SOUTH);

    return panel;
  }
    private List<Constants.Registration> loadRegistrations() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("registrations.dat"))) {
            return (List<Constants.Registration>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void saveRegistrations(List<Constants.Registration> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("registrations.dat"))) {
            oos.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

private JPanel createRegistrationPanel() {
      JPanel panel = new JPanel(new BorderLayout(10, 10));
      panel.setBorder(BorderFactory.createTitledBorder("📝 ثبت‌نام در دوره آموزشی"));

      JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
      formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

      JTextField nameField = new JTextField();
      JTextField nationalIdField = new JTextField();
      JTextField phoneField = new JTextField();

      JComboBox<String> courseCombo = new JComboBox<>();
      for (int i = 0; i < Constants.COURSES.length; i++) {
          for (int j = 0; j < Constants.COURSES[i].length; j++) {
              String[] course = Constants.COURSES[i][j];
              courseCombo.addItem(String.format("%s | %s | %s (%s تومان)",
                      course[0], course[2], course[3],
                      Constants.formatPrice(Integer.parseInt(course[1]))));
          }
      }

      formPanel.add(new JLabel("👤 نام و نام خانوادگی:"));
      formPanel.add(nameField);
      formPanel.add(new JLabel("🆔 کد ملی (10 رقم):"));
      formPanel.add(nationalIdField);
      formPanel.add(new JLabel("📞 شماره تماس:"));
      formPanel.add(phoneField);
      formPanel.add(new JLabel("📚 انتخاب دوره:"));
      formPanel.add(courseCombo);

      JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
      JButton registerBtn = new JButton("➕ ثبت‌نام");
      JButton showAllBtn = new JButton("📋 نمایش ثبت‌نام‌ها");
      buttonPanel.add(registerBtn);
      buttonPanel.add(showAllBtn);

      JTextArea output = new JTextArea(10, 40);
      output.setEditable(false);
      output.setFont(new Font("Monospaced", Font.PLAIN, 13));
      output.setMargin(new Insets(10, 10, 10, 10));
      JScrollPane scrollPane = new JScrollPane(output);
      scrollPane.setBorder(BorderFactory.createTitledBorder("🗂 اطلاعات ثبت‌نام‌ها"));

      List<Constants.Registration> registrationList = new ArrayList<>(loadRegistrations());

      registerBtn.addActionListener(e -> {
          String fullName = nameField.getText().trim();
          String nationalId = nationalIdField.getText().trim();
          String phone = phoneField.getText().trim();
          int selectedIndex = courseCombo.getSelectedIndex();

          if (!nationalId.matches("\\d{10}")) {
              JOptionPane.showMessageDialog(panel, "⚠️ کد ملی نامعتبر است!", "خطا", JOptionPane.ERROR_MESSAGE);
              return;
          }

          int group = selectedIndex / 4;
          int index = selectedIndex % 4;
          String[] course = Constants.COURSES[group][index];

          Constants.Registration reg = new Constants.Registration(
                  fullName, nationalId, phone,
                  course[0], Integer.parseInt(course[1]), course[2], course[3]
          );
          registrationList.add(reg);
          saveRegistrations(registrationList);
          JOptionPane.showMessageDialog(panel, "✅ ثبت‌نام با موفقیت انجام شد.");

          nameField.setText("");
          nationalIdField.setText("");
          phoneField.setText("");
      });

      showAllBtn.addActionListener(e -> {
          registrationList.clear();
          registrationList.addAll(loadRegistrations());
          output.setText("");
          for (Constants.Registration r : registrationList) {
              output.append(r.getRegistrationInfo() + "\n\n");
          }
      });

      panel.add(formPanel, BorderLayout.NORTH);
      panel.add(buttonPanel, BorderLayout.CENTER);
      panel.add(scrollPane, BorderLayout.SOUTH);

      return panel;
  }

  private JPanel createPayrollPanel() {
      JPanel panel = new JPanel(new BorderLayout(10, 10));
      panel.setBorder(BorderFactory.createTitledBorder("📄 صدور فیش حقوقی"));

      // فرم ورود اطلاعات
      JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
      formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

JTextField nameField = new JTextField();
      JTextField jobField = new JTextField();
      JTextField dateField = new JTextField();
      JTextField baseSalaryField = new JTextField();
      JTextField hoursWorkedField = new JTextField();
      JTextField bonusField = new JTextField();
      JTextField deductionField = new JTextField();
      JTextField cardNumberField = new JTextField();
      JComboBox<String> paymentBox = new JComboBox<>(new String[] { "واریز به حساب بانکی", "پرداخت نقدی" });

      formPanel.add(new JLabel("👤 نام کارمند:"));
      formPanel.add(nameField);
      formPanel.add(new JLabel("🔧 شغل:"));
      formPanel.add(jobField);
      formPanel.add(new JLabel("📅 تاریخ:"));
      formPanel.add(dateField);
      formPanel.add(new JLabel("💰 حقوق پایه:"));
      formPanel.add(baseSalaryField);
      formPanel.add(new JLabel("⏱️ ساعات کاری:"));
      formPanel.add(hoursWorkedField);
      formPanel.add(new JLabel("🎁 پاداش:"));
      formPanel.add(bonusField);
      formPanel.add(new JLabel("📉 کسری:"));
      formPanel.add(deductionField);
      formPanel.add(new JLabel("💳 روش پرداخت:"));
      formPanel.add(paymentBox);
      formPanel.add(new JLabel("🔢 شماره کارت (در صورت نیاز):"));
      formPanel.add(cardNumberField);

      // خروجی و دکمه‌ها
      JTextArea outputArea = new JTextArea(12, 30);
      outputArea.setEditable(false);
      outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
      JScrollPane scrollPane = new JScrollPane(outputArea);
      scrollPane.setBorder(BorderFactory.createTitledBorder("📑 فیش حقوقی"));

      JButton generateButton = new JButton("📤 صدور فیش");

      generateButton.addActionListener(e -> {
          try {
              EmployeePayroll payroll = new EmployeePayroll(
                      nameField.getText().trim(),
                      jobField.getText().trim(),
                      dateField.getText().trim(),
                      Double.parseDouble(baseSalaryField.getText().trim())
              );

              payroll.calculateSalary(Integer.parseInt(hoursWorkedField.getText().trim()));
              payroll.setBonus(Double.parseDouble(bonusField.getText().trim()));
              payroll.setDeductions(Double.parseDouble(deductionField.getText().trim()));

              String method = paymentBox.getSelectedIndex() == 0 ? "1" : "2";
              String card = cardNumberField.getText().trim();
              payroll.setPaymentMethod(method, card);

              // چاپ فیش در خروجی
              outputArea.setText("");
              NumberFormat nf = NumberFormat.getInstance(new Locale("fa", "IR"));
              outputArea.append("نام: " + payroll.employeeName + "\n");
              outputArea.append("شغل: " + payroll.jobTitle + "\n");
              outputArea.append("تاریخ: " + payroll.date + "\n\n");
              outputArea.append("حقوق پایه: " + nf.format(payroll.baseSalary) + " ریال\n");
              outputArea.append("اضافه‌کار: " + nf.format(payroll.overtime) + " ریال\n");
              outputArea.append("پاداش: " + nf.format(payroll.bonus) + " ریال\n");
              outputArea.append("کسری: " + nf.format(payroll.deductions) + " ریال\n");
              outputArea.append("-----------------------------\n");
              outputArea.append("حقوق خالص: " + nf.format(payroll.calculateNetSalary()) + " ریال\n\n");
              outputArea.append("روش پرداخت: " + (method.equals("1") ? "واریز به حساب بانکی" : "پرداخت نقدی") + "\n");
              if (method.equals("1")) {
                  outputArea.append("شماره کارت: " + card.replaceAll("(\\d{4})(?=\\d)", "$1-") + "\n");
              }
          } catch (NumberFormatException ex) {
              JOptionPane.showMessageDialog(panel, "⚠️ لطفاً مقادیر عددی را صحیح وارد کنید", "خطا", JOptionPane.ERROR_MESSAGE);
          } catch (Exception ex) {
              JOptionPane.showMessageDialog(panel, "⚠️ خطا: " + ex.getMessage(), "خطا", JOptionPane.ERROR_MESSAGE);
          }
      });


// افزودن به پنل
      JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
      buttonPanel.add(generateButton);

      panel.add(formPanel, BorderLayout.NORTH);
      panel.add(buttonPanel, BorderLayout.CENTER);
      panel.add(scrollPane, BorderLayout.SOUTH);

      return panel;
  }
  private JPanel createConferencePanel() {
      JPanel panel = new JPanel(new BorderLayout(10, 10));
      panel.setBorder(BorderFactory.createTitledBorder("📅 ایجاد رویداد کنفرانس"));

      JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
      formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

      JTextField eventIdField = new JTextField();
      JComboBox<EventType> eventTypeBox = new JComboBox<>(EventType.values());
      JTextField dateField = new JTextField();
      JTextField startTimeField = new JTextField();
      JTextField endTimeField = new JTextField();
      JTextField attendeesField = new JTextField();

      formPanel.add(new JLabel("🆔 کد رویداد:"));
      formPanel.add(eventIdField);
      formPanel.add(new JLabel("🏷 نوع رویداد:"));
      formPanel.add(eventTypeBox);
      formPanel.add(new JLabel("📅 تاریخ:"));
      formPanel.add(dateField);
      formPanel.add(new JLabel("⏰ ساعت شروع:"));
      formPanel.add(startTimeField);
      formPanel.add(new JLabel("⏰ ساعت پایان:"));
      formPanel.add(endTimeField);
      formPanel.add(new JLabel("👥 تعداد شرکت‌کنندگان:"));
      formPanel.add(attendeesField);

      JTextArea outputArea = new JTextArea(10, 30);
      outputArea.setEditable(false);
      outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
      JScrollPane scrollPane = new JScrollPane(outputArea);
      scrollPane.setBorder(BorderFactory.createTitledBorder("📌 اطلاعات رویداد"));

      JButton createButton = new JButton("📤 ایجاد رویداد");

      createButton.addActionListener(e -> {
          try {
              String eventId = eventIdField.getText().trim();
              EventType type = (EventType) eventTypeBox.getSelectedItem();
              String date = dateField.getText().trim();
              String start = startTimeField.getText().trim();
              String end = endTimeField.getText().trim();
              int attendees = Integer.parseInt(attendeesField.getText().trim());

              ConferenceRoom room = new ConferenceRoom("R1", "سالن A", 100); // موقتی
              ConferenceEvent event = new ConferenceEvent(eventId, type, date, start, end, room, attendees);
              boolean reserved = event.reserve();

              if (reserved) {
                  outputArea.setText(event.toFileString());
              } else {
                  outputArea.setText("⚠️ امکان رزرو سالن نیست.");
              }
          } catch (Exception ex) {
              JOptionPane.showMessageDialog(panel, "⚠️ خطا: " + ex.getMessage(), "خطا", JOptionPane.ERROR_MESSAGE);
          }
      });

      JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
      buttonPanel.add(createButton);

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
