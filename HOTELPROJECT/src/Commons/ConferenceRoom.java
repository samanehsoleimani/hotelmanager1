package common;

public class ConferenceRoom {
    private String roomId;
    private String name;
    private int capacity;
    private boolean isAvailable;

    public ConferenceRoom(String roomId, String name, int capacity) {
        this.roomId = roomId;
        this.name = name;
        this.capacity = capacity;
        this.isAvailable = true;
    }

    public boolean checkAvailability() {
        return isAvailable;
    }

    public boolean reserveRoom(int attendees) {
        if (!isAvailable) {
            System.out.println("❌ سالن در حال حاضر رزرو است!");
            return false;
        }
        if (attendees > capacity) {
            System.out.println("❌ تعداد شرکت‌کنندگان بیش از ظرفیت سالن است! (ظرفیت: " + capacity + ")");
            return false;
        }
        isAvailable = false;
        System.out.println("✅ سالن " + name + " با ظرفیت " + attendees + "/" + capacity + " نفر رزرو شد.");
        return true;
    }

    public String getRoomInfo() {
        return name + " (ظرفیت: " + capacity + " نفر)";
    }
}