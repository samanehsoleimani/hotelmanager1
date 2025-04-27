package Manager;

import Common.Food;
import txtFileManager.txtfilemanager;
import java.util.Scanner;

public class FoodManager {
    private Scanner scanner;
    private txtfilemanager fileManager;

    private Food[] foods = {
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

    public FoodManager() {
        scanner = new Scanner(System.in);
        fileManager = new txtfilemanager("FOOD.txt");
    }

    public void startFoodService() {
        System.out.println("Do you want to use the food service? (yes/no)");
        String answer = scanner.nextLine().trim().toLowerCase();

        if (answer.equals("yes")) {
            System.out.println("Please enter your name:");
            String guestName = scanner.nextLine().trim();

            System.out.println("Please enter your room number:");
            String roomNumber = scanner.nextLine().trim();

            listAllFoods();

            String[] foodTimes = {"Breakfast", "Lunch", "Dinner"};
            double totalPrice = 0;
            StringBuilder orderDetails = new StringBuilder();
            orderDetails.append("\n[Food Service]\n");
            orderDetails.append("Guest Name: ").append(guestName).append("\n");
            orderDetails.append("Room Number: ").append(roomNumber).append("\n");

            for (int i = 0; i < foodTimes.length; i++) {
                System.out.println("Please select your " + foodTimes[i] + " foods (separate them with commas):");
                String[] selectedFoods = scanner.nextLine().trim().split(",");

                double mealPrice = calculateTotalPrice(selectedFoods, foodTimes[i]);
                totalPrice += mealPrice;

                orderDetails.append(foodTimes[i]).append(" foods: ")
                        .append(String.join(", ", selectedFoods))
                        .append(" - Total Price: ").append(mealPrice).append(" Toman\n");
            }

            System.out.println("Would you like some water too? (yes/no)");
            if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                totalPrice += 2000;
                orderDetails.append("Water: Added\n");
            }

            orderDetails.append("Total Price: ").append(totalPrice).append(" Toman\n");

            fileManager.AppendRow(orderDetails.toString());

            System.out.println("Total price for all meals: " + totalPrice + " Toman");
        } else {
            System.out.println("Thank you, hope to see you again soon!");
        }
    }

    public double calculateTotalPrice(String[] selectedFoods, String foodTime) {
        double totalPrice = 0;
        for (int i = 0; i < foods.length; i++) {
            if (foods[i].getFoodTime().equalsIgnoreCase(foodTime)) {
                for (int j = 0; j < selectedFoods.length; j++) {
                    if (foods[i].getFoodName().equalsIgnoreCase(selectedFoods[j].trim())) {
                        totalPrice += foods[i].getFoodPrice();
                    }
                }
            }
        }
        return totalPrice;
    }

    public void listAllFoods() {
        System.out.println("📋 Available foods list:");
        for (int i = 0; i < foods.length; i++) {
            System.out.println("🍽️ " + foods[i].getFoodName() + " - Price: " + foods[i].getFoodPrice() + " Toman - Time: " + foods[i].getFoodTime());
        }
    }
}
