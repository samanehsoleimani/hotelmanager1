package Manager;

import Commons.commons;
import Commons.Guests;
import txtfilemanager.txtfilemanager;

public class GuestsManager {
    public txtfilemanager fn;

    public GuestsManager() {
        fn = new txtfilemanager("HOTEL.txt");
       // fn.Clear(); // اگه خواستی موقع شروع فایل رو خالی کن
    }

    public void insert(Guests guest) {
        String s = guest.getRoomNumber() + commons.Splitter +
                   guest.getFullName() + commons.Splitter +
                   guest.getMellicode() + commons.Splitter +
                   guest.getPhoneNumber() + "\n"; // اضافه کردن خط جدید
        fn.AppendRow(s);
    }

    public int getRoomNumber(String fullName) {
        String[] data = fn.getArrayFromFile();

        for (int i = 0; i < data.length; i++) {
            String guestData = data[i];
            Guests guest = stringToGuest(guestData);

            if (guest != null && fullName.equals(guest.getFullName())) {
                return guest.getRoomNumber();
            }
        }

        return -1; // مهمان پیدا نشد
    }

    private Guests stringToGuest(String guestData) {
        if (guestData == null || guestData.trim().isEmpty()) {
            return null;
        }

        String[] data = guestData.split("\\|");

        if (data.length < 4) {
            System.out.println("⛔ داده ناقص: " + guestData);
            return null;
        }

        try {
            Guests guest = new Guests();
            guest.setRoomNumber(Integer.parseInt(data[0].trim()));
            guest.setFullName(data[1].trim());
            guest.setMellicode(Integer.parseInt(data[2].trim()));
            guest.setPhoneNumber(data[3].trim());
            return guest;
        } catch (NumberFormatException e) {
            System.out.println("⛔ خطای تبدیل عدد در خط: " + guestData);
            return null;
        }
    }
    public void deleteGuestByName(String fullName) {
        String[] guestsData = fn.getArrayFromFile();
        String newData = "";

        for (int i = 0; i < guestsData.length; i++) {
            if (guestsData[i].trim().isEmpty()) {
                continue;
            }

            Guests guest = stringToGuest(guestsData[i]);
            if (guest == null) {
                continue; // اگر داده نامعتبر بود، ردش کن
            }

            if (fullName.equals(guest.getFullName())) {
                continue; // این مهمان رو حذف کن
            }

            newData += guestsData[i] + "\n";
        }

        fn.setIntoFile(newData);  // 🛠 این خط رو اضافه کن که داده‌ی جدید ذخیره بشه
    }

    public Guests getGuestByName(String fullName) {
        String[] data = fn.getArrayFromFile();

        for (int i = 0; i < data.length; i++) {
            String row = data[i];
            if (row.trim().isEmpty()) {
                continue;
            }

            Guests guest = stringToGuest(row);
            if (guest != null && fullName.equals(guest.getFullName())) {
                return guest;
            }
        }

        return null; // اگه مهمانی با این اسم پیدا نشد
    }

    
    
    public void listAllGuests() {
        String[] data = fn.getArrayFromFile();

        for (int i = 0; i < data.length; i++) {
            Guests g = stringToGuest(data[i]);
            if (g != null) {
                System.out.println("🔹 " + g.getFullName() + " - Room: " + g.getRoomNumber());
            }
        }
    }
    
    
    
    public boolean guestExists(String fullName) {
        String[] data = fn.getArrayFromFile();

        for (int i = 0; i < data.length; i++) {
            Guests g = stringToGuest(data[i]);
            if (g != null && fullName.equals(g.getFullName())) {
                return true;
            }
        }

        return false;
    }

    public void clearAllGuests() {
        fn.Clear();
    }


}
