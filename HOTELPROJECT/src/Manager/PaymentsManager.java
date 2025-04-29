package Manager;

import Commons.commons;
import Commons.Payment;
import Common.Room1;
import txtFileManager.txtfilemanager;

import java.io.IOException;

public class PaymentsManager {
    public txtfilemanager fn;

    public PaymentsManager() {
        fn = new txtfilemanager("Paymant.txt");
       //fn.Clear();
    }
    
    public void insert(Payment payment, Room1 room, int nights) {
        String s = payment.getRoomNumber() + commons.Splitter +
                   payment.getGuest() + commons.Splitter +
                   payment.getStatus() + commons.Splitter +
                   room.getBed() + commons.Splitter +
                   nights + "\n";  // اضافه کردن تعداد شب‌ها
        fn.AppendRow(s);
    }

    public void showAllPayments() {
        String[] rows = fn.getArrayFromFile(); // خواندن اطلاعات از فایل
        for (int i = 0; i < rows.length; i++) {
            String row = rows[i];
            // استفاده از | به عنوان جداکننده
            String[] data = row.split("\\|");
            if (data.length >= 3) {
                System.out.println("Room: " + data[0] + ", Guest: " + data[1] + ", Status: " + data[2]);
            }
        }
    }

    public void searchByStatus(String status) {
        String[] rows = fn.getArrayFromFile(); // خواندن اطلاعات از فایل
        for (int i = 0; i < rows.length; i++) {
            String row = rows[i];
            // استفاده از | به عنوان جداکننده
            String[] data = row.split("\\|"); // استفاده از \\| برای جدا کردن بر اساس |
            
            if (data.length >= 3 && data[2].equalsIgnoreCase(status)) {
                System.out.println("Room: " + data[0] + ", Guest: " + data[1]);
            }
        }
    }

    public double calculateTotalPayments() {
        String[] rows = fn.getArrayFromFile();  // خواندن داده‌ها از فایل
        double total = 0.0;

        for (String row : rows) {
            String[] data = row.split("\\|");

            if (data.length >= 5 && data[2].equalsIgnoreCase("paid")) {
                int roomNumber = Integer.parseInt(data[0]);
                String bed = data[3];
                int nights = Integer.parseInt(data[4]);  // خواندن تعداد شب‌ها

                Room1 room = new Room1();
                room.setRoomNumber(roomNumber);
                room.setBed(bed);

                total += Payment.calculateTotalAmount(room, nights);  // پاس دادن nights
            }
        }

        return total;
    }

    public void updatePaymentStatus(int roomNumber, String newStatus) {
        String[] rows = fn.getArrayFromFile();  // خواندن اطلاعات از فایل
        boolean paymentFound = false;

        for (int i = 0; i < rows.length; i++) {
            String row = rows[i];
            String[] data = row.split("\\|");  // جدا کردن اطلاعات بر اساس |
            
            if (data.length >= 3 && Integer.parseInt(data[0]) == roomNumber) {
                data[2] = newStatus;  

                rows[i] = String.join("|", data);  

                paymentFound = true;
                break; 
            }
        }

        if (!paymentFound) {
            System.out.println("Payment for room number " + roomNumber + " not found.");
        } else {
            fn.writeArrayToFile(rows);
            System.out.println("Payment status for room number " + roomNumber + " updated to: " + newStatus);
        }
    }

    public void showGuestNights() {
        String[] rows = fn.getArrayFromFile();

        for (String row : rows) {
            String[] data = row.split("\\|");

            if (data.length >= 5) {
                String guestName = data[1];
                String nights = data[4];  

                System.out.println("Guest: " + guestName + ", Nights: " + nights);
            }
        }
    }

    public void calculateTotalPaymentsPerGuest() {
        String[] rows = fn.getArrayFromFile();  // خواندن داده‌ها از فایل

        // متغیر برای ذخیره مجموع پرداخت‌ها
        String currentGuest = "";  // نام مهمان فعلی
        double guestTotal = 0.0;   // مجموع پرداخت‌های مهمان فعلی

        for (String row : rows) {
            String[] data = row.split("\\|");

            if (data.length >= 5 && data[2].equalsIgnoreCase("paid")) {
                String guestName = data[1];  // نام مهمان
                int roomNumber = Integer.parseInt(data[0]);
                String bed = data[3];
                int nights = Integer.parseInt(data[4]);  // خواندن تعداد شب‌ها

                Room1 room = new Room1();
                room.setRoomNumber(roomNumber);
                room.setBed(bed);

                double totalAmount = Payment.calculateTotalAmount(room, nights);  // محاسبه مبلغ کل برای این اتاق

                // اگر مهمان جدید باشد
                if (!guestName.equals(currentGuest)) {
                    if (!currentGuest.equals("")) {
                        System.out.println("Guest: " + currentGuest + ", Total Payment: " + guestTotal);
                    }

                    // تغییر به مهمان جدید
                    currentGuest = guestName;
                    guestTotal = totalAmount;  // شروع مجموع پرداخت‌ها برای مهمان جدید
                } else {
                    guestTotal += totalAmount;
                }
            }
        }

        if (!currentGuest.equals("")) {
            System.out.println("Guest: " + currentGuest + ", Total Payment: " + guestTotal);
        }
    }

    public void updatePayment(int roomNumber, Payment newPayment, Room1 newRoom, int newNights) {
        String[] rows = fn.getArrayFromFile();  
        boolean paymentFound = false;

        for (int i = 0; i < rows.length; i++) {
            String row = rows[i];
            String[] data = row.split("\\|");

            if (data.length >= 5 && Integer.parseInt(data[0]) == roomNumber) {
                // به‌روزرسانی اطلاعات پرداخت
                data[1] = newPayment.getGuest();  
                data[2] = newPayment.getStatus();  
                data[3] = newRoom.getBed();  
                data[4] = String.valueOf(newNights);  

                // ذخیره داده‌ها در فایل
                rows[i] = String.join("|", data);

                paymentFound = true;
                break;
            }
        }

        if (!paymentFound) {
            System.out.println("Payment for room number " + roomNumber + " not found.");
        } else {
            fn.writeArrayToFile(rows);  // ذخیره داده‌های به‌روز شده در فایل
            System.out.println("Payment updated for room number " + roomNumber);
        }
    }
}
