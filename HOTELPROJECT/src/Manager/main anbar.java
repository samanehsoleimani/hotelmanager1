import manager.InventoryManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // ذخیره‌سازی خودکار هنگام بستن برنامه
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nدر حال ذخیره‌سازی داده‌ها...");
            InventoryManager.saveProducts();
        }));

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();

            int choice = getIntInput(scanner, "لطفاً گزینه مورد نظر را انتخاب کنید: ");

            switch (choice) {
                case 1 -> InventoryManager.addProduct();
                case 2 -> InventoryManager.displayProductsByMonths();
                case 3 -> InventoryManager.displayAllProducts();
                case 4 -> InventoryManager.searchProduct();
                case 5 -> InventoryManager.updateProductQuantity();
                case 6 -> {
                    System.out.println("خروج از سیستم...");
                    running = false;
                }
                default -> System.out.println("⚠ گزینه نامعتبر!");
            }
        }
    }

    private static void printMenu() {
        System.out.println("""
            \n*** سیستم مدیریت انبار ***
            1. ثبت کالای جدید
            2. نمایش موجودی بر اساس مدت
            3. نمایش تمام کالاها
            4. جستجوی کالا
            5. به‌روزرسانی تعداد کالا
            6. خروج""");
    }

    private static int getIntInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("⚠ لطفاً عدد وارد کنید!");
            }
        }
    }
}