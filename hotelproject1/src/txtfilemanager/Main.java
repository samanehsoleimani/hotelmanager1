package txtfilemanager;

import Commons.Guests;
import Manager.GuestsManager;

public class Main {
    public static void main(String[] args) {
        GuestsManager manager = new GuestsManager();

        // ساخت یک مهمان
        Guests guest = new Guests();
        guest.setFullName("Ali Rezaei");
        guest.setMellicode(123456789);
        guest.setPhoneNumber("09123456789");
        guest.setRoomNumber(101);

        // اضافه کردن مهمان
       // manager.insert(guest);
        System.out.println("✅ Guest inserted.");

        // نمایش همه مهمان‌ها
        //System.out.println("\n📋 List of all guests:");
        //manager.listAllGuests();
        manager.deleteGuestByName("Ali Rezaei");
        // پیدا کردن یک مهمان
        Guests foundGuest = manager.getGuestByName("Ali Rezaei");
        if (foundGuest != null) {
            System.out.println("\n🔍 Guest found: " + foundGuest.getFullName() + ", Room: " + foundGuest.getRoomNumber());
        } else {
            System.out.println("\n❌ Guest not found.");
        }
    }
}
