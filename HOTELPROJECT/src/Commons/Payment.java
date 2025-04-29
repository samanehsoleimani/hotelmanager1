package Commons;
import Commons.Room1;
public class Payment {
	private int roomNumber;
    private String guest;
    private Room1 room;
    private String status; // "paid", "unpaid", etc.

    private static final double PRICE_PER_BED = 500.0; // مبلغ هر تخت (قابل تغییر)

    public int getRoomNumber() {
    	
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getGuest() {
		return guest;
	}

	public void setGuest(String guest) {
		this.guest = guest;
	}

	public Room1 getRoom() {
		return room;
	}

	public void setRoom(Room1 room) {
		this.room = room;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static double calculateTotalAmount(Room1 room, int nights) {
	    double pricePerRoom = 500.0;
	    int beds = Integer.parseInt(room.getBed());  // تبدیل رشته به عدد
	    return pricePerRoom * beds * nights;  // محاسبه مبلغ کل با احتساب تعداد شب
	}

    
}
