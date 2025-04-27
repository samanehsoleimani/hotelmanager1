package Common;

public class Food {
    private String foodName;
    private double foodPrice;
    private String foodTime; // Breakfast, Lunch, or Dinner

    // سازنده
    public Food(String foodName, double foodPrice, String foodTime) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodTime = foodTime;
    }

    // Getter و Setterها
    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(double foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodTime() {
        return foodTime;
    }

    public void setFoodTime(String foodTime) {
        this.foodTime = foodTime;
    }

    @Override
    public String toString() {
        return foodName + " - " + foodPrice + " Toman - Time: " + foodTime;
    }
}




