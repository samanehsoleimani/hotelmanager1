package Common;

public class Food {

    private String foodName;
    private double foodPrice;
    private String mealType; // نوع وعده غذایی (Breakfast, Lunch, Dinner)

    // سازنده برای ایجاد غذا
    public Food(String foodName, double foodPrice, String mealType) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.mealType = mealType;
    }

    // متد برای دریافت نام غذا
    public String getFoodName() {
        return foodName;
    }

    // متد برای دریافت قیمت غذا
    public double getFoodPrice() {
        return foodPrice;
    }

    // متد برای دریافت نوع وعده غذایی
    public String getMealType() {
        return mealType;
    }
}
