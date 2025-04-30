package manager;

import common.Product;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InventoryManager {
    private static final String FILE_NAME = "inventory_data.dat";
    private static final List<Product> products = loadProducts();
    private static final Scanner scanner = new Scanner(System.in);

    private static List<Product> loadProducts() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("فایل ذخیره‌سازی یافت نشد. یک لیست جدید ایجاد می‌شود.");
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Product>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("خطا در بارگذاری داده‌ها: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void saveProducts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(products);
        } catch (IOException e) {
            System.err.println("خطا در ذخیره‌سازی داده‌ها: " + e.getMessage());
        }
    }

    public static void addProduct() {
        System.out.println("\n--- ثبت کالای جدید ---");

        String name = getValidInput("نام کالا: ", "نام کالا نمی‌تواند خالی باشد");
        String features = getInput("ویژگی‌های کالا: ");
        int months = getPositiveInt("مدت موجودی در انبار (ماه): ");
        int quantity = getNonNegativeInt("تعداد: ");

        products.add(new Product(name, features, months, quantity));
        saveProducts();
        System.out.println("✅ کالا با موفقیت ثبت شد!");
    }

    public static void displayProductsByMonths() {
        if (products.isEmpty()) {
            System.out.println("⛔ هیچ کالایی ثبت نشده است.");
            return;
        }

        System.out.println("\n--- موجودی انبار بر اساس مدت ---");
        for (int i = 1; i <= 12; i++) {
            final int month = i;
            List<Product> monthlyProducts = products.stream()
                    .filter(p -> p.getMonthsInStock() == month)
                    .collect(Collectors.toList());

            System.out.printf("\nکالاهای با موجودی %d ماه:%n", month);
            if (monthlyProducts.isEmpty()) {
                System.out.println("-- هیچ کالایی یافت نشد --");
            } else {
                monthlyProducts.forEach(p -> {
                    System.out.println(p.getProductInfo());
                    System.out.println("-------------------");
                });
            }
        }
    }

    public static void displayAllProducts() {
        if (products.isEmpty()) {
            System.out.println("⛔ هیچ کالایی ثبت نشده است.");
            return;
        }

        System.out.println("\n--- تمام کالاهای ثبت‌شده ---");
        products.forEach(p -> {
            System.out.println(p.getProductInfo());
            System.out.println("-------------------");
        });
    }

    public static void searchProduct() {
        String name = getValidInput("\nنام کالا برای جستجو: ", "نام جستجو نمی‌تواند خالی باشد");

        List<Product> foundProducts = products.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());

        if (foundProducts.isEmpty()) {
            System.out.println("⛔ کالایی با این نام یافت نشد.");
        } else {
            foundProducts.forEach(p -> System.out.println(p.getProductInfo()));
        }
    }

    public static void updateProductQuantity() {
        String name = getValidInput("\nنام کالا برای به‌روزرسانی: ", "نام کالا نمی‌تواند خالی باشد");

        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(name)) {
                int newQuantity = getNonNegativeInt("تعداد جدید: ");
                p.setQuantity(newQuantity);
                saveProducts();
                System.out.println("✅ تعداد کالا به‌روزرسانی شد.");
                return;
            }
        }

        System.out.println("⛔ کالایی با این نام یافت نشد.");
    }

    // Helper methods
    private static String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static String getValidInput(String prompt, String errorMessage) {
        while (true) {
            String input = getInput(prompt).trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("⚠ " + errorMessage);
        }
    }

    private static int getNonNegativeInt(String prompt) {
        while (true) {
            try {
                int value = Integer.parseInt(getInput(prompt));
                if (value >= 0) {
                    return value;
                }
                System.out.println("⚠ عدد نمی‌تواند منفی باشد!");
            } catch (NumberFormatException e) {
                System.out.println("⚠ لطفاً عدد معتبر وارد کنید!");
            }
        }
    }

    private static int getPositiveInt(String prompt) {
        while (true) {
            int value = getNonNegativeInt(prompt);
            if (value > 0) {
                return value;
            }
            System.out.println("⚠ عدد باید بزرگتر از صفر باشد!");
        }
    }
}