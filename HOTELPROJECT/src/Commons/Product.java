package common;

import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String name;
    private final String features;
    private final int monthsInStock;
    private int quantity;

    public Product(String name, String features, int monthsInStock, int quantity) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("نام کالا نمی‌تواند خالی باشد");
        }
        if (monthsInStock <= 0) {
            throw new IllegalArgumentException("مدت موجودی باید مثبت باشد");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("تعداد نمی‌تواند منفی باشد");
        }

        this.name = name.trim();
        this.features = features != null ? features.trim() : "";
        this.monthsInStock = monthsInStock;
        this.quantity = quantity;
    }

    public String getProductInfo() {
        return String.format("""
                کالا: %s
                ویژگی‌ها: %s
                مدت موجودی: %d ماه
                تعداد: %d""",
                name, features, monthsInStock, quantity);
    }

    // Getter و Setter ها
    public String getName() { return name; }
    public String getFeatures() { return features; }
    public int getMonthsInStock() { return monthsInStock; }
    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("تعداد نمی‌تواند منفی باشد");
        }
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return getProductInfo();
    }
}