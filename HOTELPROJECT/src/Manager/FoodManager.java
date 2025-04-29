package Manager;

import Common.Food;
import txtFileManager.txtfilemanager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FoodManager {

    private Food[] foods;
    private txtfilemanager fileManager;
    private JPanel foodPanel; // پنل اصلی

    public FoodManager() {
        foods = new Food[]{
                new Food("Pasta", 120000, "Lunch"),
                new Food("Pizza", 200000, "Dinner"),
                new Food("Omelette", 60000, "Breakfast"),
                new Food("Salad", 30000, "Lunch"),
                new Food("Burger", 80000, "Dinner"),
                new Food("Fried Chicken", 150000, "Dinner"),
                new Food("Sandwich", 40000, "Lunch"),
                new Food("Soup", 35000, "Dinner"),
                new Food("Grilled Fish", 180000, "Lunch"),
                new Food("Steak", 250000, "Dinner"),
                new Food("Pancakes", 50000, "Breakfast"),
                new Food("Fruit Salad", 25000, "Breakfast"),
                new Food("Spaghetti", 120000, "Lunch"),
                new Food("Rice and Chicken", 95000, "Lunch"),
                new Food("Noodles", 70000, "Dinner"),
                new Food("Spring Rolls", 45000, "Lunch"),
                new Food("Tacos", 95000, "Dinner"),
                new Food("Kebab Koobideh", 200000, "Dinner"),
                new Food("Kebab Soltani", 250000, "Dinner"),
                new Food("Khoresh Gormeh Sabzi", 180000, "Lunch"),
                new Food("Khoresh Fesenjan", 190000, "Dinner"),
                new Food("Chelow Morgh", 140000, "Lunch"),
                new Food("Baghali Polo with Meat", 150000, "Lunch"),
                new Food("Mirza Ghasemi", 70000, "Dinner"),
                new Food("Dolmeh", 80000, "Lunch"),
                new Food("Ash Reshteh", 60000, "Dinner"),
                new Food("Kebab Khorak", 220000, "Dinner"),
                new Food("Halim", 65000, "Breakfast"),
                new Food("Salad Shirazi", 25000, "Lunch"),
                new Food("Kashk Bademjan", 70000, "Lunch"),
                new Food("Falafel", 50000, "Lunch"),
                new Food("Kofteh Tabrizi", 150000, "Dinner")
        };
        fileManager = new txtfilemanager("FOOD.txt");
        buildPanel(); // ساختن پنل هنگام سازنده
    }

    private void buildPanel() {
        foodPanel = new JPanel();
        foodPanel.setLayout(null);

        JLabel nameLabel = new JLabel("Guest Name:");
        nameLabel.setBounds(30, 20, 100, 30);
        foodPanel.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(140, 20, 300, 30);
        foodPanel.add(nameField);

        JLabel roomLabel = new JLabel("Room Number:");
        roomLabel.setBounds(30, 60, 100, 30);
        foodPanel.add(roomLabel);

        JTextField roomField = new JTextField();
        roomField.setBounds(140, 60, 300, 30);
        foodPanel.add(roomField);

        JLabel mealTypeLabel = new JLabel("Select Meal Type:");
        mealTypeLabel.setBounds(30, 100, 100, 30);
        foodPanel.add(mealTypeLabel);

        String[] mealTypes = {"Breakfast", "Lunch", "Dinner"};
        JComboBox<String> mealTypeComboBox = new JComboBox<>(mealTypes);
        mealTypeComboBox.setBounds(140, 100, 300, 30);
        foodPanel.add(mealTypeComboBox);

        JLabel foodLabel = new JLabel("Select Food:");
        foodLabel.setBounds(30, 140, 100, 30);
        foodPanel.add(foodLabel);

        JComboBox<String> foodComboBox = new JComboBox<>();
        foodComboBox.setBounds(140, 140, 300, 30);
        foodPanel.add(foodComboBox);

        mealTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMeal = (String) mealTypeComboBox.getSelectedItem();
                updateFoodComboBox(selectedMeal, foodComboBox);
            }
        });

        JButton submitButton = new JButton("Submit Order");
        submitButton.setBounds(140, 190, 150, 40);
        foodPanel.add(submitButton);

        JTextArea resultArea = new JTextArea();
        resultArea.setBounds(30, 250, 410, 100);
        resultArea.setEditable(false);
        foodPanel.add(resultArea);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String guestName = nameField.getText().trim();
                String roomNumber = roomField.getText().trim();
                String selectedFood = (String) foodComboBox.getSelectedItem();

                if (guestName.isEmpty() || roomNumber.isEmpty() || selectedFood == null) {
                    JOptionPane.showMessageDialog(foodPanel, "Please fill all fields and select a food.");
                    return;
                }

                double totalPrice = getFoodPrice(selectedFood);
                StringBuilder orderDetails = new StringBuilder();
                orderDetails.append("\n[Food Service]\n");
                orderDetails.append("Guest Name: ").append(guestName).append("\n");
                orderDetails.append("Room Number: ").append(roomNumber).append("\n");
                orderDetails.append("Selected Food: ").append(selectedFood).append("\n");

                int waterOption = JOptionPane.showConfirmDialog(foodPanel, "Would you like water? (+2000 Toman)", "Water", JOptionPane.YES_NO_OPTION);
                if (waterOption == JOptionPane.YES_OPTION) {
                    totalPrice += 2000;
                    orderDetails.append("Water: Added\n");
                }

                orderDetails.append("Total Price: ").append(totalPrice).append(" Toman\n");

                resultArea.setText(orderDetails.toString());
                fileManager.AppendRow(orderDetails.toString());

                JOptionPane.showMessageDialog(foodPanel, "Order saved successfully!");
            }
        });

        updateFoodComboBox("Breakfast", foodComboBox);
    }

    private void updateFoodComboBox(String mealType, JComboBox<String> foodComboBox) {
        foodComboBox.removeAllItems();
        for (Food food : foods) {
            if (food.getMealType().equalsIgnoreCase(mealType)) {
                foodComboBox.addItem(food.getFoodName());
            }
        }
    }

    private double getFoodPrice(String foodName) {
        for (Food food : foods) {
            if (food.getFoodName().equalsIgnoreCase(foodName)) {
                return food.getFoodPrice();
            }
        }
        return 0;
    }

    // 📌 متد مورد نیاز myMainSwing برای دریافت پنل
    public JPanel getPanel() {
        return foodPanel;
    }
}
